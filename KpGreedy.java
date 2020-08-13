
package pkg0.pkg1.kp;
import java.util.*;
class object{
   public int ID;
   public double price;
   public double weight;
    
     public static Comparator<object> ratio_sort() {
      return (object o1, object o2) -> Double.compare(o1.price/o1.weight, o2.price/o2.weight);
   }
       public static Comparator<object> IDsort() {
      return (object o1, object o2) -> o1.ID - o2.ID;
   }
}
 class Gprintsolution {
   
   String Info;
   public List<object> objects;
   public double weight;
   public double value;
   
   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(Info);
      builder.append(": ");
      builder.append(value);
      builder.append(" ");
      builder.append(weight);
      builder.append("\n");
      
      Collections.sort(objects, object.IDsort());
      builder.append("the picked elements \n");
      objects.stream().map((o) -> {
          builder.append(o.ID);
           return o;
       }).forEachOrdered((object _item) -> {
           builder.append(" ");
      });
      
      return builder.toString();
   }
}
 class Greedy{
   public List<object> objects;
   public int capacity;
  public Greedy(List<object> objects, int capacity) {
      this.objects = objects;
      this.capacity = capacity;
   }
   public Gprintsolution Gsolve() {
      double current_capacity = 0;
      double current_value = 0;
      Gprintsolution greedysolution = new Gprintsolution();
      
      greedysolution.objects = new ArrayList<>(objects);
     int G;
      Collections.sort(greedysolution.objects, object.ratio_sort());
      for ( G = 0; G < greedysolution.objects.size(); G++) {
         object o = greedysolution.objects.get(G);
         if (current_capacity + o.weight > capacity) 
            break;
         
         current_capacity += o.weight;
         current_value += o.price;
      }
      greedysolution.objects = greedysolution.objects.subList(0, G);
      greedysolution.weight = current_capacity;
      greedysolution.value = current_value;
      greedysolution.Info = "Greedy solution doesn't always get the optimal solution for 0-1 knapsack problem but it always get the optimal solution for fractional knapsack";
      
      return greedysolution;
   }
}
public class KpGreedy {

   
    public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);
      System.out.println("enter the number of available objects");
      int Number = scanner.nextInt();
      System.out.println("enter the capacity");
      int capacity = scanner.nextInt();
      
      List<object> objects = new LinkedList<>();
      for (int i = 0; i < Number; i++) {
         object o = new object();
         System.out.println("enter the id of the object");
         o.ID = scanner.nextInt();
         System.out.println("enter the price of the object");
         o.price = scanner.nextDouble();
         System.out.println("enter the weight of the object");
         o.weight = scanner.nextDouble();
         objects.add(o);
      } 
      Greedy g= new Greedy(objects, capacity);
      System.out.println(g.Gsolve().toString());
    }
    
}


import java.util.*;
class obje{
   public int ID;
   public double price;
   public double weight;
    
     public static Comparator<obje> ratio_sort() {
      return (obje o1, obje o2) -> Double.compare(o1.price/o1.weight, o2.price/o2.weight);
   }
       public static Comparator<obje> IDsort() {
      return (obje o1, obje o2) -> o1.ID - o2.ID;
   }
}
 class BBsolution {
   String Info;
   public List<obje> objects;
   public double weight;
   public double price;
   
   @Override
   public String toString() {
      StringBuilder build = new StringBuilder();
      Info=("the branch and bound most feasible solution for 0-1 knapsack is ");
      build.append(Info);
      build.append("of total value : ");
      build.append(price);
      build.append("  and total weight : ");
      build.append(weight);
      build.append("\n");
      
      Collections.sort(objects, obje.IDsort());
      build.append("the picked elements \n");
      objects.stream().map((o) -> {
          build.append(o.ID);
           return o;
       }).forEachOrdered((obje _item) -> {
           build.append(" ");
      });
      
      return build.toString();
   }
}
 class branchandbound{
     public List<obje> objs;
     public int capacity;
    public branchandbound(List<obje> objects, int capacity) {
     this.objs=objects;
     this.capacity=capacity;
   }
  public class treenode implements Comparable<treenode> {
      
      public int height;
      public double B;
      public double price;
      public double weight;
      public List<obje> obj;
      public treenode() {
         obj= new ArrayList<>();
      }
      public treenode(treenode p) {
         height = p.height + 1;
         obj= new ArrayList<>(p.obj);
         B = p.B;
         price = p.price;
         weight = p.weight;
      }
      @Override
      public int compareTo(treenode other) {
         return (int) (other.B - B);
      }
      
      public void boundcomputation() {
         int H = height;
         double Weight = weight;
         B = price;
         obje object;
         do {
            object = objs.get(H);
            if (Weight + object.weight > capacity) break;
            Weight += object.weight;
            B += object.price;
            H++;
         }
         while (H < obj.size());
         B += (capacity - Weight) * (object.price / object.weight);
      }
   }
   

   
   public BBsolution kpbranchandboundsolve() {
      
      Collections.sort(objs, obje.ratio_sort());
      BBsolution sol= new BBsolution();
      treenode Bestsol = new treenode();
      treenode R = new treenode();
      R.boundcomputation();
      
      PriorityQueue<treenode> Priorityqueue = new PriorityQueue<>();
      Priorityqueue.offer(R);
      
      while (!Priorityqueue.isEmpty()) {
         treenode node = Priorityqueue.poll();
         
         if (node.B > Bestsol.price && node.height < objs.size() - 1) {
            
            treenode ADD = new treenode(node);
            obje object = objs.get(node.height);
            ADD.weight += object.weight;
            
            if (ADD.weight <= capacity) {
            
               ADD.obj.add(objs.get(node.height));
               ADD.price += object.price;
               ADD.boundcomputation();
               
               if (ADD.price > Bestsol.price) {
                  Bestsol = ADD;
               }
               if (ADD.B > Bestsol.price) {
                  Priorityqueue.offer(ADD);
               }
            }
            
            treenode DontADD = new treenode(node);
            DontADD.boundcomputation();
            
            if (DontADD.B > Bestsol.price) {
               Priorityqueue.offer(DontADD);
            }
         }
      }
      sol.price=Bestsol.price;
      sol.weight=Bestsol.weight;
      sol.objects=Bestsol.obj;
       return sol;
    }
}
public class kpbranchandbound {

   
    public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);
      System.out.println("enter the number of available objects");
      int Number = scanner.nextInt();
      System.out.println("enter the capacity");
      int capacity = scanner.nextInt();
      List<obje> objects = new LinkedList<>();
      for (int i = 0; i < Number; i++) {
         obje o = new obje();
         System.out.println("enter the id of the object");
         o.ID = scanner.nextInt();
         System.out.println("enter the price of the object");
         o.price = scanner.nextDouble();
         System.out.println("enter the weight of the object");
         o.weight = scanner.nextDouble();
         objects.add(o);
      } 
      branchandbound g= new branchandbound(objects, capacity);
      System.out.println(g.kpbranchandboundsolve().toString());
    }
    
}
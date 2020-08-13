
package pkg0.pkg1.kp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
class Obj{
   public int ID;
   public double price;
   public double weight;
     public static Comparator<Obj> IDsort() {
      return (Obj o1, Obj o2) -> o1.ID - o2.ID;
   }

}
class dynamic{
    public double storage[][];//2D matrix
    public List<Obj> objects;
    public int capacity;
  public dynamic(List<Obj> objects, int capacity) {
      this.objects = objects;
      this.capacity = capacity;
   }
    // Uses recursion with memoization
    public double getelement(int j, int i) {
   
      if (i < 0 || j < 0) //i and C must be greater than zero 
      return 0;
      Obj o; 
      o = objects.get(i);// returns element Number from the list
      double add,dontadd,element = storage[j][i];
      if (element == -1) { //not stored yet 
         if (o.weight > j) 
             add = -1; //if the weight is greater than the capacity, we can't add it
         else 
             add = o.price + getelement(j - (int) o.weight, i - 1); //remove the weight of the added object, and recurse to add the rest and calculate the price after addition
         dontadd = getelement(j, i - 1); //we don't choose the element
        element = Math.max(add, dontadd);//compare what is the best move, taking the element or leaving it    
         storage[j][i] = element; // Memoize the element
      }
      
      return element;
   }
    public Dprintsolution Keeptrack() {
   
      Dprintsolution dynamicsolution = new Dprintsolution();
      dynamicsolution.objects = new ArrayList<>();
      int Number = objects.size() - 1, C = capacity;
      double dontadd;
      while (Number >= 0) { // we have objects in the list
         Obj o = objects.get(Number);
         if(Number==0)
             dontadd=0;
         else
             dontadd = storage[C][Number - 1];
         
         if (storage[C][Number] != dontadd) //we add a solution because the next element differs which means that i took the previous element
         {
            dynamicsolution.objects.add(o); //add it to the list
            dynamicsolution.value += o.price; //add it's price to the total value
            dynamicsolution.weight += o.weight; //add the weight
            C= C-(int)o.weight; // remove the weight from the remaining capacity 
         }
         
         Number--;
      }
      
      return dynamicsolution;
   }
  public Dprintsolution Dsolve()
  {
      storage = new double[capacity + 1][objects.size()];
      for (int row = 0; row < capacity + 1; row++)
         for (int column = 0; column < objects.size(); column++)
            storage[row][column] = -1; //initialize the 2D array with -1
      
      getelement(capacity, objects.size() - 1);
      
      Dprintsolution dynamicsolution = Keeptrack();
      
      dynamicsolution.Info = "Dynamic Programming solution";
      return dynamicsolution;  
  }
}
class Dprintsolution
{
    String Info;
   public List<Obj> objects;
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
      
      Collections.sort(objects, Obj.IDsort());
      builder.append("the picked elements \n");
      objects.stream().map((o) -> {
          builder.append(o.ID);
           return o;
       }).forEachOrdered((Obj _item) -> {
           builder.append(" ");
      });
      
      return builder.toString();
   }

}

public class kpdynamic {
    public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);
      System.out.println("enter the number of available objects");
      int Number = scanner.nextInt();
      System.out.println("enter the capacity");
      int capacity = scanner.nextInt();
      List<Obj> objects = new LinkedList<>();
      for (int i = 0; i < Number; i++) {
         Obj o = new Obj();
         System.out.println("enter the id of the object");
         o.ID = scanner.nextInt();
         System.out.println("enter the price of the object");
         o.price = scanner.nextDouble();
         System.out.println("enter the weight of the object");
         o.weight = scanner.nextDouble();
         objects.add(o);
      } 
      dynamic d= new dynamic(objects, capacity);
      System.out.println(d.Dsolve().toString());
    }
}

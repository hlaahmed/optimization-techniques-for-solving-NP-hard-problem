
package pkg0.pkg1.kp;


import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
class obj{
   public int ID;
   public double price;
   public double weight;
   public static Comparator<obj> IDsort() {
      return (obj o1, obj o2) -> o1.ID - o2.ID;
   }

}
class Bprintsolution
{
    String Info;
   public List<obj> objects;
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
      
      Collections.sort(objects, obj.IDsort());
       builder.append("the picked elements \n");
      objects.stream().map((o) -> {
          builder.append(o.ID);
           return o;
       }).forEachOrdered((obj_item) -> {
           builder.append(" ");
      });
      
      return builder.toString();
   }
}

class Bruteforce
{
    public double storage[][];//2D matrix
    public List<obj> objects;
    public int capacity;
    public Bruteforce(List<obj> objects, int capacity) {
      this.objects = objects;
      this.capacity = capacity;
   }
    public List<List<obj>> partition(List<obj> objects) {
   
      List<List<obj>> partitions = new LinkedList<>();
      if (objects.isEmpty()) {  
         partitions.add(new LinkedList<>());
         return partitions;
      }
      obj Start = objects.get(0);
      List<List<obj>> Partitions = partition(objects.subList(1, objects.size()));
      Partitions.stream().map((List<obj> partition) -> {
          partitions.add(partition);
          return partition;
      }).map((partition) -> new LinkedList<>(partition)).map((concatenated) -> {
            concatenated.add(0, Start);
            return concatenated;
        }).forEachOrdered((concatenated) -> {
            partitions.add(concatenated);
        });
      return partitions;
   }
    public double CalcW(List<obj> objects) {
      double weight = 0;
      weight = objects.stream().map((object) -> object.weight).reduce(weight, (accumulator, _item) -> accumulator + _item);
      return weight;
   }
   
   public double Calcvalue(List<obj> objects) {
      double value = 0;
      value = objects.stream().map((object) -> object.price).reduce(value, (accumulator, _item) -> accumulator + _item);
      return value;
   }
       
    public Bprintsolution Bsolve() {
      Bprintsolution brute = new Bprintsolution();
      brute.objects = new LinkedList<>();
      partition(objects).forEach((part) -> {
          double weight = CalcW(part);
            if (weight <= capacity) { //the new part is below or equal the capacity limit
                double value = Calcvalue(part);
                if (value > brute.value) { //found a part with a better total value
                    brute.value = value;
                    brute.weight = weight;
                    brute.objects = part;
                }
            }
        });
      
      brute.Info = "we can use brute force to find the best feasible solution";
      return brute;
   }
}
public class kpBruteForce
{
    public static void main(String[] args) 
    {
      Scanner scanner = new Scanner(System.in);
      System.out.println("enter the number of available objects");
      int Number = scanner.nextInt();
      System.out.println("enter the capacity");
      int capacity = scanner.nextInt();
      
      List<obj> objects = new LinkedList<>();
      for (int i = 0; i < Number; i++) {
         obj o = new obj();
         System.out.println("enter the id of the object");
         o.ID = scanner.nextInt();
         System.out.println("enter the price of the object");
         o.price = scanner.nextDouble();
         System.out.println("enter the weight of the object");
         o.weight = scanner.nextDouble();
         objects.add(o);
      } 
      Bruteforce B= new Bruteforce(objects, capacity);
      System.out.println(B.Bsolve().toString());
      
    }
}

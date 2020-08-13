
package pkg0.pkg1.kp;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
class objee{
   public int ID;
   public double price;
   public double weight;
   public static Comparator<objee> IDsort() 
   {
      return (objee o1, objee o2) -> o1.ID - o2.ID;
   }

}
class divandconq
{
  public List<objee> objects;
  public int capacity;
  public divandconq(List<objee> objects, int capacity)
  {
      this.objects = objects;
      this.capacity = capacity;
   }
   double max(double first, double second)
   {
    if(first >= second)
        return first;
    else
        return second;
    }

    double divide(double capacity,int number,List<objee> objects)  
    {  
   
      if (capacity == 0 || number == 0)  //stopping condition
           return 0;  

       if (objects.get(number-1).weight > capacity)  
          return divide(capacity,number-1,objects);  

       else 
           return max( objects.get(number-1).price + divide(capacity-objects.get(number-1).weight,number-1,objects),  divide(capacity,number-1,objects) );
         
    } 
     public String toString(double value) {
      StringBuilder builder = new StringBuilder();
      builder.append(" the maximum value that can be achieved by divide and conquer: ");
      builder.append(value);
      return builder.toString();
   }
    

}

public class kpdivideandconquer {
    public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);
      System.out.println("enter the number of available objects");
      int Number = scanner.nextInt();
      System.out.println("enter the capacity");
      int capacity = scanner.nextInt();
      List<objee> objects = new LinkedList<>();
      for (int i = 0; i < Number; i++) {
         objee o = new objee();
         System.out.println("enter the id of the object");
         o.ID = scanner.nextInt();
         System.out.println("enter the price of the object");
         o.price = scanner.nextDouble();
         System.out.println("enter the weight of the object");
         o.weight = scanner.nextDouble();
         objects.add(o);
      } 
      divandconq d= new divandconq(objects, capacity);
      System.out.println(d.toString(d.divide(capacity, Number, objects)));
    }
}

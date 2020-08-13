import java.util.BitSet;
import java.util.Random;
import java.util.Scanner;

class objects {
	
	public int id;
	public int weight;
	public int price;
	public int getPrice() {
		return price;
	}	
}
 
class Knapsack {
	
	private int Capacity;
	private int weight;
	private int value;
	private BitSet kpcontents = new BitSet();
	public Knapsack()
        {
		
	}
	
	public Knapsack(Knapsack knapsack) {
		this.Capacity = knapsack.getCapacity();
		this.weight = knapsack.getWeight();
		this.value = knapsack.getValue();
		this.kpcontents = (BitSet) knapsack.getKpcontents().clone();
	}
	
	public BitSet getKpcontents() {
		return kpcontents;
	}

	public void setKpcontents(BitSet kpcontents) {
		this.kpcontents = kpcontents;
	}

	public int getCapacity() {
		return Capacity;
	}
	
	public void setCapacity(int Capacity) {
		this.Capacity = Capacity;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
}
 class problem {


	public objects[] availobjects;
	public int capacity;
        public Knapsack k;
	public problem( objects[] availobjects,int capacity) {
		
	
		this.availobjects = availobjects;
                this.capacity=capacity;
		
	}
	
	
}
public class kpsimulatedannealing {
	
	private problem P;
	private Knapsack kpstate;
	private Knapsack kpbest;
	private double temperature = 0;
	private final double factorofcooling = 0.96;
	private final double tempending = 2;
	private int increase; 
	
	public Knapsack findsolution(problem P) {
		this.P = P;
		kpstate = new Knapsack(P.k);
		kpbest = new Knapsack(P.k);
		increase = P.capacity * 5; 
		setInitState();
		temperature = setInitTemperature();
		while (temperature > tempending) {
			for (int m = 0; m < increase; m++) {
				kpstate = getNextState();
				
				if (kpstate.getValue() > kpbest.getValue())
					kpbest = kpstate;
			}
			
			cooling();
		}
		
		return kpbest;
	}
	
	private void setInitState() {
		kpstate.setKpcontents(BitSet.valueOf(new long[]{0}));
		kpstate.setValue(0);
		kpstate.setWeight(0);
	}
	private Knapsack getNextState() {
		Knapsack newKnapsack = new Knapsack();
		
		do {
			newKnapsack = findneighbour();
		} while (newKnapsack.getWeight() > P.k.getCapacity());
		
		int change = newKnapsack.getValue() - kpstate.getValue();
		
		if (change > 0)
			return newKnapsack;
		else {
			double x = Math.random();
			if (x < Math.exp(change/temperature))
				return newKnapsack;
			else
				return kpstate;
		}
	}
	
	private Knapsack findneighbour() {
		Knapsack newKnapsack = new Knapsack(kpstate);
		Random r = new Random();
		int x = r.nextInt(P.capacity);
		
		newKnapsack.getKpcontents().flip(x);
		calcprice(newKnapsack);
		calcweight(newKnapsack);
		
		return newKnapsack;
	}
	
	private void calcprice(Knapsack knapsack) {
		int i = -1, newPrice = 0;
		
		while ((i = knapsack.getKpcontents().nextSetBit(i + 1)) != -1) {
			newPrice += P.availobjects[i].price;
		}
		
		knapsack.setValue(newPrice);
	}
	
	private void calcweight(Knapsack knapsack) {
		int i = -1, newWeight = 0;
		
		while ((i = knapsack.getKpcontents().nextSetBit(i + 1)) != -1) {
			newWeight += P.availobjects[i].weight;
		}
		
		knapsack.setWeight(newWeight);
	}
	
	private double setInitTemperature() {
		int totalcost = 0, totalweight = 0;
		
		for (int i = 0; i < P.capacity; i++) {
			totalcost += P.availobjects[i].price;
			totalweight += P.availobjects[i].weight;
		}
		
		return (totalcost / P.capacity) / (totalweight / P.k.getCapacity()); 
	}
	
	private void cooling() {
		temperature = temperature * factorofcooling;
	}
        public static void main(String[] args) 
        {
	Scanner scanner = new Scanner(System.in);
        System.out.println("enter the number of available objects");
        int Number = scanner.nextInt();
        System.out.println("enter the capacity");
        int capacity = scanner.nextInt();
        objects[] Itempool= new objects[Number];
        for (int i = 0; i < Number; i++) 
        {
         Itempool[i]  = new objects();
         System.out.println("enter the id of the object");
         Itempool[i].id = scanner.nextInt();
         System.out.println("enter the price of the object");
         Itempool[i].price = scanner.nextInt();
         System.out.println("enter the weight of the object");
         Itempool[i].weight = scanner.nextInt();
        
         }  
         Knapsack k= new Knapsack();
         k.setCapacity(capacity);
	 problem inst = new problem(Itempool,Number);
         inst.k=k;
	kpsimulatedannealing sa = new kpsimulatedannealing();             
	Knapsack knapsack = sa.findsolution(inst);
	int appPrice = knapsack.getValue();
	System.out.println("solution of simulated annealing to this problem = "+knapsack.getValue());
	
	}	
}
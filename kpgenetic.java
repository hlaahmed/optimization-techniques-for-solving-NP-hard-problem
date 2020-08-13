
import java.util.Scanner;


class pop {
	kp[] bag;

    public pop(int popsize, boolean init) {
        bag=new kp[popsize];
            
        if (init) {
            for (int P = 0; P < popsize(); P++) {
            	kp bagnew = new kp();
                bagnew.individualcreation();
                addbag(P, bagnew);
            }
        }
    }
    
    public void addbag(int i, kp bag2) {
        bag[i]=bag2;
    }
    
    public kp getbag(int i) {
    	return bag[i];
    }

    public kp bestfit() {
    	kp bestfit = bag[0];
    	 for (int i = 1; i < popsize(); i++) {
             if (bestfit.getfit() <= getbag(i).getfit()) {
                 bestfit = getbag(i);
             }
         }
   
    	return bestfit;
    }

    public int popsize() {
    	return bag.length;
    }
}
class paramaters {
		public static int bagcapacity;
		public static int objectnumber;
		public static Objects[] choices;
}
class GA {


    private static final double mutationprobability = 0.015;
    private static final int sizetournment = 5;
    private static final boolean elite = true;


    public static pop evolution(pop pop) {
        pop newPopulation = new pop(pop.popsize(), false);

        int offset = 0;
        if (elite) {
            newPopulation.addbag(0, pop.bestfit());
            offset = 1;
        }

        for (int i = offset; i < newPopulation.popsize(); i++) {
            kp P1 = tselect(pop);
            kp P2 = tselect(pop);
            kp C = crossing(P1, P2);
            newPopulation.addbag(i, C);
        }

        for (int i = offset; i < newPopulation.popsize(); i++) {
            mutating(newPopulation.getbag(i));
        }

        return newPopulation;
    }

    public static kp crossing(kp P1, kp P2) {
        kp C = new kp();

        int position = (int) (Math.random() * P1.getobj().length);
        for (int i = 0; i < C.getobj().length; i++) {
        	if(i<position){
        		C.Wprocessing();
        		if(C.getWeight()+paramaters.choices[i].getWeight()<=C.getCapacity()){
        			C.setobj(i, P2.getobj(i));
        			C.Wprocessing();
        			}
        		
        	}else{
        		C.Wprocessing();
        		if(C.getWeight()+paramaters.choices[i].getWeight()<=C.getCapacity()){
        			C.setobj(i, P1.getobj(i));
        			C.Wprocessing();
        			}
        	}
        }
        
        
        
        
        C.processing();
        return C;
    }

    private static void mutating(kp bag) {
    	for(int i=0; i < bag.getobj().length; i++){
            if(Math.random() < mutationprobability){
                int mutationPos = (int) (bag.getobj().length * Math.random());
                if(bag.getobj()[mutationPos]){
                	bag.setobj(i, false);
                	bag.processing();
                	bag.Wprocessing();
                }else{
                	if(bag.getWeight()+paramaters.choices[mutationPos].getWeight()<=bag.getCapacity()){
                	bag.setobj(i, true);
                	bag.processing();
                	bag.Wprocessing();
                	}
                }
            }
        }
    }

    private static kp tselect(pop pop) {
        pop tour = new pop(sizetournment, false);
        for (int i = 0; i < sizetournment; i++) {
            int R = (int) (Math.random() * pop.popsize());
            tour.addbag(i, pop.getbag(R));
        }
        kp bestfit = tour.bestfit();
        return bestfit;
    }
}

class Objects {
	private int price;
	private int weight;
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int value) {
		this.price = value;
	}
	
	
}
class kp {
	private int capacity;
	private boolean[] obj;
	private int price;
	private int weight;

	public int getWeight() {
		return weight;
	}

	public void setW(int weight) {
		this.weight = weight;
	}

	public void processing(){
		price=0;
		for (int i= 0 ; i<paramaters.objectnumber;i++) {
			if(obj[i]){
				price+=paramaters.choices[i].getPrice();
			}
		}
	}
	
	
	public void Wprocessing(){
		weight=0;
		for (int i= 0 ; i<paramaters.objectnumber;i++) {
			if(obj[i]){
				weight+=paramaters.choices[i].getWeight();
			}
		}
	}
	
	public int getCapacity() {
		return capacity;
	}

	public void setC(int capacity) {
		this.capacity = capacity;
	}
	
	public boolean getobj(int i){
		return obj[i];
	}

	public void setobj(int i, boolean obj){
		this.obj[i]=obj;
		Wprocessing();
		processing();
	}
	
	public boolean[] getobj() {
		return obj;
	}

	public void newobj(int index, boolean object){
		this.obj[index]=object;
		Wprocessing();
		processing();
	}
	
	public void setobj(boolean[] objects) {
		this.obj = objects;
	}
	
	public kp(){
		setC(paramaters.bagcapacity);
		setP(0);
		setW(0);
		obj=new boolean[paramaters.objectnumber];
		for (boolean b : obj) {
			b=false;
		}
	}
	
	public kp(boolean[] B){
		setC(paramaters.bagcapacity);
		setP(0);
		setW(0);
		obj=B;
		processing();
		Wprocessing();
		if(weight>capacity){
			System.err.println("weight>capacity");
		}
	}

	public int getValue() {
		return price;
	}

	public void setP(int value) {
		this.price = value;
	}
	
	public void show(){
		System.out.println("capacite "+getCapacity());
		System.out.println("valeur "+getValue());
		System.out.println("weight "+getWeight());
		for (int i = 0; i < obj.length; i++) {
			if(obj[i]){
				System.out.print(i+" T | ");
			}else{
				System.out.print(i+" F | ");
			}
		}System.out.println("    ");
	}

	public void individualcreation(){
		
			for (int i=0 ;i<obj.length;i++) {
				if(Math.random()>0.5 && getWeight()+paramaters.choices[i].getWeight()<=capacity){
					obj[i]=true;
					setP(getValue()+paramaters.choices[i].getPrice());
					setW(getWeight()+paramaters.choices[i].getWeight());
				}
			}
		
	}
	
	public int getfit(){
		return price;
	}
}
public class kpgenetic {

    public static void main(String[] args) {
    	long stopwatch = System.currentTimeMillis();
    	paramaters Param = new paramaters();
		
		 Scanner scanner = new Scanner(System.in);
                  System.out.println("enter the number of available objects");
                  Param.objectnumber = scanner.nextInt();
                   System.out.println("enter the capacity");
                   Objects[] L = new Objects[Param.objectnumber];
                  Param.bagcapacity = scanner.nextInt();
		for (int i = 0; i < Param.objectnumber; i++) {
			L[i]=new Objects();
                        System.out.println("enter the price of the object");
                        L[i].setPrice(scanner.nextInt())  ;
                        System.out.println("enter the weight of the object");
                        L[i].setWeight(scanner.nextInt())  ;
                      
		}
		paramaters.choices=L;
		kp bag;
		bag=new kp();
		bag.individualcreation();
        pop pop = new pop(50, true);

        pop = GA.evolution(pop);
        for (int i = 0; i < 100; i++) {
            pop = GA.evolution(pop);
        }
        long stoptime = System.currentTimeMillis();
    	System.out.println("it takes " + (stoptime - stopwatch) + " milliseconds");
        System.out.println("Solution: ");
        pop.bestfit().show();
    }
}
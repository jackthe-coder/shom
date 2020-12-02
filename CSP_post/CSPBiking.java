import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CSPBiking extends CSP {
	
	static Set<Object> varBike   = new HashSet<Object>(Arrays.asList(new String[] {"black", "blue", "green", "red", "white"}));
	static Set<Object> varName   = new HashSet<Object>(Arrays.asList(new String[] {"Adrian", "Charles", "Henry", "Joel", "Richard"}));
	static Set<Object> varAge    = new HashSet<Object>(Arrays.asList(new String[] {"12years", "13years", "14years", "15years", "16years"}));
	static Set<Object> varJuice  = new HashSet<Object>(Arrays.asList(new String[] {"apple", "cranberry", "grapefruit", "orange", "pineapple"}));
	static Set<Object> varSand   = new HashSet<Object>(Arrays.asList(new String[] {"bacon", "chicken", "cheese", "pepperoni", "tuna"}));
	static Set<Object> varSport  = new HashSet<Object>(Arrays.asList(new String[] {"baseball", "basketball", "hockey", "soccer", "swimming"}));

	public boolean isGood(Object X, Object Y, Object x, Object y) {
		//if X is not even mentioned in by the constraints, just return true
		//as nothing can be violated
		if(!C.containsKey(X))
			return true;
		
		//check to see if there is an arc between X and Y
		//if there isn't an arc, then no constraint, i.e. it is good
		if(!C.get(X).contains(Y))
			return true;
		

		//Henry is exactly to the left of the soccer fan
		if(X.equals("Henry") && Y.equals("soccer") && (Integer)y-(Integer)x!=1)
			return false;
		
		//The one who likes swimming is next to the firned who likes baseball
		if(X.equals("swimming") && Y.equals("baseball") && Math.abs((Integer)x - (Integer)y)!= 1 )
			return false;

		//The boy who likes the sport played on ice is going to ear pepperoni sandwich
		if(X.equals("hockey") && Y.equals("pepperoni") && !x.equals(y))
			return false;
		
		//Joel is next to the 16-year-old cyclist.
		if(X.equals("Joel") && Y.equals("16years") && Math.abs((Integer)x-(Integer)y)!=1)
			return false;
		
		//Adrian is exactly to the left of the boy who is going to ear pepperoni sandwich.
		if(X.equals("Adrian") && Y.equals("pepperoni") && (Integer)y-(Integer)x!=1)
			return false;
		
		//The boy who is going to eat bacon sandwich is somewhere to the right of the owner of the white cicycle.
		if(X.equals("bacon") && Y.equals("white") && (Integer)y >= (Integer)x)
			return false;
		
		//The 16-year-old brought cheese sandwich
		if(X.equals("16years") && Y.equals("cheese") && !x.equals(y))
			return false;
		
		//The baseketball fan is next to the boy who is going to drink apple juice 
		if(X.equals("baseketball") && Y.equals("apple") && Math.abs((Integer)x-(Integer)y)!=1)
			return false;



		//Tenary constrints divided into binary 

		//The owner of the White bike is somewhere between the 15yearold boy and the youngest boy in that order
		if(X.equals("white") && Y.equals("15years") && (Integer)y >= (Integer)x )
			return false;
		if(X.equals("white") && Y.equals("12years") && (Integer)x >= (Integer)y )
			return false;
		
		//The boy who is going to drink grapefruit juice is somewhere between who brought tuna sandwich
		// and who brought pineapple juice in that order
		if(X.equals("grapefruit") && Y.equals("tuna") && (Integer)y >= (Integer)x)
			return false;
		if(X.equals("grapefruit") && Y.equals("pineapple") && (Integer)x >= (Integer)y)
			return false;

		//The cyclist that brought pineapple juice is somewhere between the 14year old and the boy 
		//who brought organge juice in that order
		if(X.equals("pineapple") && Y.equals("14years") && (Integer)y >= (Integer)x )
			return false;
		if(X.equals("pineapple") && Y.equals("orange") && (Integer)x >= (Integer)y )
			return false;

		//The boy riding the white bike is somewhere between the boys riding the blue and the black
		//bicycles in that order
		if(X.equals("white") && Y.equals("blue") && (Integer)y >= (Integer)x)
			return false;
		if(X.equals("white") && Y.equals("black") && (Integer)x >= (Integer)y)
			return false;

		//The 12year old is somewhere between the 14 year old and the oldest boy in that order
		if(X.equals("12years") && Y.equals("14years") && (Integer)y >= (Integer)x)
			return false;
		if(X.equals("12years") && Y.equals("16years") && (Integer)x >= (Integer)y)
			return false;

		//The cyclist riding the white bike is somewhere between richard and the boy boy riding the red bike
		if(X.equals("white") && Y.equals("Richard") && (Integer)y >= (Integer)x)
			return false;
		if(X.equals("white") && Y.equals("red") && (Integer)x >= (Integer)y)
			return false;

		//Charles is somewhere between Richard and adrian in that order
		if(X.equals("Charles") && Y.equals("Richard") && (Integer)y >= (Integer)x)
			return false;
		if(X.equals("Charles") && Y.equals("Adrian") && (Integer)x >= (Integer)y)
			return false;




		//Uniqueness constraints
		
    	if(varBike.contains(X) && varBike.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;
		
		if(varName.contains(X) && varName.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;
		
		if(varSand.contains(X) && varSand.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;
		
		if(varJuice.contains(X) && varJuice.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;
		
		if(varAge.contains(X) && varAge.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;

    	if(varSport.contains(X) && varSport.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;
		
		return true;
	}
		
	public static void main(String[] args) throws Exception {
		CSPBiking csp = new CSPBiking();
		
		Integer[] dom = {1,2,3,4,5};
		
		for(Object X : varBike) 
			csp.addDomain(X, dom);

		for(Object X : varAge) 
			csp.addDomain(X, dom);

		for(Object X : varName) 
			csp.addDomain(X, dom);

		for(Object X : varSand) 
			csp.addDomain(X, dom);

		for(Object X : varJuice) 
			csp.addDomain(X, dom);

		for(Object X : varSport) 
			csp.addDomain(X, dom);
		
		//unary constraints: just remove values from domains
		
		//Milk is drunk in the middle house.
		for(int i=1; i<=5; i++)
			if(i != 3)
				csp.D.get("baseball").remove(i);
		//The boy that is going to drink pineapple juice is at the fourth position 
		for(int i=1; i<=5; i++)
			if(i != 4)
				csp.D.get("pineapple").remove(i);

		//The cyclist who is going to eat tuna sandwich is at one of the ends
		for(int i=1; i<=5; i++)
			if(i != 1 && i !=5)
				csp.D.get("tuna").remove(i);

		//The boy riding the black bike is at the third position 
		for(int i=1; i<=5; i++)
			if(i != 3 )
				csp.D.get("black").remove(i);

		//In one of the ends is the boy riding the Green bicycle	
		for(int i=1; i<=5; i++)
			if(i != 1 && i !=5)
				csp.D.get("green").remove(i);			
		
		//13 years old at 5th position
		for(int i=1; i<=5; i++)
			if(i != 5)
				csp.D.get("13years").remove(i);
		
		// hockey is at 5th
		for(int i =1;i<=5;i++)
			if(i != 5)
				csp.D.get("hockey").remove(i);
		
		//binary constraints: add constraint arcs
		
		
		csp.addBidirectionalArc("Henry", "soccer");		
		
		
		csp.addBidirectionalArc("swimming", "baseball");
		
		
		csp.addBidirectionalArc("hockey", "pepperoni");
		
		
		csp.addBidirectionalArc("Joel", "16years");
		
		
		csp.addBidirectionalArc("Adrian", "pepperoni");
		
		
		csp.addBidirectionalArc("bacon", "white");
		
		
		csp.addBidirectionalArc("16years", "cheese");
		
		
		csp.addBidirectionalArc("baseball", "apple");


		csp.addBidirectionalArc("white", "15years");
		csp.addBidirectionalArc("white", "12years");

		csp.addBidirectionalArc("grapefruit", "tuna");
		csp.addBidirectionalArc("grapefruit", "pineapple");

		csp.addBidirectionalArc("pineapple", "14years");
		csp.addBidirectionalArc("pineapple", "orange");

		csp.addBidirectionalArc("white", "blue");
		csp.addBidirectionalArc("white", "black");	

		csp.addBidirectionalArc("12years", "14years");	
		csp.addBidirectionalArc("12years", "16years");

		csp.addBidirectionalArc("white", "Richard");
		csp.addBidirectionalArc("white", "red");

		csp.addBidirectionalArc("Charles", "Richard");
		csp.addBidirectionalArc("Charles", "Adrian");

		//Uniqueness constraints
		
		for(Object X : varBike)
			for(Object Y : varBike)
				csp.addBidirectionalArc(X,Y);
		
		for(Object X : varName)
			for(Object Y : varName)
				csp.addBidirectionalArc(X,Y);

		for(Object X : varAge)
			for(Object Y : varAge)
				csp.addBidirectionalArc(X,Y);

		for(Object X : varJuice)
			for(Object Y : varJuice)
				csp.addBidirectionalArc(X,Y);

		for(Object X : varSand)
			for(Object Y : varSand)
				csp.addBidirectionalArc(X,Y);

		for(Object X : varSport)
			for(Object Y : varSport)
				csp.addBidirectionalArc(X,Y);


		//Now let's search for solution 
		
		Search search = new Search(csp);
		System.out.println(search.BacktrackingSearch());
	}
}
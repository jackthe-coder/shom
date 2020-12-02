import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CSPZebra extends CSP {
	
	static Set<Object> varCol = new HashSet<Object>(Arrays.asList(new String[] {"blue", "green", "ivory", "red", "yellow"}));
	static Set<Object> varDri = new HashSet<Object>(Arrays.asList(new String[] {"coffee", "milk", "orange-juice", "tea", "water"}));
	static Set<Object> varNat = new HashSet<Object>(Arrays.asList(new String[] {"englishman", "japanese", "norwegian", "spaniard", "ukrainian"}));
	static Set<Object> varPet = new HashSet<Object>(Arrays.asList(new String[] {"dog", "fox", "horse", "snails", "zebra"}));
	static Set<Object> varCig = new HashSet<Object>(Arrays.asList(new String[] {"chesterfield", "kools", "lucky-strike", "old-gold", "parliament"}));
	
	public boolean isGood(Object X, Object Y, Object x, Object y) {
		//if X is not even mentioned in by the constraints, just return true
		//as nothing can be violated
		if(!C.containsKey(X))
			return true;
		
		//check to see if there is an arc between X and Y
		//if there isn't an arc, then no constraint, i.e. it is good
		if(!C.get(X).contains(Y))
			return true;
		
		//The Englishman lives in the red house.
		if(X.equals("englishman") && Y.equals("red") && !x.equals(y))
			return false;
		
		//The Spaniard owns a dog.
		if(X.equals("spaniard") && Y.equals("dog") && !x.equals(y))
			return false;

		//Coffee is drunk in the green house.
		if(X.equals("coffee") && Y.equals("green") && !x.equals(y))
			return false;
		
		//The Ukrainian drinks tea.
		if(X.equals("ukrainian") && Y.equals("tea") && !x.equals(y))
			return false;
		
		//The green house is directly to the right of the ivory house.
		if(X.equals("green") && Y.equals("ivory") && (Integer)x-(Integer)y!=1)
			return false;
		
		//The Old-Gold smoker owns snails.
		if(X.equals("old-gold") && Y.equals("snails") && !x.equals(y))
			return false;
		
		//Kools are being smoked in the yellow house.
		if(X.equals("kools") && Y.equals("yellow") && !x.equals(y))
			return false;
		
		//The Chesterfield smoker lives next to the fox owner.
		if(X.equals("chesterfield") && Y.equals("fox") && Math.abs((Integer)x-(Integer)y)!=1)
			return false;
		
		//Kools are smoked in the house next to the house where the horse is kept.
		if(X.equals("kools") && Y.equals("horse") && Math.abs((Integer)x-(Integer)y)!=1)
			return false;
		
		//The Lucky-Strike smoker drinks orange juice.
		if(X.equals("lucky-strike") && Y.equals("orange-juice") && !x.equals(y))
			return false;
		
		//The Japanese smokes Parliament.
		if(X.equals("japanese") && Y.equals("parliament") && !x.equals(y))
			return false;
		
		//The Norwegian lives next to the blue house. 
		if(X.equals("norwegian") && Y.equals("blue") && Math.abs((Integer)x-(Integer)y)!=1)
			return false;
		
		
		//Uniqueness constraints
		
		if(varCol.contains(X) && varCol.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;
		
		if(varDri.contains(X) && varDri.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;
		
		if(varNat.contains(X) && varNat.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;
		
		if(varPet.contains(X) && varPet.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;
		
		if(varCig.contains(X) && varCig.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;

		
		return true;
	}
		
	public static void main(String[] args) throws Exception {
		CSPZebra csp = new CSPZebra();
		
		Integer[] dom = {1,2,3,4,5};
		
		for(Object X : varCol) 
			csp.addDomain(X, dom);
		
		for(Object X : varDri) 
			csp.addDomain(X, dom);
		
		for(Object X : varNat) 
			csp.addDomain(X, dom);
		
		for(Object X : varPet) 
			csp.addDomain(X, dom);
		
		for(Object X : varCig) 
			csp.addDomain(X, dom);
		
		
		//unary constraints: just remove values from domains
		
		//Milk is drunk in the middle house.
		for(int i=1; i<=5; i++)
			if(i != 3)
				csp.D.get("milk").remove(i);
				
		//The Norwegian lives in the first house on the left.
		for(int i=1; i<=5; i++)
			if(i != 1)
				csp.D.get("norwegian").remove(i);
		
		
		//binary constraints: add constraint arcs
		
		//The Englishman lives in the red house. 
		csp.addBidirectionalArc("englishman", "red");		
		
		//The Spaniard owns a dog.
		csp.addBidirectionalArc("spaniard", "dog");
		
		//Coffee is drunk in the green house.
		csp.addBidirectionalArc("coffee", "green");
		
		//The Ukrainian drinks tea. 
		csp.addBidirectionalArc("ukrainian", "tea");
		
		//The green house is directly to the right of the ivory house. 
		csp.addBidirectionalArc("green", "ivory");
		
		//The Old-Gold smoker owns snails.
		csp.addBidirectionalArc("old-gold", "snails");
		
		//Kools are being smoked in the yellow house.
		csp.addBidirectionalArc("kools", "yellow");
		
		//The Chesterfield smoker lives next to the fox owner. 
		csp.addBidirectionalArc("chesterfield", "fox");
		
		//Kools are smoked in the house next to the house where the horse is kept. 
		csp.addBidirectionalArc("kools", "horse");
		
		//The Lucky-Strike smoker drinks orange juice.
		csp.addBidirectionalArc("lucky-strike", "orange-juice");
		
		//The Japanese smokes Parliament. 
		csp.addBidirectionalArc("japanese", "parliament");
		
		//The Norwegian lives next to the blue house. 
		csp.addBidirectionalArc("norwegian", "blue");


		//Uniqueness constraints
		
		for(Object X : varCol)
			for(Object Y : varCol)
				csp.addBidirectionalArc(X,Y);
		
		for(Object X : varDri)
			for(Object Y : varDri)
				csp.addBidirectionalArc(X,Y);

		for(Object X : varNat)
			for(Object Y : varNat)
				csp.addBidirectionalArc(X,Y);

		for(Object X : varPet)
			for(Object Y : varPet)
				csp.addBidirectionalArc(X,Y);

		for(Object X : varCig)
			for(Object Y : varCig)
				csp.addBidirectionalArc(X,Y);


		//Now let's search for solution 
		
		Search search = new Search(csp);
		System.out.println(search.BacktrackingSearch());
	}
}
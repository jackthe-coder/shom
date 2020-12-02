import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Search {
	CSP csp;
	
	public Search(CSP csp) { this.csp = csp; }
	
	//returns an assignment
	public Map<Object, Object> BacktrackingSearch() {
		//create an empty assignment
		Map<Object, Object> assignment = new TreeMap<>();
		return Backtrack(assignment, csp.D);
	}
	
	//We denote assignment by A
	Map<Object, Object> Backtrack(Map<Object, Object> A, Map<Object,Set<Object>> D) {
				
		if( isComplete(A,D) )
			return A;
		Object X = SelectUnassignedVariable(A,D);
		for(Object x : D.get(X)) 
			if(isConsistent(X,x,A)) {
				Map<Object, Object> AA = assign(X,x,A);
				Map<Object,Set<Object>> DD = FC(X,x,AA,D);
				
				Map<Object, Object> result = Backtrack(AA,DD);
				if(result!=null) 
					return result;
			}
		
		return null;
	}
	
	//adding a new variable assignment to A
	Map<Object, Object> assign(Object X, Object x, Map<Object, Object> A) {
		Map<Object, Object> AA = copyA(A);
		AA.put(X, x);
		return AA;
	}
	
	//Forward Checking
	Map<Object,Set<Object>> FC(Object X, Object x, Map<Object, Object> A, Map<Object,Set<Object>> D) {
		Map<Object,Set<Object>> DD = copyD(D);
		
		//now do forward checking and record deleted values for neighboring variable Y 
		if(!csp.C.containsKey(X))
			return DD;
		
		Map<Object, Set<Object>> deletedValues = new TreeMap<>();
		for(Object Y : csp.C.get(X)) {
			if(A.containsKey(Y))
				continue;
			
			for(Object y : DD.get(Y))
				if(!csp.isGood(X, Y, x, y)) {
					
					if(!deletedValues.containsKey(Y))
						deletedValues.put(Y, new TreeSet<Object>());
					
					deletedValues.get(Y).add(y);
				}
		}
		
		//do the real value deletions from variable domains in csp
		for(Object Y : deletedValues.keySet()) 
			DD.get(Y).removeAll(deletedValues.get(Y));
		
		return DD;
	}
	
	
	boolean isConsistent(Object X, Object x, Map<Object, Object> A) {
		for(Object Y : A.keySet()) {
			Object y = A.get(Y);
		
			if(!csp.isGood(X,Y,x,y))
				return false;
			
			if(!csp.isGood(Y,X,y,x))
				return false;
		}
		return true;
	}
	
	Object SelectUnassignedVariable(Map<Object, Object> A, Map<Object,Set<Object>> D) {
		//Implements minimum remaining values (MRV) 
		int min = Integer.MAX_VALUE;
		Object Xmin = null;
		
		for(Object X : D.keySet()) {
			if (A.containsKey(X))
				continue;
		
			if (D.get(X).size() < min) {
				min = D.get(X).size();
				Xmin = X;
			}
		}
		
		return Xmin;
	}
	
	boolean isComplete(Map<Object, Object> A, Map<Object,Set<Object>> D) {
		if(A.size() == D.size())
			return true;
		
		return false;
	}
	
	
	//for copying assignment
	Map<Object, Object> copyA(Map<Object, Object> A) {
		Map<Object, Object> AA = new TreeMap<>();
		for(Object X: A.keySet())
			AA.put(X, A.get(X));
		return AA;
	}
	
	//for copying domains
	Map<Object,Set<Object>> copyD(Map<Object,Set<Object>> D) {
		Map<Object,Set<Object>> DD = new TreeMap<>(D);
		for(Object X: D.keySet()) {
			DD.put(X, new TreeSet<>());
			for(Object x: D.get(X))
				DD.get(X).add(x);
		}
		return DD;
	}
}
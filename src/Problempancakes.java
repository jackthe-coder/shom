import java.util.HashSet;
import java.util.Set;

public class Problempancakes extends Problem {

	static final int n = 6;
    static final int ssBig  = 5;
	static final int sLarge= 4; 
	static final int large= 3;
	static final int mid = 2;
	static final int small = 1;
    static final int sssmall = 0;
    //0 means didnot get over the river
    //1 means get over the river
boolean goal_test(Object state){
	StateofPancakes goal = (StateofPancakes) state;

	int k = 0; 
	int cnt = 0;
	for (int i = 0; i<goal.N ; i++){
		if (goal.curr_state[i]==k){
			cnt++;
		}
		k++;
	}

	if(cnt==6){
		return true;
	}else{
		return false;
	}
	
}
Set<Object> getSuccessors(Object state) {
	
    Set<Object> set = new HashSet<Object>();
    StateofPancakes s = (StateofPancakes) state;
    //int i0 = s.i0, j0 = s.j0;
    
    StateofPancakes ss; //successor state
        //Only farmer on the boat!
    int cnt = 2;
    while(s.N>=cnt){
    //flip N pancakes iterate until the length
    	ss = new StateofPancakes(s);
   		for(int i = 0; i<cnt; i++){
   			ss.curr_state[i]=s.curr_state[cnt-1-i];
   		}
   		cnt++;
   		set.add(ss);
   	}    
    return set;
}

double step_cost(Object fromState, Object toState) { 
    return 1; 
}

public double h(Object state){
	return 0;
}

public static void main(String[] args) throws Exception {

	Problempancakes problem = new Problempancakes();
	//1 represent 
	int[] init = {0,1,3,5,2,4};
	problem.initialState = new StateofPancakes(init); 
	
	Search search  = new Search(problem);
	
	System.out.println("TreeSearch------------------------");
	//System.out.println("BreadthFirstTreeSearch:\t\t" + search.BreadthFirstTreeSearch());
	//System.out.println("UniformCostTreeSearch:\t\t" + search.UniformCostTreeSearch());
	//System.out.println("DepthFirstTreeSearch:\t\t" + search.DepthFirstTreeSearch());
	//System.out.println("GreedyBestFirstTreeSearch:\t" + search.GreedyBestFirstTreeSearch());
	//System.out.println("AstarTreeSearch:\t\t" + search.AstarTreeSearch());
	
	System.out.println("\n\nGraphSearch----------------------");
	System.out.println("BreadthFirstGraphSearch:\t" + search.BreadthFirstGraphSearch());
	System.out.println("UniformCostGraphSearch:\t\t" + search.UniformCostGraphSearch());
	//System.out.println("DepthFirstGraphSearch:\t\t" + search.DepthFirstGraphSearch());
	//System.out.println("GreedyBestGraphSearch:\t\t" + search.GreedyBestFirstGraphSearch());
	System.out.println("AstarGraphSearch:\t\t" + search.AstarGraphSearch());
	
	System.out.println("\n\nIterativeDeepening----------------------");
	System.out.println("IterativeDeepeningTreeSearch:\t" + search.IterativeDeepeningTreeSearch());
	System.out.println("IterativeDeepeningGraphSearch:\t" + search.IterativeDeepeningGraphSearch());
}

}
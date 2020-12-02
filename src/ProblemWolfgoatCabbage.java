import java.util.HashSet;
import java.util.Set;

public class ProblemWolfgoatCabbage extends Problem {

		int farmer = 0;
		int wolf = 1;
		int goat = 2;
		int cabbage = 3;

	public boolean goal_test(Object state) {

		StateWolfgoatCabbage goal = (StateWolfgoatCabbage)state;
        
        if (goal.curr_state[farmer]==1&&goal.curr_state[wolf]==1&&goal.curr_state[goat]==1&&goal.curr_state[cabbage]==1){
        	return true;
        }else{
        	return false;
        }
        
	}

	Set<Object> getSuccessors(Object state) {
    	
        Set<Object> set = new HashSet<Object>();
        StateWolfgoatCabbage s = (StateWolfgoatCabbage)state;
 		
        StateWolfgoatCabbage ss; //successor state

        boolean check_state = constraint(s);
        
        //move farmer to destination
        ss = new StateWolfgoatCabbage(s);
        if (check_state = true){
        	ss.curr_state[farmer] = 1;
        	set.add(ss);
        }
        
        //move famer back to origin 
        ss = new StateWolfgoatCabbage(s);
        if (check_state = true){
        	ss.curr_state[farmer] = 0;
        	set.add(ss);
        }

        //move farmer and wolf 
        ss = new StateWolfgoatCabbage(s);
        if (check_state = true){
        	ss.curr_state[farmer] = 1;
        	ss.curr_state[wolf] = 1;
        	set.add(ss);
        }

        // move farmer and goat 
        ss = new StateWolfgoatCabbage(s);
        if (check_state = true){
        	ss.curr_state[farmer] = 1;
        	ss.curr_state[goat] = 1;
        	set.add(ss);
        }
        
        // move farmer and cabbage
        ss = new StateWolfgoatCabbage(s);
        if (check_state = true){
        	ss.curr_state[farmer] = 1;
        	ss.curr_state[cabbage] = 1;
        	set.add(ss);
        }

  		//move farmer and wolf back to origin
  		ss = new StateWolfgoatCabbage(s);
        if (check_state = true){
        	ss.curr_state[farmer] = 0;
        	ss.curr_state[wolf] = 0;
        	set.add(ss);
        }

        //move farmer and goat back to origin 
        ss = new StateWolfgoatCabbage(s);
        if (check_state = true){
        	ss.curr_state[farmer] = 1;
        	ss.curr_state[goat] = 1;
        	set.add(ss);
        }


        //move farmer and cabbage back to origin 
        ss = new StateWolfgoatCabbage(s);
        if (check_state = true){
        	ss.curr_state[farmer] = 0;
        	ss.curr_state[cabbage] = 0;
        	set.add(ss);
        }
        
        return set;
    }



    public boolean constraint (StateWolfgoatCabbage state){

    	if (state.curr_state[farmer] == 0 && state.curr_state[goat] == 1 && state.curr_state[wolf]==1){
    		return false;
    	}
    	if (state.curr_state[farmer] == 0 && state.curr_state[goat] == 1 && state.curr_state[cabbage]==1){
    		return false;
    	}

    	if (state.curr_state[farmer] == 1 && state.curr_state[goat] == 0 && state.curr_state[wolf]==0){
    		return false;
    	}

    	if (state.curr_state[farmer] == 1 && state.curr_state[goat] == 0 && state.curr_state[cabbage]==0){
    		return false;
    	}

    return true;

    }
	
	double step_cost(Object fromState, Object toState) { return 1; }

	public double h(Object state) { return 0; }

	public static void main(String[] args) throws Exception {
		ProblemWolfgoatCabbage problem = new ProblemWolfgoatCabbage();
		int [] curr_state = new int[4];
		problem.initialState = new StateWolfgoatCabbage(curr_state); 
		
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
		//System.out.println("IterativeDeepeningTreeSearch:\t" + search.IterativeDeepeningTreeSearch());
		System.out.println("IterativeDeepeningGraphSearch:\t" + search.IterativeDeepeningGraphSearch());
	}
}



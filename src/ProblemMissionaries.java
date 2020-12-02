import java.util.HashSet;
import java.util.Set;

public class ProblemMissionaries extends Problem {
    
	boolean goal_test(Object state) {
        StateMissionaries goal = (StateMissionaries)state;

        if (goal.missionariesDest==3&&goal.cannibalsDest==3){
            return true;
        }else{
            return false;
        }
	}
  
    Set<Object> getSuccessors(Object state) {
    	
        Set<Object> set = new HashSet<Object>();
        StateMissionaries s = (StateMissionaries) state;
        
        
        StateMissionaries ss; //successor state


        boolean c = check(s);

        //carry 1 missionaries
        ss = new StateMissionaries(s);
        if (ss.missionaries>0&&c == true){
            ss.missionaries --;
            ss.missionariesDest ++;
            set.add(ss);
        }

        //carry 2 missionaries
        ss = new StateMissionaries(s);
        if (ss.missionaries>1&&c == true){
            ss.missionaries = ss.missionaries - 2;
            ss.missionariesDest = ss.missionariesDest + 2;
            set.add(ss);
        }

        //carry 1 cannibals
        ss = new StateMissionaries(s);
        if (ss.cannibals>0&&c == true){
            ss.cannibals --;
            ss.cannibalsDest ++;
            set.add(ss);
        }

        //carry 2 cannibals
        ss = new StateMissionaries(s);
        if (ss.cannibals>1&&c == true){
            ss.cannibals = ss.cannibalsDest - 2;
            ss.cannibalsDest = ss.cannibalsDest + 2;
            set.add(ss);
        }

        //carry 1 missionaries back
        ss = new StateMissionaries(s);
        if (ss.missionariesDest>0&&c == true){
            ss.missionaries ++;
            ss.missionariesDest --;
            set.add(ss);
        }

        // carry 2 missionaries back 
        ss = new StateMissionaries(s);
        if (ss.missionariesDest>1&&c == true){
            ss.missionaries = ss.missionaries + 2;
            ss.missionariesDest = ss.missionariesDest - 2;
            set.add(ss);
        }
        //carry 1 cannibals back
        ss = new StateMissionaries(s);
        if (ss.cannibalsDest>0&&c == true){
            ss.cannibals ++;
            ss.cannibalsDest --;
            set.add(ss);
        }

        //carry 2 cannibals back 
        ss = new StateMissionaries(s);
        if (ss.cannibalsDest>1&&c == true){
            ss.cannibals = ss.cannibals + 2;
            ss.cannibalsDest = ss.cannibalsDest - 2;
            set.add(ss);
        }

        //carry 1 missionaries & 1 cannibals 
        ss = new StateMissionaries(s);
        if (ss.missionaries>0&&ss.cannibals>0&&c == true){
            ss.missionaries --;
            ss.missionariesDest ++;
            ss.cannibals --;
            ss.cannibalsDest ++;
            set.add(ss);
        }
        //carry 1 missionaries & 1 cannibals back 
        ss = new StateMissionaries(s);
        if (ss.missionariesDest>0&&ss.cannibalsDest>0&&c == true){
            ss.missionaries ++;
            ss.missionariesDest --;
            ss.cannibals ++;
            ss.cannibalsDest --;
            set.add(ss);
        }
        return set;
    }
	
	double step_cost(Object fromState, Object toState) { return 1; }

	public double h(Object state) { return 0; }

    public boolean check(StateMissionaries state){

        if (state.missionaries<state.cannibals){
            return false;
        }

        if (state.missionariesDest<state.cannibalsDest){
            return false;
        }

        return true;


    }

	public static void main(String[] args) throws Exception {
		ProblemMissionaries problem = new ProblemMissionaries();
		int missionaries = 3;
        int cannibals = 3;
		problem.initialState = new StateMissionaries(missionaries,cannibals); 
		
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

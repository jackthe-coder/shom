public class StateMissionaries{
    
    int missionaries;
    int cannibals;
    int missionariesDest = 0;
    int cannibalsDest = 0;
    
    public StateMissionaries(int missionaries, int cannibals) { 
        this.missionaries = missionaires;
        this.cannibals = cannibals;
    }
    
    //It has to be a copy of values not reference because we will 
    //create many states and don't want to overwrite the same array.
    public StateMissionaries(StateMissionaries state) {
    	missionaires=state.missionaires;
        cannibals = state.cannibals;
        missionariesDest = state.missionariesDest;
        cannibalsDest = state.cannibalsDest;
    }

    
    public int hashCode() {
       // return curr_state[0]*1000 + curr_state[1]*100 + curr_state[2]*10 + curr_state[3];
    }
    
    public String toString() {
    	   String s = "";
           s = s+" M: "+missionaires+" "+"C: "+cannibals+" ======== "+" M: "+missionariesDest+" C: "+cannibalsDest+" ";
           return s;
    }
}
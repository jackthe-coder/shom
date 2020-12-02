public class StateMissionaries{
    
    int missionaries;
    int cannibals;
    int missionariesDest = 0;
    int cannibalsDest = 0;
    
    public StateMissionaries(int missionaries, int cannibals) { 
        this.missionaries = missionaries;
        this.cannibals = cannibals;
    }
    
    //It has to be a copy of values not reference because we will 
    //create many states and don't want to overwrite the same array.
    public StateMissionaries(StateMissionaries state) {
    	missionaries=state.missionaries;
        cannibals = state.cannibals;
        missionariesDest = state.missionariesDest;
        cannibalsDest = state.cannibalsDest;
    }

    
    public int hashCode() {
       return missionaries*1000 + missionariesDest*100 + cannibals*10 + cannibalsDest;
    }
    
    public String toString() {
    	   String s = "";
           s = s+" M: "+missionaries+" "+"C: "+cannibals+" ======== "+" M: "+missionariesDest+" C: "+cannibalsDest+"     ";
           return s;
    }
}
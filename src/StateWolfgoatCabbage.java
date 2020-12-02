public class StateWolfgoatCabbage
{    
    
    //current state value 
    int [] curr_state = new int[4];

    //represented number of each objects 
    int farmer = 0;
    int wolf = 1;
    int goat = 2;
    int cabbage = 3;


    
    public StateWolfgoatCabbage(int [] curr_state) { 
        this.curr_state = curr_state;
    }
    
    //It has to be a copy of values not reference because we will 
    //create many states and don't want to overwrite the same array.
    public StateWolfgoatCabbage(StateWolfgoatCabbage state) {
    	for(int i=0; i<4; i++){
            this.curr_state[i]=state.curr_state[i];
        }
    }

    
    public int hashCode() {
        return curr_state[0]*1000 + curr_state[1]*100 + curr_state[2]*10 + curr_state[3];
    }
    
    public String toString() {
    	   String s = "";
           for (int i = 0; i<4; i++){
                s= s+curr_state[i]+" ";
           }
           return s;
    }
}
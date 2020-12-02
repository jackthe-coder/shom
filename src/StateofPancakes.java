public class StateofPancakes{    
    int curr_state[];
    int N;
    
public StateofPancakes(int[] curr_state) { 
    this.curr_state = curr_state; 
    N = curr_state.length;
}

//It has to be a copy of values not reference because we will 
//create many states and don't want to overwrite the same array.
public StateofPancakes(StateofPancakes state) {
    N = state.N;
	curr_state = new int[N];
    for(int i=0; i<N; i++) 
        curr_state[i] = state.curr_state[i];
}



public int hashCode() {
        return curr_state[0]*100000 + curr_state[1]*10000 + curr_state[2]*1000 + curr_state[3]*100+curr_state[4]*10+curr_state[5];
}    
    
public String toString() {

    String s = "";
    for (int i=0; i<N; i++){
        s = s+" "+curr_state[i];
        
    }
    s = s+" ,";

    return s;
}

}
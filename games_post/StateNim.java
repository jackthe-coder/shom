public class StateNim extends State{

	public int init_num;


	public StateNim(){

		this.init_num = 13;

		player = 1;

	}

	public StateNim(StateNim state){

		this.init_num = state.init_num;

		player = state.player;

	}

	public String toString(){

		String s = "";

		s += init_num;

	return s;
	}
}
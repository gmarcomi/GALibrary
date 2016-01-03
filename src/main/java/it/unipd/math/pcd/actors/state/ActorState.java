package it.unipd.math.pcd.actors.state;

import it.unipd.math.pcd.actors.Message;

public class ActorState implements ExecutionState {
	private boolean occupy=false;
	@Override
	public void signal(Message message) {
		// TODO Auto-generated method stub

	}
	@Override
	public boolean isOccupy() {
		return occupy;
	}
	
	public void setOccupy(boolean occupy){
		this.occupy=occupy;
	}

}

package it.unipd.math.pcd.actors;

public abstract class AbsActorRef<T extends Message> implements ActorRef<T> {
	
	@Override
	public int compareTo(ActorRef arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void send(T message, ActorRef to) {
		// TODO Auto-generated method stub
		
	}

}

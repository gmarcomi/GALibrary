package it.unipd.math.pcd.actors.message;

import it.unipd.math.pcd.actors.ActorRef;
import it.unipd.math.pcd.actors.Message;


public class IntMessage extends AbsMessage<Integer> {
	
	public IntMessage(Integer payload, ActorRef<Message> target) {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void tag(Integer payload, ActorRef<Message> target) {
		// TODO Auto-generated method stub
		
	}

}

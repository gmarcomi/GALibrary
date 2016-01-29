package it.unipd.math.pcd.actors;

public interface ActionActor<T extends Message> {
	void addMessage(T message,ActorRef<T> sender);
	void stop();
}

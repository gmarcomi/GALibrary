/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2015 Riccardo Cardin
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * <p/>
 */

package it.unipd.math.pcd.actors;

import it.unipd.math.pcd.actors.exceptions.NoSuchActorException;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A map-based implementation of the actor system.
 * @author Gabriele Marcomin
 * @author Riccardo Cardin
 * @version 1.2
 * @since 1.0
 *
 */
public abstract class AbsActorSystem implements ActorSystem {
  /**
  * Associates every Actor created with an identifier.
  */
  private Map<ActorRef<? extends Message>, Actor<? extends Message>> actors;
    
  public AbsActorSystem() {
    actors = new HashMap<ActorRef<? extends Message>, Actor<? extends Message>>();
  }
  
  @Override
  public ActorRef<? extends Message> actorOf(Class<? extends Actor> actor, ActorMode mode) {
    // ActorRef instance
    ActorRef<?> reference;
    try {
      // Create the reference to the actor
      reference = this.createActorReference(mode);
      // Create the new instance of the actor 
      Actor actorInstance = ((AbsActor) actor.newInstance()).setSelf(reference);
      // Associate the reference to the actor
      actors.put(reference, actorInstance); 
    }
    catch (InstantiationException | IllegalAccessException e) {
     throw new NoSuchActorException(e);
    }
    return reference;
  }

  @Override
  public ActorRef<? extends Message> actorOf(Class<? extends Actor> actor) {
    return this.actorOf(actor, ActorMode.LOCAL);
  }

  protected abstract ActorRef createActorReference(ActorMode mode);
    
  @Override
  public void stop(ActorRef<?> actor) {
    AbsActor stoppedActor = null; 
    synchronized (actors) {
      stoppedActor = (AbsActor) actors.get(actor);
    }
    if (stoppedActor != null) {
      stoppedActor.stop();
    }
    else {
      throw new NoSuchActorException();
    }
    /* the MainBoxManager thread of stoppedActor keeps the reference of his owner Actor,
     * until its death
     */
    synchronized (actors) {
      actors.remove(actor);
    }
  }
    
    @Override
    public void stop() {
    	//other operation aren't allowed on actors
    	synchronized (actors) {
    		for (Entry<ActorRef<?>, Actor<?>> entry : actors.entrySet()){
        		ActorRef<?> actorRef = entry.getKey();
        	    stop(actorRef);
        	}
		}
    	
    }
    /**
     * Retrieves the corresponding Actor of {@code ref}
     * @param ref The reference of ActorRef
     * @return The corresponding Actor
     */
    public Actor<? extends Message> getActor(ActorRef ref){
    	Actor<? extends Message> tmp=null;
    	//one access to map at time
    	synchronized (actors) {
			tmp=actors.get(ref);
		}
    	//the searched ref don't exist, stopped or less
    	if(tmp == null)
    		throw new NoSuchActorException();
    	else{
    		//testing interrupted error
    		AbsActor<? extends Message> temp = (AbsActor<? extends Message>) tmp;
    		if(temp.getInterrupted()){
    			//stop the interrupted actor and throw the exception
    			stop(ref);
    			throw new NoSuchActorException();
    		}
    	}
    	return tmp;
    }
}
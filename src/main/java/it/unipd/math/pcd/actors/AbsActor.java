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


import it.unipd.math.pcd.actors.mailbox.MailBoxManager;
/**
 * Defines common properties of all actors.
 * 
 * @author Gabriele Marcomin
 * @author Riccardo Cardin
 * @version 1.1
 * @since 1.0
 * 
 */
public abstract class AbsActor<T extends Message> implements Actor<T>, ActionActor<T>{
    /**
     * Self-reference of the actor
     */
    protected ActorRef<T> self;
    /**
     * Sender of the current message
     */
    protected ActorRef<T> sender;
    /**
     * The manager of the messages
     */
    private MailBoxManager<T> receiver;
    public AbsActor(){
    	receiver= new MailBoxManager<T>(this);
    	Thread lp = new Thread(receiver);
    	lp.start();
    }
    /**
     * Sets the self-reference.
     *
     * @param self The reference to itself
     * @return The actor.
     */
    protected final Actor<T> setSelf(ActorRef<T> self) {
        this.self = self;
        return this;
    }
    
    @Override
    public void addMessage(T message, ActorRef<T> sender) {
    	this.sender=sender;
    	receiver.storeMessage(message);
    	
    }
    
    @Override
    public void stop(){
    	receiver.stop();
    	
    }
}

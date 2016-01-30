/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2015 Gabriele Marcomin
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
package it.unipd.math.pcd.actors.mailbox;

import java.util.concurrent.atomic.AtomicBoolean;

import it.unipd.math.pcd.actors.Actor;
import it.unipd.math.pcd.actors.Message;

public class MailBoxManager<T extends Message> implements Runnable {
	private Actor<T> actor;
	private MailBox<T> mailbox;
	private AtomicBoolean stop = new AtomicBoolean(false);
	public MailBoxManager(Actor<T> actor) {
		this.actor=actor;
		mailbox=new MailBoxQueue<T>();
	}
	@Override
	public void run() {
		/*
		 * if the mailbos is stopped, the execution runs until the last
		 * message is processed.
		 */
		while(!stop.get() || !mailbox.isEmpty()){
			//reference of the next elaborated message
			T tmp=null;
			synchronized (mailbox) {
				if(mailbox.isEmpty())
					try {
						wait();
					} 
					catch (InterruptedException e) {
						stop.set(true);
						e.printStackTrace();
					}
				else{
					//get the reference of the top
					tmp = mailbox.remove();
				}
			}
			//signal the actor for the elaboration
			actor.receive(tmp);
			try {
				Thread.sleep(280);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean storeMessage(T message){
		boolean isSalved=false;
		synchronized (stop) {
			if(!stop.get()){
				isSalved=true;
				mailbox.addMessage(message);
			}
		}
		return isSalved;
		
	}
	
	public void stop(){
		stop.set(true);
	}
}

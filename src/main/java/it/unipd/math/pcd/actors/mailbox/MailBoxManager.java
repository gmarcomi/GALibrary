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

import it.unipd.math.pcd.actors.Actor;
import it.unipd.math.pcd.actors.Message;
import it.unipd.math.pcd.actors.mailbox.Mail.State;

public class MailBoxManager<T extends Message> implements Runnable {
	private Actor actor;
	private MailBoxQueue mailbox;
	private boolean stop;
	public MailBoxManager(Actor actor) {
		this.actor=actor;
		mailbox=new MailBoxQueue();
		stop=false;
	}
	@Override
	public void run() {
		/*
		 * if the mailbox is stopped, the execution runs until the last
		 * message is processed.
		 */
		while(!stop){
			//reference of the next elaborated message
			Message message=null;
			synchronized (mailbox) {
				if(mailbox.isEmpty())
					try {
						wait();
					} 
					catch (InterruptedException e) {
						stop=true;
						e.printStackTrace();
					}
				else{
					//get the reference of the top
					Mail mail = mailbox.remove();
					if(mail.getState() == State.STOP)
						stop=true;
					else
						message = mail.getMessage();
				}
			}
			//signal the actor for the elaboration
			if(message != null)
				actor.receive(message);
			try {
				Thread.sleep(280);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		while(!mailbox.isEmpty()){
			//reference of the next elaborated message
			Message message=null;
			synchronized (mailbox) {
				//get the reference of the top
				Mail mail = mailbox.remove();
				message = mail.getMessage();
			}
			//signal the actor for the elaboration
			if(message != null)
				actor.receive(message);
			try {
				Thread.sleep(120);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public boolean addNewMail(T message){
		if(stop == true);
		else{
			mailbox.addMessage(new BaseMail(message));
		}
		return false;
	}
	public void stop(){
		mailbox.addMessage(new StopMail());
	}
}

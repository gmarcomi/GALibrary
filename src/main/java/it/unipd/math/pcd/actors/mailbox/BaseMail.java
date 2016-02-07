/**
 * 
 */
package it.unipd.math.pcd.actors.mailbox;

import it.unipd.math.pcd.actors.Message;

/**
 * An base implementation of {@code Mail}
 * @author Gabriele
 * @version 1.1
 * @since 1.1
 *
 */
public class BaseMail implements Mail<Message> {
	private Message mail;
	private State state=State.CONTINUE;
	public BaseMail(Message mail) {
		this.mail=mail;
	}

	@Override
	public Message getMessage() {
		return mail;
	}

	@Override
	public Mail.State getState() {
		return state;
	}

}

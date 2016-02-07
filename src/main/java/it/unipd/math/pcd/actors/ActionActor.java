/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2016 Gabriele Marcomin
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
 * 
 * @author Gabriele Marcomin
 * @version 1.1
 * @since 1.1
 */
package it.unipd.math.pcd.actors;
/**
 * An actor who cans add, gets his state and stops itself
 */
public interface ActionActor<T extends Message> {
	/**
     * Adds a message to the actor
     *
     * @param message The type of messages the actor can receive
     * @param sender The sender's ActorRef of the message
     */
	boolean addMessage(T message,ActorRef<T> sender);
	/**
	 * Gets the state of actor
	 * @return true if actor is interrupted
	 */
	boolean getInterrupted();
	/**
     * Stops the actor
     */
	void stop();
}

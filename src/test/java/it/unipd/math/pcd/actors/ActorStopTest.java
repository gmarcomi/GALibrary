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

package it.unipd.math.pcd.actors;

import it.unipd.math.pcd.actors.utils.ActorSystemFactory;
import it.unipd.math.pcd.actors.utils.actors.TrivialActor;
import it.unipd.math.pcd.actors.utils.actors.counter.CounterActor;
import it.unipd.math.pcd.actors.utils.messages.counter.Decrement;
import it.unipd.math.pcd.actors.utils.messages.counter.Increment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import sirius.galibrary.ActorRef;
import sirius.galibrary.ActorSystem;

/**
 * Integration test suite on actor stop feature.
 *
 * @author Gabriele Marcomin
 * @version 1.2
 * @since 1.2
 */
public class ActorStopTest {
  
  private ActorSystem system;
  /**
  * Initializes the {@code system} with a concrete implementation before each test.
  */
  
  @Before
  public void init() {
    this.system = ActorSystemFactory.buildActorSystem();
  }
  
  @Test
  public void shouldCompleteAnyMessaggesAfterStop() throws InterruptedException {
    TestActorRef counter = new TestActorRef(system.actorOf(CounterActor.class));
    CounterActor ref = (CounterActor)counter.getUnderlyingActor(system);
    for (int i = 0; i < 100; i++) {
      TestActorRef adder = new TestActorRef(system.actorOf(TrivialActor.class));
      adder.send(new Increment(), counter);
    }
    system.stop((ActorRef)counter);
    //more time sleep
    Thread.sleep(1000);
    Assert.assertEquals("A counter that was incremented 100 times should be equal to 100",
        100, ref.getCounter());
  }
  
  @Test
  public void shouldCompleteAnyMessaggesAfterStopEmAll() throws InterruptedException {
    TestActorRef counter = new TestActorRef(system.actorOf(CounterActor.class));
    TestActorRef counter2 = new TestActorRef(system.actorOf(CounterActor.class));
    CounterActor ref = (CounterActor) counter.getUnderlyingActor(system);
    CounterActor ref2 = (CounterActor) counter2.getUnderlyingActor(system);
    for (int i = 0; i < 100; i++) {
      TestActorRef adder = new TestActorRef(system.actorOf(TrivialActor.class));
      adder.send(new Increment(), counter);
      adder.send(new Decrement(), counter2);
    }
    system.stop();
    //more time sleep
    Thread.sleep(1000);
    Assert.assertEquals("A counter that was incremented 100 times should be equal to 100",
        100, ref.getCounter());
    Assert.assertEquals("A counter that was decremented 100 times should be equal to -100",
        -100, ref2.getCounter());
  }
}

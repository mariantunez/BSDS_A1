package ClientPart1;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Generates a given number of Swipe requests and stores them in a queue
 */
public class RequestGenerator implements Runnable{

  private final int NUM_REQUESTS;

  private final LinkedBlockingQueue<SwipeEvent> eventRequests;

  public RequestGenerator(int numRequests, LinkedBlockingQueue<SwipeEvent> queue) {
    this.NUM_REQUESTS = numRequests;
    this.eventRequests = queue;
  }

  @Override
  public void run() {
    // Generate request and store in queue
    for(int i=0; i<NUM_REQUESTS; i++) {
      eventRequests.add(new SwipeEvent());
    }
  }
}

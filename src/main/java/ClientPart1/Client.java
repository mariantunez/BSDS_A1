package ClientPart1;


import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SwipeApi;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Runnable Client class that perform post requests
 */
public class Client implements Runnable {

  //private final String LOCAL_SERVER = "http://localhost:8080/Server_war_exploded/";
  private final String EC2_SERVER = "http://35.162.142.0:8080/Server_war";
  private final int MAX_ATTEMPTS = 5;
  final private int REQUESTS_TO_POST;
  private final AtomicInteger failedRequests;
  private final AtomicInteger successRequests;

  private final LinkedBlockingQueue<SwipeEvent> requests;

  public Client(LinkedBlockingQueue<SwipeEvent> requests, int requestsToPost,
      AtomicInteger failedRequests, AtomicInteger successRequest) {
    this.requests = requests;
    this.REQUESTS_TO_POST = requestsToPost;
    this.failedRequests = failedRequests;
    this.successRequests = successRequest;
  }

  @Override
  public void run() {
    // Start Api
    SwipeApi apiInstance = new SwipeApi();
    apiInstance.getApiClient().setBasePath(EC2_SERVER);

    for (int i = 0; i < REQUESTS_TO_POST; i++) {

      // Retrieve a request
      SwipeEvent request = requests.poll();
      if(request == null) return;

      for (int j=0 ; j < MAX_ATTEMPTS; j++) {

        ApiResponse<Void> response;
        try {
          response = apiInstance.swipeWithHttpInfo(request.getSwipeDetails(), request.getSwipe());

          // Break loop and count request result if 201 response is received
          if (response.getStatusCode() == 201) {
            failedRequests.getAndDecrement();
            successRequests.getAndIncrement();
            break;
          }

        } catch (ApiException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

  /**
   * Starts a given number of threads to run a client
   * @param numThreads int - number of threads to create
   * @param requests Queue - group of SwipeEvent requests to post
   * @param posts int - number of post requests per thread
   * @param failedRequests AtomicInteger - number of failed post requests
   * @param successRequests AtomicInteger - number of successful post requests
   */
  public static void startClientThreads(int numThreads, LinkedBlockingQueue<SwipeEvent> requests,
      int posts ,AtomicInteger failedRequests, AtomicInteger successRequests)
      throws InterruptedException {

    List<Thread> threads = new ArrayList<>();

    for(int i=0; i< numThreads; i++) {
      Client client = new Client(requests, posts , failedRequests, successRequests);
      Thread thread = new Thread(client);
      threads.add(thread);
      thread.start();
    }

    // Wait for threads to terminate
    for(Thread thread: threads) {
      thread.join();
    }
  }
}
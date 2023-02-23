package ClientPart1;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Runs the client and threads with the given specifications
 */
public class Main {

  public static void main(String[] args) throws InterruptedException {

    //final int TOTAL_REQUESTS = 200000;
    //final int TOTAL_REQUESTS = 500000;
    final int TOTAL_REQUESTS = 10000;

    // Initialize count of fail and success requests
    AtomicInteger failedRequests = new AtomicInteger(TOTAL_REQUESTS);
    AtomicInteger successRequests = new AtomicInteger();

    // Generate queue of request on separate thread
    LinkedBlockingQueue<SwipeEvent> requests = new LinkedBlockingQueue<>(TOTAL_REQUESTS);
    new Thread(new RequestGenerator(TOTAL_REQUESTS, requests)).start();


    final long startTime = System.currentTimeMillis();

    // Start Phase 1
    final int THREADS = 1;
    final int REQUESTS = TOTAL_REQUESTS;
    Client.startClientThreads(THREADS, requests, REQUESTS, failedRequests, successRequests);

    final long endTime = System.currentTimeMillis();

    // Determine wall time and throughput in seconds
    final double wallTime = (endTime - startTime) * 0.001;
    final int throughput = (int) (TOTAL_REQUESTS/wallTime);

    String ANSI_BLUE = "\u001B[34m";
    String ANSI_RESET = "\u001B[0m";

    System.out.println("Client Results Part 1:");
    System.out.println(THREADS + " Threads (" + REQUESTS + " Requests Each)");
    System.out.printf("%27s %10s%n", "Successful Request ----> ", ANSI_BLUE + successRequests.get() + ANSI_RESET);
    System.out.printf("%27s %10s%n", "Failed Request ----> ", ANSI_BLUE + failedRequests.get() + ANSI_RESET);
    System.out.printf("%27s %10s %8s%n", "Wall Time ----> ", ANSI_BLUE + wallTime + ANSI_RESET, "seconds");
    System.out.printf("%27s %10s %15s%n", "Throughput ----> ", ANSI_BLUE + "~"+ throughput + ANSI_RESET, " requests/second");
  }
}

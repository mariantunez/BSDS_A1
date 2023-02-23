package ClientPart2;

import ClientPart1.RequestGenerator;
import ClientPart1.SwipeEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Runs the client 2 with the given specifications and write a file of response records
 */
public class Main {

  public static void main(String[] args) throws InterruptedException {

    final int TOTAL_REQUESTS = 500000;

    // Initialize count of fail and success requests
    AtomicInteger failedRequests = new AtomicInteger(TOTAL_REQUESTS);
    AtomicInteger successRequests = new AtomicInteger();

    // Generate queue of request on separate thread
    LinkedBlockingQueue<SwipeEvent> requests = new LinkedBlockingQueue<>(TOTAL_REQUESTS);
    new Thread(new RequestGenerator(TOTAL_REQUESTS, requests)).start();


    List<Integer> latencies = Collections.synchronizedList(new ArrayList<>());
    StringBuffer records = new StringBuffer();
    records.append("Start Time, Request Type, Latency, Response Code\n");

    final long startTime = System.currentTimeMillis();

    // Start Threads
    final int THREADS = 125;
    final int REQUESTS = 4000;
    Client2.startClientThreads(THREADS, requests, REQUESTS, failedRequests, successRequests,
        records, latencies);


    final long endTime = System.currentTimeMillis();

    // Determine wall time and throughput in seconds
    final double wallTime = (endTime - startTime) * 0.001;
    final int throughput = (int) (TOTAL_REQUESTS/wallTime);


    // Print requests result
    String ANSI_BLUE = "\u001B[34m";
    String ANSI_RESET = "\u001B[0m";

    System.out.println("Client Results Part 2");
    System.out.printf("%27s %10s%n", "Successful Request ----> ", ANSI_BLUE + successRequests.get() + ANSI_RESET);
    System.out.printf("%27s %10s%n", "Failed Request ----> ", ANSI_BLUE + failedRequests.get() + ANSI_RESET);
    System.out.printf("%27s %10s %8s%n", "Wall Time ----> ", ANSI_BLUE + wallTime + ANSI_RESET, "seconds");
    System.out.printf("%27s %10s %15s%n", "Throughput ----> ", ANSI_BLUE + "~"+ throughput + ANSI_RESET, " requests/second");


    // Print requests performance
    Client2.printPerformance(latencies);

    // Save performance records
    final String OUTPUT_FILE = "SwipeRecords.csv";
    final String OUTPUT_FILE2 = "PlotRecords.csv";
    SaveRecord writer = new SaveRecord();
    writer.writeCSVFile(records, OUTPUT_FILE);
    writer.writeDataToPlot(records, startTime, OUTPUT_FILE2);
  }
}

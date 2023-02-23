package ClientPart2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SaveRecord {

  public SaveRecord() {}

  /**
   * @param records StringBuffer of records to write
   * @param fileName String name of file
   */
  public void writeCSVFile(StringBuffer records, String fileName) {

    // Initialize BufferedWriter with file name
    BufferedWriter writer;
    try {
      writer = new BufferedWriter(new FileWriter(fileName, false));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    //Write file
    try {
      writer.write(records.toString());
      writer.flush();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }

    System.out.println("Finish writing file: " + fileName);
  }

  /**
   * Formats the requests sent per second
   * @param records StringBuffer of records
   * @param startTime long Starting time for reference
   * @param fileName String file name to write
   */
  public void writeDataToPlot(StringBuffer records, long startTime, String fileName) {

    String[] lines = records.toString().split("\n");
    Map<Integer, Integer> requests = new HashMap<>();
    StringBuffer requestSeconds = new StringBuffer();

    // Retrieve time record parameter and convert it to seconds
    for(int i=1; i< lines.length; i++) {
      long curr_time = Long.valueOf(lines[i].split(",")[0]);
      int requestSecond = (int) ((curr_time - startTime) * 0.001);
      requests.put(requestSecond, requests.getOrDefault(requestSecond, 0) + 1);
    }

    // Format the plot record to save
    requestSeconds.append("Second, Requests Sent\n");
    for (Map.Entry<Integer, Integer> entry : requests.entrySet()){
      String currEntry = entry.getKey() + "," + entry.getValue() + "\n";
      requestSeconds.append(currEntry);
    }

    writeCSVFile(requestSeconds, fileName);
  }
}

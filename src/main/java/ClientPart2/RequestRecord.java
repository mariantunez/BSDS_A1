package ClientPart2;

/**
 * Represents a post request record
 */
public class RequestRecord {
  private final long startTime;
  private final String requestType = "POST";
  private final int latency;
  private final int responseCode;

  public RequestRecord(long startTime, int latency, int responseCode) {
    this.startTime = startTime;
    this.latency = latency;
    this.responseCode = responseCode;
  }

  public long getStartTime() {
    return startTime;
  }

  @Override
  public String toString() {
    return startTime +
        "," +  requestType +
        "," + latency +
        "," + responseCode +
        "\n";
  }
}

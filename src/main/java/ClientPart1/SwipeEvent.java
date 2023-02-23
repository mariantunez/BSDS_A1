package ClientPart1;

import io.swagger.client.model.SwipeDetails;
import java.util.Random;

/**
 * Represents a random Swipe event request
 */
public class SwipeEvent {

  private final int MAX_COMMENT_CHARS = 256;
  private final int MAX_SWIPER_ID = 5000;
  private final int MAX_SWIPEE_ID = 1000000;

  private final String[] SWIPE_OPTION = { "left", "right" };

  private String swipe;
  private SwipeDetails swipeDetails;

  private Random random;

  public SwipeEvent() {
    random = new Random();

    // Define random values for each attribute
    this.swipe = SWIPE_OPTION[randomValue(SWIPE_OPTION.length - 1)];

    this.swipeDetails = new SwipeDetails();
    swipeDetails.setSwipee(String.valueOf(randomValue(MAX_SWIPEE_ID)));
    swipeDetails.setSwiper(String.valueOf(randomValue(MAX_SWIPER_ID)));
    swipeDetails.setComment(randomString(randomValue(MAX_COMMENT_CHARS)));
  }

  private int randomValue(int upperBound) {
    return random.nextInt(upperBound + 1);
  }

  private String randomString(final int length) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      sb.append((char) (random.nextInt(26) + 'a'));
    }
    return sb.toString();
  }

  public String getSwipe() {
    return swipe;
  }

  public SwipeDetails getSwipeDetails() {
    return swipeDetails;
  }
}

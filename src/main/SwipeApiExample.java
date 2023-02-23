package src.main;

import ClientPart1.SwipeEvent;
import io.swagger.client.*;
import io.swagger.client.auth.*;
import io.swagger.client.model.*;
import io.swagger.client.api.SwipeApi;

import java.io.File;
import java.util.*;

public class SwipeApiExample {

  public static void main(String[] args) {
    String PATH = "http://localhost:8080/SkierServer_war2";

    SwipeApi apiInstance = new SwipeApi();
    apiInstance.getApiClient().setBasePath(PATH);

    SwipeEvent swipeEvent = new SwipeEvent();
    SwipeDetails body = swipeEvent.getSwipeDetails(); // SwipeDetails | response details
    String leftorright = swipeEvent.getSwipe(); // String | Ilike or dislike user

    try {
      apiInstance.swipe(body, leftorright);
    } catch (ApiException e) {
      System.err.println("Exception when calling SwipeApi#swipe");
      e.printStackTrace();
      System.out.println(e.getCode());
    }
  }
}
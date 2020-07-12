package io.frank.vertx.learn;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

/**
 * @author jinjunliang
 **/
public class QuickGenerateData {
  public static void main(String[] args) {
    JsonObject json = new JsonObject();
    json.put("cmd", "checkin");
    JsonArray array = new JsonArray();
    json.put("payload", new JsonObject().put("users", array));
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    Random r = new Random();
    String userId = "1";

    Vertx vertx = Vertx.vertx();

    Scanner s = new Scanner(System.in);
    System.out.println("请输入时间: (yyyy-MM-dd HH:mm)");
    while (s.hasNext()) {
      String str = s.nextLine();
      if (str.equals("ok")) {
        break;
      } else {
        try {
          Long second = getSecond(LocalDateTime.parse(str, formatter));
          JsonObject jsonObject = new JsonObject();
          jsonObject.put("user_id", userId);
          jsonObject.put("check_type", "fa");
          jsonObject.put("check_time", second);
          array.add(jsonObject);

        }catch (Exception e) {
          // ignore
        }
      }
    }
    vertx.fileSystem().writeFile("checkIn.json", json.toBuffer(), ar -> {
      if (ar.succeeded()) {
        System.out.println("生成结束");
      } else {
        ar.cause().printStackTrace();
      }
    });
  }

  private static Long getSecond(LocalDateTime dateTime) {
    return dateTime.toEpochSecond(ZoneOffset.of("+8"));
  }
}

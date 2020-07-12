package io.frank.learn.vertx.action;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author jinjunliang
 **/
public class MockDeviceAndMemberAddVerticle extends AbstractVerticle {
  private Random random = new Random();
  private static final int send_count = 100;
  private static final int limit = 500;
  private static final String[] snArr = new String[]{"DL-D7_00000001",
    "DL-D7_00000002", "DL-D7_00000003", "DL-D7_00000004", "DL-D7_00000005"};

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    JsonArray array = new JsonArray();
    String urlTemplate = "http://127.0.0.1:8080/device/member/sync/%s";

    WebClient client = WebClient.create(vertx);
    for (int i = 0; i < limit; i++) {
      array.add(String.valueOf(i));
    }
    List<Future> futures = new ArrayList<>(send_count);
    for (int i = 0; i < send_count; i++) {
      Thread.sleep(10);
      futures.add(postAsync(client, String.format(urlTemplate, getSn()), array));
    }
    CompositeFuture.all(futures)
      .onSuccess(h -> System.out.println("发送成功"))
      .onFailure(Throwable::printStackTrace);
  }

  private static Future<HttpResponse<Buffer>> postAsync(WebClient client, final String url, final JsonArray array) {
    return Future.future(p -> client.postAbs(url).sendJson(array, p));
  }

  private String getSn() {
    return snArr[random.nextInt(snArr.length)];
  }
}

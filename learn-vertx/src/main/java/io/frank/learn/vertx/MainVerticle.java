package io.frank.learn.vertx;

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

public class MainVerticle extends AbstractVerticle {
  private static final int send_count = 1;
  private static final int limit = 3000;
  private static final String sn = "DL-D7_676105114";

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    JsonArray array = new JsonArray();
    String urlTemplate = "http://192.168.0.202:9801/api/v2.0/device/%s/member/add";
    WebClient client = WebClient.create(vertx);
    for (int i = 0; i < limit; i++) {
      array.add(String.valueOf(i));
    }
    List<Future> futures = new ArrayList<>(send_count);
    for (int i = 0; i < send_count; i++) {
      Thread.sleep(10);
      futures.add(postAsync(client, String.format(urlTemplate, sn), array));
    }
    CompositeFuture.all(futures)
      .onSuccess(h -> {
        System.out.println("发送成功");
      })
      .onFailure(Throwable::printStackTrace);
  }

  private static Future<HttpResponse<Buffer>> postAsync(WebClient client, final String url, final JsonArray array) {
    return Future.future(p -> client.postAbs(url).sendJson(array, p));
  }
}

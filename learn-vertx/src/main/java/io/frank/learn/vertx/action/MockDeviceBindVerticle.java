package io.frank.learn.vertx.action;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jinjunliang
 **/
public class MockDeviceBindVerticle extends AbstractVerticle {
  private static final String MSG_TEMPLATE = "{\n" +
    "    \"mid\": \"123456\",\n" +
    "    \"from\": \"system\",\n" +
    "    \"to\": \"412299173810094081\",\n" +
    "    \"action\": 402,\n" +
    "    \"data\": {\n" +
    "      \"org_id\": \"465885746820235265\",\n" +
    "      \"device_id\": \"DL-D7_%s\"\n" +
    "    }\n" +
    "  }";
  private static final int send_count = 10;


  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    String url = "http://127.0.0.1:9805/webhook";

    WebClient client = WebClient.create(vertx);

    List<Future> futures = new ArrayList<>(send_count);

    for (int i = 0; i < send_count; i++) {
      Thread.sleep(10);
      futures.add(postAsync(client, url, getData(i)));
      System.out.println("发送" + (i + 1) + "次");
    }

    CompositeFuture.all(futures)
      .onSuccess(h -> System.out.println("发送成功"))
      .onFailure(Throwable::printStackTrace);
  }

  private static Future<HttpResponse<Buffer>> postAsync(WebClient client, final String url, final String body) {
    return Future.future(p -> client.postAbs(url).sendJsonObject(new JsonObject(body), p));
  }

  private String getData(int count) {
    return String.format(MSG_TEMPLATE, StringUtils.leftPad(count + "", 8));
  }


}

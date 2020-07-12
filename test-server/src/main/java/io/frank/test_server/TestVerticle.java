package io.frank.test_server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

import java.util.Random;

/**
 * @author jinjunliang
 **/
public class TestVerticle extends AbstractVerticle {
  private int count = 0;
  private WebClient webClient;
  private Random random = new Random();
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    this.webClient = WebClient.create(vertx);
    vertx.setPeriodic(5000, id -> {
      System.out.println("已请求：" + count + "次");
    });

    vertx.setPeriodic(100, id -> {
      String sn = "3960C_" + random.nextInt(100000);
      this.webClient.get(8199, "192.168.0.201", "/api/2.0/device-gateway/status/connected/" + sn)
      .send(ar -> {
        if (ar.succeeded()) {
          HttpResponse httpResponse = ar.result();
          if (httpResponse.statusCode() == 200) {
            count++;
          } else {
            System.out.println("code: " + httpResponse.statusCode() + "\n, body: " + httpResponse.bodyAsString());
          }
        } else {
          ar.cause().printStackTrace();
        }
      });
    });
    startPromise.complete();
  }
}

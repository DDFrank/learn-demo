package io.frank.vertx.learn;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;

public class MainVerticle extends AbstractVerticle {
  private WebClient webClient;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    webClient = WebClient.create(vertx);
    //generateDevice();
    generateDeviceOnline();
    startPromise.complete();
  }

  private void generateDeviceOnline() {
    int i = 0;
    String snPrefix = "14050C_";

    for (i = 0; i < 1000; i++) {
      final int snIndex = i + 1;
      webClient.getAbs("http://127.0.0.1:9801/api/v2.0/device/" + snPrefix + getSn(i) + "/online").send(ar -> {
        if (ar.succeeded()) {
          System.out.println("已上线" + snIndex + "次");
        } else {
          ar.cause().printStackTrace();
        }
      });
    }
  }

  private void generateDevice() {
    int i = 0;
    HttpRequest<Buffer> request = webClient.postAbs("http://127.0.0.1:9801/api/v2.0/device/create");
    String snPrefix = "14050C_";
    for (i = 0; i < 1000; i++) {
      final int snIndex = i + 1;
      JsonObject json = new JsonObject();
      json.put("model", "14050C");
      json.put("name", "设备" + i);
      json.put("product_version", "1.0.0");
      json.put("sn", snPrefix + getSn(snIndex));
      json.put("status", "1");
      json.put("status_code", "1");
      json.put("status_msg", "正常");

      request.sendJsonObject(json, ar -> {
        if (ar.succeeded()) {
          System.out.println("已生成" + snIndex + "次");
        } else {
          ar.cause().printStackTrace();
        }
      });
    }
  }

  private String getSn(int index) {
    String sn = index + "";
    while (sn.length() < 4) {
      sn = "0" + sn;
    }
    return sn;
  }
}

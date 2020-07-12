package io.frank.test_server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.deployVerticle(new TestVerticle(), ar -> {
      if (ar.succeeded()) {
        System.out.println("启动成功");
        startPromise.complete();
      } else {
        ar.cause().printStackTrace();
        startPromise.fail("启动失败");
      }
    });

  }
}

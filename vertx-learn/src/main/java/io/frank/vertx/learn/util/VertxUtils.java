package io.frank.vertx.learn.util;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

/**
 *
 * @author jinjunliang 2019-07-16 17:03
 **/
public class VertxUtils {
  public static EventBus eventBus = Vertx.vertx().eventBus();

  public static Future<String> send() {
    Future<String> future =  Future.future();
    eventBus.<String>send("test", "test", ar -> {
      if (ar.succeeded()) {
        future.complete(ar.result().body());
      } else {
        future.fail(ar.cause());
      }
    });
    return future;
  }
}

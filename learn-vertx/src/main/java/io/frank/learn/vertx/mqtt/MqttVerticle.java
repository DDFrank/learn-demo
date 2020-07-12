package io.frank.learn.vertx.mqtt;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

/**
 * @author jinjunliang
 **/
public class MqttVerticle extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    startPromise.complete();
  }
}

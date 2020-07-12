package io.frank.learn.vertx;

import io.frank.learn.vertx.action.MockDeviceBindVerticle;
import io.vertx.core.Vertx;

/**
 * @author jinjunliang
 **/
public class Action {
  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new MockDeviceBindVerticle());
  }
}

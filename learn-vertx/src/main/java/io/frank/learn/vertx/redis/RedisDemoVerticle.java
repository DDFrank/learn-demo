package io.frank.learn.vertx.redis;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.Request;

/**
 * @author jinjunliang
 **/
public class RedisDemoVerticle extends AbstractVerticle {
  private static final String KEY = "s-key";

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
      Redis redisClient = Redis.createClient(vertx, "redis://127.0.0.1:6379");
      redisClient.connect(ar -> {

        if (ar.succeeded()) {
          startPromise.complete();

          ar.result().send(Request.cmd(Command.SUBSCRIBE).arg(KEY), res -> {
            System.out.println("订阅成功");
            ar.result().handler(r -> {
              System.out.println("收到了消息:" + r.toString());
            });
            vertx.eventBus().consumer("io.vertx.redis." + KEY, res1 -> {
              System.out.println(res1.body());
            });
            action();
          });
        } else {
          System.out.println("redis连接失败");
        }
      });
  }

  private void action() {
    Redis redis = Redis.createClient(vertx, "redis://127.0.0.1:6379");
    redis.send(Request.cmd(Command.PUBLISH).arg(KEY).arg("Hello World!"), res -> {
      if (res.succeeded()) {
        System.out.println("发送成功");
      }
    });
  }

}

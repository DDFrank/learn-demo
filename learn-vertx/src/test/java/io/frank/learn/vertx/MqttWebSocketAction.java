package io.frank.learn.vertx;

import io.frank.learn.vertx.mqtt.Websocket.MqttWebsocketServerImpl;
import io.vertx.core.Vertx;
import io.vertx.mqtt.MqttServer;
import io.vertx.mqtt.MqttServerOptions;

/**
 * @author jinjunliang
 **/
public class MqttWebSocketAction {
  public static void main(String[] args) {
    MqttServer server = new MqttWebsocketServerImpl(Vertx.vertx(), new MqttServerOptions()
      .setHost("127.0.0.1")
      .setPort(8010));
    server.endpointHandler(endpoint -> {

      // shows main connect info
      System.out.println("MQTT client [" + endpoint.clientIdentifier() + "] request to connect, clean session = " + endpoint.isCleanSession());

      if (endpoint.auth() != null) {
        System.out.println("[username = " + endpoint.auth().getUsername() + ", password = " + endpoint.auth().getPassword() + "]");
      }

      System.out.println("[keep alive timeout = " + endpoint.keepAliveTimeSeconds() + "]");

      // accept connection from the remote client
      endpoint.accept(false);
    });

    server.listen(ar -> {
      if (ar.succeeded()) {
        System.out.println("MQTT server listening on port " + ar.result().actualPort());
      } else {
        System.out.println("Error starting MQTT server" + ar.cause());
      }
    });
  }
}

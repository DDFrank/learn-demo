package io.frank.learn.vertx.mqtt.Websocket;

import io.frank.learn.vertx.mqtt.Websocket.codec.ByteBufToWebSocketFrameEncoder;
import io.frank.learn.vertx.mqtt.Websocket.codec.WebSocketFrameToByteBufDecoder;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.impl.NetSocketInternal;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetServer;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.MqttServer;
import io.vertx.mqtt.MqttServerOptions;

/**
 * @author jinjunliang
 **/
public class MqttWebsocketServerImpl implements MqttServer {
  private static final Logger log = LoggerFactory.getLogger(MqttWebsocketServerImpl.class);

  private final NetServer server;
  private Handler<MqttEndpoint> endpointHandler;
  private Handler<Throwable> exceptionHandler;

  private MqttServerOptions options;

  public MqttWebsocketServerImpl(Vertx vertx, MqttServerOptions options) {
    this.server = vertx.createNetServer(options);
    this.options = options;
  }

  @Override
  public MqttServer listen() {
    return listen(ar -> {});
  }

  @Override
  public MqttServer listen(int port, String host) {
    return listen(port, host, ar -> {});
  }

  @Override
  public MqttServer listen(int port) {
    return listen(port, ar -> {});
  }

  @Override
  public MqttServer listen(int port, Handler<AsyncResult<MqttServer>> listenHandler) {
    return listen(port, this.options.getHost(), listenHandler);
  }

  @Override
  public MqttServer listen(Handler<AsyncResult<MqttServer>> listenHandler) {
    return listen(this.options.getPort(), listenHandler);
  }

  @Override
  public MqttServer listen(int port, String host, Handler<AsyncResult<MqttServer>> listenHandler) {
    Handler<MqttEndpoint> h1 = endpointHandler;
    Handler<Throwable> h2 = exceptionHandler;
    server.connectHandler(so -> {
      NetSocketInternal soi = (NetSocketInternal) so;
      ChannelPipeline pipeline = soi.channelHandlerContext().pipeline();

      initChannel(pipeline);
      CustomMqttServerConnection conn = new CustomMqttServerConnection(soi, options);

      soi.messageHandler(msg -> {
        synchronized (conn) {
          conn.handleMessage(msg);
        }
      });

      conn.init(h1, h2);

    });
    server.listen(port, host, ar -> listenHandler.handle(ar.map(this)));
    return this;
  }

  @Override
  public synchronized MqttServer endpointHandler(Handler<MqttEndpoint> handler) {
    endpointHandler = handler;
    return this;
  }

  @Override
  public synchronized MqttServer exceptionHandler(Handler<Throwable> handler) {
    exceptionHandler = handler;
    return this;
  }

  @Override
  public int actualPort() {
    return server.actualPort();
  }

  @Override
  public void close() {
    server.close();
  }

  @Override
  public void close(Handler<AsyncResult<Void>> completionHandler) {
    server.close(completionHandler);
  }

  private void initChannel(ChannelPipeline pipeline) {

    pipeline.addBefore("handler", "mqttEncoder", MqttEncoder.INSTANCE);
    if (this.options.getMaxMessageSize() > 0) {
      pipeline.addBefore("handler", "mqttDecoder", new MqttDecoder(this.options.getMaxMessageSize()));
    } else {
      // max message size not set, so the default from Netty MQTT codec is used
      pipeline.addBefore("handler", "mqttDecoder", new MqttDecoder());
    }

    // adding the idle state handler for timeout on CONNECT packet
    pipeline.addBefore("handler", "idle", new IdleStateHandler(this.options.timeoutOnConnect(), 0, 0));
    pipeline.addBefore("handler", "timeoutOnConnect", new ChannelDuplexHandler() {

      @Override
      public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
          IdleStateEvent e = (IdleStateEvent) evt;
          if (e.state() == IdleState.READER_IDLE) {
            // as MQTT 3.1.1 describes, if no packet is sent after a "reasonable" time (here CONNECT timeout)
            // the connection is closed
            ctx.channel().close();
          }
        }
      }
    });

    pipeline.addBefore("mqttEncoder", "httpServerCodec", new HttpServerCodec());
    pipeline.addAfter("httpServerCodec", "aggregator", new HttpObjectAggregator(65536));

    pipeline.addAfter("aggregator", "webSocketHandler",
      new WebSocketServerProtocolHandler("/mqtt", "mqtt, mqttv3.1, mqttv3.1.1"));

    pipeline.addAfter("webSocketHandler", "bytebuf2wsEncoder", new ByteBufToWebSocketFrameEncoder());
    pipeline.addAfter("bytebuf2wsEncoder", "ws2bytebufDecoder", new WebSocketFrameToByteBufDecoder());
  }
}

package io.frank.test_server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.handler.BodyHandler;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jinjunliang
 **/
public class HttpServerVerticle extends AbstractVerticle {
  private final static String SUCCESS_RESPONSE = "{\"code\":0, \"msg\": null}";
  private final static String SUCCESS_LENGTH = SUCCESS_RESPONSE.length() + "";
  private WebClient webClient;
  private ReportCount report = new ReportCount();
  private static final String APP_ID = "373514968640995329";
  private Map<String, Org> orgs = new HashMap<>();
  private Random random = new Random();

  private int count;

  /**
   * k : 设备Sn
   * v :绑定的OrgId
   */
  private Map<String, String> deviceOrgs = new HashMap<>();



  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    HttpServer httpServer = vertx.createHttpServer();
    // web客户端
    webClient = WebClient.create(vertx);
    Router router = Router.router(vertx);

    router.route().handler(BodyHandler.create());

    router.get("/index").handler(routingContext -> {
      count++;
      routingContext.response().end("response");
    });

    // 开始绑定设备的任务
    router.get("/bindDevice").handler(routingContext -> {

      this.webClient.get(5555, "127.0.0.1", "/platform/402")
        .putHeader("concurrency", "100")
        .addQueryParam("count", "100")
        .addQueryParam("perBindCount", "1")
        .send(ar -> {
          if (ar.succeeded()) {
            this.generalResponse(routingContext);
          } else {
            this.generalResponse(routingContext);
            ar.cause().printStackTrace();
          }
        });
    });

    //开始解绑设备的任务
    router.get("/unbindDevice").handler(routingContext -> {
      this.webClient.get(5555, "127.0.0.1", "/platform/403")
        .putHeader("concurrency", "100")
        .addQueryParam("count", "1000")
        .send(ar -> {
          if (ar.succeeded()) {
            this.generalResponse(routingContext);
          } else {
            this.generalResponse(routingContext);
            ar.cause().printStackTrace();
          }
        });
    });

    // 开始更新设备状态的任务
    router.get("/updateDevice").handler(routingContext -> {
      this.webClient.get(5555, "127.0.0.1", "/platform/404")
        .putHeader("concurrency", "100")
        .addQueryParam("count", "1000")
        .addQueryParam("online", "false")
        .send(ar -> {
          if (ar.succeeded()) {
            this.generalResponse(routingContext);
          } else {
            this.generalResponse(routingContext);
            ar.cause().printStackTrace();
          }
        });
    });

    // 开始发送设备消息，就是打卡
    router.get("/sendMessage").handler(routingContext -> {
      this.webClient.get(5555, "127.0.0.1", "/platform/405")
        .putHeader("concurrency", "100")
        .addQueryParam("count", "1000")
        .send(ar -> {
          if (ar.succeeded()) {
            this.generalResponse(routingContext);
          } else {
            this.generalResponse(routingContext);
            ar.cause().printStackTrace();
          }
        });
    });

    // 开始更新组织信息的任务, 需要先绑定设备
    router.get("/updateOrgMember").handler(routingContext -> {
      this.webClient.get(5555, "127.0.0.1", "/platform/406")
        .putHeader("concurrency", "100")
        .addQueryParam("count", "1000")
        .addQueryParam("updateType", "add")
        .send(ar -> {
          if (ar.succeeded()) {
            this.generalResponse(routingContext);
          } else {
            this.generalResponse(routingContext);
            ar.cause().printStackTrace();
          }
        });
    });
    // 开始更新设备信息的任务, 需要先绑定设备
    router.get("/updateDeviceInfo").handler(routingContext -> {
      this.webClient.get(5555, "127.0.0.1", "/platform/408")
        .putHeader("concurrency", "100")
        .addQueryParam("count", "1000")
        .send(ar -> {
          if (ar.succeeded()) {
            this.generalResponse(routingContext);
          } else {
            this.generalResponse(routingContext);
            ar.cause().printStackTrace();
          }
        });
    });

    // 查看内存中的数据状态
    router.get("/status").handler(routingContext -> {
      JsonArray jsonArray = new JsonArray(new ArrayList<>(orgs.values()));
      Buffer buffer = jsonArray.toBuffer();
      String length = buffer.length() + "";
      routingContext.response()
        .putHeader("content-type", "application/json")
        .putHeader("content-length", length)
        .end(buffer);
    });

    // 开始人员同步的任务 , count 是给每个设备同步多少人
    router.get("/userSync/:count").handler(routingContext -> {
      this.generalResponse(routingContext);
      int userSyncCountPerDevice = Integer.parseInt(routingContext.pathParam("count"));
      // 给每个设备发送人员同步请求
      sendAllUserSync(userSyncCountPerDevice);
    });

    // 开始设打卡的定时任务
    router.get("/period").handler(routingContext -> {
      int deviceCount = Integer.parseInt(routingContext.queryParam("deviceCount").get(0));
      int userCount = Integer.parseInt(routingContext.queryParam("userCount").get(0));
      int periodTime = Integer.parseInt(routingContext.queryParam("periodTime").get(0));
      int totalMinutes = Integer.parseInt(routingContext.queryParam("totalMinutes").get(0));
      long endMill = System.currentTimeMillis() + totalMinutes * 60 * 1000;
      this.generalResponse(routingContext);
      vertx.setPeriodic(periodTime, id -> {
        if (System.currentTimeMillis() > endMill) {
          System.out.println(LocalDateTime.now().toString() + ": 结束循环打卡");
          vertx.cancelTimer(id);
        } else {
          // 根据间隔定时触发设备打卡
          this.webClient.get(5555, "127.0.0.1", "/platform/405")
            .putHeader("concurrency", deviceCount + "")
            .addQueryParam("count", deviceCount + "")
            .addQueryParam("userCount", userCount + "")
            .send(ar -> {
              if (ar.failed()) {
                ar.cause().printStackTrace();
              }
            });
        }
      });
    });

    // 开始设备绑定的定时任务
    router.get("/period/deviceBind").handler(routingContext -> {
      int orgTotalCount = Integer.parseInt(routingContext.queryParam("orgTotalCount").get(0));
      int orgSize = Integer.parseInt(routingContext.queryParam("orgSize").get(0));
      int preBindCount = Integer.parseInt(routingContext.queryParam("perBindCount").get(0));
      int periodTime = Integer.parseInt(routingContext.queryParam("periodTime").get(0));
      this.generalResponse(routingContext);

      sendDeviceBind(periodTime, 0, orgSize, preBindCount, orgTotalCount);
    });

    // 开始调用后门的同步任务
    router.get("/period/back").handler(routingContext -> {
      int deviceTotalCount = Integer.parseInt(routingContext.queryParam("deviceTotalCount").get(0));
      int size = Integer.parseInt(routingContext.queryParam("deviceSize").get(0));
      int periodTime = Integer.parseInt(routingContext.queryParam("periodTime").get(0));
      this.generalResponse(routingContext);

      this.sendBackDevice(periodTime, 0 , deviceTotalCount, size);
    });

    // 对于收到的数据，统计值并给与成功响应
    router.post("/webhook").handler(routingContext -> {
      report.updateTotalCount();
      /*
       * 对于不同的action做不同的处理
       * */
      JsonObject payload = routingContext.getBodyAsJson();
      Integer action = payload.getInteger("action");

      if (action == 402) {
        handleWebHook402(payload, routingContext);
      } else if (action == 405) {
        handleWebHook405(payload, routingContext);
      } else {
        // 其它消息就打印一下,并直接回复
        this.generalResponse(routingContext);
        System.out.println("收到:" + payload.toString());
      }
    });

    // 考勤应用后门接口的模拟
    router.get("/test/device").handler(routingContext -> {
      this.generalResponse(routingContext);
      String sn = routingContext.queryParam("device_id").get(0);
      System.out.println("后门接口触发了:" + sn);
      report.updateBackCount();
    });

    httpServer
      .requestHandler(router)
      .listen(5050);

    // 开始定时器，定时汇报状态
    vertx.setPeriodic(10000, id -> {
      //report.report();
      System.out.println("已收到" + count + "次");
    });

    startPromise.complete();
  }

  /**
   * 开始调用一次设备后门接口
   * @param periodTime 消息发送间隔
   * @param startDeviceIndex 设备的开始的index
   * @param totalDeviceCount 总共设备的数量
   * @param size 一次发送的设备数量
   */
  private void sendBackDevice(int periodTime, int startDeviceIndex, int totalDeviceCount, int size) {
    int endIndex = startDeviceIndex + size;
    if (startDeviceIndex >= totalDeviceCount) {
      System.out.println(LocalDateTime.now().toString() + ": 结束设备后门接口任务");
    } else {
      vertx.setTimer(periodTime, id -> {
        // 根据间隔定时触发设备打卡
        this.webClient.get(5555, "127.0.0.1", "/api/deviceUserSync")
          .addQueryParam("startIndex",  + startDeviceIndex+ "")
          .addQueryParam("size", size + "")
          .send(ar -> {
            if (ar.failed()) {
              ar.cause().printStackTrace();
            }
          });
        // 设置下一次的定时器
        sendBackDevice(periodTime, endIndex, totalDeviceCount, size);
      });
    }
  }

  /**
   * 开始发起设备绑定
   * @param periodTime 定时时间
   * @param orgFromIndex 开始组织index
   * @param orgSize 一次执行的组织数
   * @param perBindCount 每个组织需要绑定的设备数量
   * @param totalOrgCount 总的组织数量
   */
  private void sendDeviceBind(int periodTime, int orgFromIndex, int orgSize, int perBindCount, int totalOrgCount) {
    int endIndex = orgFromIndex + orgSize;
    if (orgFromIndex >= totalOrgCount) {
      System.out.println(LocalDateTime.now().toString() + ": 结束设备绑定定时任务");
    } else {
      vertx.setTimer(periodTime, id -> {
        // 根据间隔定时触发设备打卡
        this.webClient.get(5555, "127.0.0.1", "/platform/402")
          .putHeader("concurrency", 200 + "")
          .addQueryParam("orgFromIndex",  + orgFromIndex + "")
          .addQueryParam("orgSize", orgSize + "")
          .addQueryParam("perBindCount", perBindCount + "")
          .send(ar -> {
            if (ar.failed()) {
              ar.cause().printStackTrace();
            }
          });
        // 设置下一次的定时器
        sendDeviceBind(periodTime, endIndex, orgSize, perBindCount, totalOrgCount);
      });
    }

  }


  /**
   * 获取人员同步的 payload
   * @param sn 设备SN
   * @param index 起始位置
   * @param limit 结束位置
   * @param users 全部用户数
   * @param appId 应用id
   * @return
   */
  private JsonObject getUserSyncPayload(String sn, int index, int limit, Set<String> users, String appId) {
    JsonObject userSyncPayload = new JsonObject();
    List<User> syncList = users.stream()
      .skip(index)
      .limit(limit)
      .map(User::new)
      .collect(Collectors.toList());


    return userSyncPayload.put("mid", "123456")
      .put("from", appId)
      .put("to", sn)
      .put("time", 1555307375)
      .put("action", 500)
      .put("data", new JsonObject()
        .put("cmd", "user_sync")
        .put("payload", new JsonObject()
          .put("reset", false)
          .put("total_count", syncList.size())
          .put("users", syncList)))
        ;
  }

  /**
   * 处理405消息
   * @param resp
   */
  private void handleWebHook405(JsonObject resp, RoutingContext routingContext) {
    JsonObject payload = resp.getJsonObject("data");
    String cmd = payload.getString("cmd");
    String sn = resp.getString("from");
    if ("user_sync_check".equals(cmd)) {
      this.generalResponse(routingContext);
      handleUserSyncCheck(sn);
    } else if ("feature_update".equals(cmd)) {
      this.generalResponse(routingContext);
      handleFeatureUpdate(sn);
    } else if ("user_sync".equals(cmd)) {
      this.generalResponse(routingContext);
      report.updateUserSyncCount();
    } else if ("checkin".equals(cmd)) {
      report.updateCheckinCount();
      // 随机事件回复打卡消息
      this.generalResponse(routingContext);

      if (random.nextBoolean()) {
        // 3秒后返回
        vertx.setTimer(3000, id -> sendCheckInPayload(resp));
      } else {
        // 直接返回
        sendCheckInPayload(resp);
      }
    } else {
      System.out.println("忽略指令:" + cmd);
      this.generalResponse(routingContext);
    }
  }

  /**
   * 处理 feature_update
   */
  private void handleFeatureUpdate(String sn) {
    report.updateOtherCount();
    System.out.println("开始处理" + sn + "发起的特征值更新");
  }

  /**
   * 发送考勤打卡的应用
   * @param payload 打卡消息
   */
  private void sendCheckInPayload(JsonObject payload) {
    JsonObject checkInPayload = new JsonObject();
    checkInPayload
      .put("mid", payload.getString("mid"))
      .put("from", payload.getString("to"))
      .put("to", payload.getString("from"))
      .put("time", 1555307375)
      .put("action", 500)
      .put("data", new JsonObject()
        .put("cmd", "checkin"))
    ;

    webClient.post(5555, "127.0.0.1", "/gateway")
      // 随便写一个签名
      .putHeader("sig", "aaaaa")
      .sendJsonObject(checkInPayload, ar -> {
        if (ar.failed()) {
          ar.cause().printStackTrace();
        }
      });
  }

  /**
   * 处理check命令
   */
  private void handleUserSyncCheck(String sn) {
    System.out.println("开始给" + sn + "发起人员同步");
    report.updateOtherCount();
    // 触发对应的SN的人员同步
    sendUserSync(sn, 0, 50);
  }

  /**
   * 处理402消息
   * @param payload 收到的消息payload
   */
  private void handleWebHook402(JsonObject payload, RoutingContext routingContext) {
    this.generalResponse(routingContext);
    System.out.println("402:" + routingContext.getBodyAsJson());
    report.updateBindCount();
    // 查询组织的基本信息，组织的成员信息，设备的基本信息
    String orgId = payload.getJsonObject("data").getString("org_id");
    String deviceId = payload.getJsonObject("data").getString("device_id");
    Org org = new Org(orgId);
    Set<String> devices = new HashSet<>();
    devices.add(deviceId);
    org.setDevices(devices);
    // 存储设备和组织的绑定关系
    deviceOrgs.put(deviceId, orgId);
    // 收集数据
    orgs.merge(orgId, org, Org::plus);
    JsonObject body506 = new JsonObject()
      .put("mid", "123456")
      .put("from", "373514968640995329")
      .put("to", "system")
      .put("time", 1555307375)
      .put("action", 506)
      .put("data", new JsonObject().put("org_id", orgId))
      ;
    webClient.post(5555, "127.0.0.1", "/gateway")
      // 随便写一个签名
      .putHeader("sig", "aaaaa")
    .sendJsonObject(body506, ar -> {
      if (ar.succeeded()) {
        System.out.println("506: " + ar.result().bodyAsString());
      } else {
        ar.cause().printStackTrace();
      }
    });

    JsonObject body507 = new JsonObject()
      .put("mid", "123456")
      .put("from", "373514968640995329")
      .put("to", "system")
      .put("time", 1555307375)
      .put("action", 507)
      .put("data", new JsonObject()
        .put("org_id", orgId)
        .put("target", "member")
        .put("update_time", -1)
        .put("size", 300)
      );

    webClient.post(5555, "127.0.0.1", "/gateway")
      // 随便写一个签名
      .putHeader("sig", "aaaaa")
      .sendJsonObject(body507, ar -> {
        if (ar.succeeded()) {
          // 接收人员
          System.out.println("507: " + ar.result().bodyAsString());
          Org org1 = new Org(orgId);
          JsonArray jsonArray = ar.result().bodyAsJsonObject().getJsonObject("data").getJsonArray("payload");
          int l = jsonArray.size();
          Set<String> userIds = new HashSet<>();
          for (int i=0;i<l;i++) {
            JsonObject jsonObject = jsonArray.getJsonObject(i);
            userIds.add(String.valueOf(jsonObject.getString("user_id")));
          }

          org1.setUsers(userIds);
          orgs.merge(orgId, org1, Org::plus);
        } else {
          ar.cause().printStackTrace();
        }
      });

    JsonObject body508 = new JsonObject()
      .put("mid", "123456")
      .put("from", "373514968640995329")
      .put("to", "system")
      .put("time", 1555307375)
      .put("action", 508)
      .put("data", new JsonObject()
        .put("device_id", deviceId)
      );

    webClient.post(5555, "127.0.0.1", "/gateway")
      // 随便写一个签名
      .putHeader("sig", "aaaaa")
      .sendJsonObject(body508, ar -> {
        if (ar.succeeded()) {
          System.out.println("508: " + ar.result().bodyAsString());
        } else {
          ar.cause().printStackTrace();
        }
      });
  }

  /**
   * 根据内存中组织和人员的情况给设备发起人员同步
   * 按顺序给每个device分配 userSyncCountPerDevice 数量的人员
   * @param userSyncCountPerDevice 需要给每个device分配的人员
   */
  private void sendAllUserSync(int userSyncCountPerDevice) {
    orgs.forEach((orgId, org) -> {
      Set<String> devices = org.getDevices();
      int index = 0;
      Iterator<String> iterator = devices.iterator();
      while(iterator.hasNext()) {
        String sn = iterator.next();
        sendUserSync(sn, index, userSyncCountPerDevice);
        index += userSyncCountPerDevice;
      }
    });
  }

  /**
   * 给对应的sn发送人员同步请求
   * @param sn 设备SN
   * @param userSyncCountPerDevice 需要给每个设备发送的数量, 假如超过组织的最大人员数量就直接同步全部组织人员
   */
  private void sendUserSync(String sn, int startIndex, int userSyncCountPerDevice) {
    String orgId = deviceOrgs.get(sn);
    Org org = orgs.get(orgId);
    if (Objects.isNull(org)) {
      System.out.println("orgId:" + orgId + "不存在");
      return;
    }
    JsonObject payload = getUserSyncPayload(sn, startIndex, userSyncCountPerDevice, org.getUsers(), APP_ID);
    this.webClient.post(5555, "127.0.0.1", "/gateway")
      .putHeader("sig", "asdasda")
      .putHeader("Content-Type", "application/json")
      .sendJsonObject(payload, ar -> {
        if (ar.succeeded()) {
          System.out.println("发送成功");
        } else {
          ar.cause().printStackTrace();
        }
      });
  }

  /**
   * 返回成功的响应
   */
  private void generalResponse(RoutingContext routingContext) {
    routingContext
      .response()
      .putHeader("Content-Type", "application/json")
      .putHeader("Content-Length", SUCCESS_LENGTH)
      .end(SUCCESS_RESPONSE);
  }

}

# `silver-alarm`
> 报警模块， 主要实现了一个报警的基本框架，支持动态配置异常报警类型，支持多种报警类型选择，支持自定义不同报警方式的紧急程度并实现根据报警频率来自动提升报警的危险等级的功能


## 1. 背景

> 首先说一下为什么要做这个东西，结合最近工作过程中实际遇到的一个案例进行说明，最近系统中依赖的一个上游业务做改造，导致稳定性变得较差，时不时的会出现一波超时，然后就会收到一堆的报警，而这个报警，又实现得比较简单粗暴，系统中所有的异常数超过一定的阀值，就报警；

这种时候，我的一个迫切需求是，针对这个上游业务依赖的报警，可以独立出来，如果出现问题，则报给这个依赖的负责人，直接让他去查看问题；而我自己系统内部的异常，则不要报给他。即这里的一个需求点是，区分不同的异常类型，实现报警的隔离

其次，就是这个报警全部都是短信方式，直接导致手机堆积大量的这种报警短信，非常容易的将我自己的一些核心报警短信淹没掉。所以这里的一个需求点是，需要可选的报警方式，自定义报警的优先级，只将非常核心的异常报给短信，保证手机的干净

最后一点就是，正好想做点好玩的东西，所以就有了这么个想法


## 2. 设计思路

设计思路比较简单， 如下图基本上描述了整个设计实现的框架， 

![报警设计图](https://static.oschina.net/uploads/img/201704/28225117_XcGV.png)



### 1. 配置项 `Config`

对每种报警类型都有一个基本的配置项

- users存储默认报警的用户
- threshold 表示每种类型的报警阀值以及对应的报警用户
- min 表示1分钟最低的报警数（少于这个的就不报警了）
- max 表示1分钟最高的报警数（超过这个次数也不要继续报，浪费资源）
- level: 当前的报警类型
- auto : true时，表示报警类型可以晋级，如最开始为邮件报警，超过阀值后，改为tt报警，依然超过，则改为短信轰炸



default 为默认的兜底，必须存在，否则报给谁都不知道了

```json
{
    "default": {
        "level": "NONE",
        "autoIncEmergency": true,
        "max": 30,
        "min": 5,
        "threshold": [
            {
                "level": "SMS",
                "threshold": 20,
                "users": [
                    "345345345345",
                    "123123123123"
                ]
            },
            {
                "level": "WEIXIN",
                "threshold": 10,
                "users": [
                    "yihui",
                    "erhui"
                ]
            }
        ],
        "users": [
            "yihui"
        ]
    },
    "NPE": {
        "level": "WEIXIN",
        "autoIncEmergency": false,
        "max": 30,
        "min": 0,
        "threshold": [
            {
                "level": "SMS",
                "threshold": 20,
                "users": [
                    "345345345345",
                    "123123123123"
                ]
            },
            {
                "level": "WEIXIN",
                "threshold": 10,
                "users": [
                    "3hui",
                    "4hui"
                ]
            }
        ],
        "users": [
            "yihui"
        ]
    },
    "XXX,YYY": {
        "level": "EMAIL",
        "autoIncEmergency": false,
        "max": 30,
        "min": 3,
        "threshold": [
            {
                "level": "SMS",
                "threshold": 20,
                "users": [
                    "345345345345",
                    "123123123123"
                ]
            },
            {
                "level": "WEIXIN",
                "threshold": 10,
                "users": [
                    "yihui",
                    "erhui"
                ]
            },
            {
                "level": "EMAIL",
                "threshold": 5,
                "users": [
                    "yihui@xxx.com",
                    "erhui@xxx.com"
                ]
            }
        ],
        "users": [
            "yihui@xxx.com"
        ]
    }
}
```


### 2. 执行器 `IExecute`
> 每一个实现，表示具体的报警类型

如我们实现的三种

- `NoneExecute` 空报警执行 （什么都不干）
    - 部分报警类型是不需要care的，可以直接无视掉
- `EmailExecute` 邮件报警执行
- `WeiXinExecute` 微信报警执行
- `SmsExecute` 短信报警执行
- `LogExecute` 日志执行，只在日志里记录一下
- `ExecuteSimpleFactory` 简单工厂，用于获取具体的执行报警类


### 3. AlarmSelector 选择执行器
> 根据Wrapper传过来的信息，选择具体的执行器


在选择时，会将以下几点作为考虑因素：

- 是否达到最低报警限制
- 是否超过最大报警限制
- 是否开启了报警等级递增开关（即`autoIncEmergency `是否为true）
- 若开启自动升级
    - 判断默认报警执行器，是否在`threshold`列表中
    - 在，则最低的报警限制为默认执行器，然后依次上升
    - 不在，则完全按照`threshold`列表的规则来


### 4. ConfigWrapper 封装类
> 读取并关注最新的配置信息，转换为业务DO对象，缓存与内存中
>
> 对外提供根据异常类型，获取配置项的接口


对外提供两个接口，一个是是否开启的开关，一个是获取报警配置

```java
/**
 * 获取异常类型对应的报警配置项, 若不存在则返回默认的配置项
 * @param key
 * @return
 */
public AlarmConfig getConfig(String key);
    
    
/**
 * 判断是否允许报警
 *
 * @param enableKey
 * @return
 */
public boolean alarmEnable(String enableKey)
```

这个类里面有几点可以注意

- 提供了一个默认的报警配置项（即兜底的报警）
- 将配置转换为业务对象，加载内存中；避免每次解析的开销
- 监听配置的变动，主动更新内存中的配置项，采用备份覆盖的方式，保证线程安全
- 报警类型的排序，支持自定义，没有自己写死（每个人认为的重要程度都不尽相同）


**可以优化的点**

本项目里面，配置信息就是写在本地的一个文件中，并起了一个定时任务，每个一分钟去判断下文件是否更新，如果更新，则重新加载

一般公司内部都会有这种动态配置的变更系统，完全可以替换掉，监听配置变更事件，主动刷新内存中的配置项即可


### 5. AlarmWrapper 封装类
> 对外提供调用接口，内部维持 Selector对象，ConigWrapper对象，Count计数器，线程调度池

具体的执行流:

- 获取报警对应的配置项
- 获取当前计数，并报警计数+1
- 根据Selecotr获取执行报警
- 拼装报警数据结构
- 执行报警


对外暴露两个接口，且  `AlarmWrapper` 作为单例模式进行调用

```java
 public void sendMsg(String key, String content)；
 
 public void sendMsg(String key, String title, String content)；
```


## 3. 使用姿势 & 测试case

### 使用方法

使用起来非常简单，因为`AlarmWrapper`实现了单例模式，所以可以直接这么调用

```java
// 第一个参数为报警的业务key
// 第二个参数为报警标题
// 第三个参数为报警的正文
AlarmWrapper.getInstance().sendMsg("xxx", "不存在xxx异常配置", "报警嗒嗒嗒嗒");
```


### 测试用例

可以直接参考test目录下面的测试类，在执行之前，请确保机器的根目录下存在 `/tmp/alarmConfig`文件，可以直接将源工程中的配置文件拷贝过去


```java
@Test
public void sendMsg() throws InterruptedException {
    String key = "NPE";
    String title = "NPE异常";
    String msg = "出现NPE异常了!!!";


    AlarmWrapper.getInstance().sendMsg(key, title, msg);  // 微信报警


    // 不存在异常配置类型, 采用默认报警, 次数较小, 则直接部署出
    AlarmWrapper.getInstance().sendMsg("zzz", "不存在xxx异常配置", "报警嗒嗒嗒嗒");



    Thread.sleep(1000);
}


public static void main(String[] args) {
    // 测试异常升级的case
    // 计数 [1 - 2] 默认报警（即无日志） （其中 < 3 的是因为未达到下限, 采用的默认报警）
    // 计数 [3 - 4] 默认邮件报警（其中 < 5 采用的默认报警, 与下面的区别是报警用户）
    // 计数 [5 - 9] 邮件报警 （大于5小于10根据上升规则,还是选择邮件报警）
    // 计数 [10 - 19] 微信报警
    // 计数 [20 - 30] 短信报警
    // 计数 [31 -] 默认报警 （超过上限, 不报警）
    for (int i = 0; i < 40; i++) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AlarmWrapper.getInstance().sendMsg("YYY", "异常报警升级测试");
            }
        }).start();
    }
}
```

下面分别是上面两个case的输出

```
11:37:39.444 [sms-sender-1-1] DEBUG alarm - Do send msg by WeiXinExecute to user:yihui, title: NPE异常, msg:  ip:172.17.37.73 >>> key:NPE >>> 异常数: 1 >>> 出现NPE异常了!!!




---------------- 人肉分割线 ----- 第二个方法输出 --------------


11:24:48.835 [Thread-13] DEBUG alarm - Do send msg by SmsExecute to user:345345345345,123123123123, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 23 >>> 异常报警升级测试
11:24:48.843 [Thread-2] DEBUG alarm - Do send msg by WeiXinExecute to user:yihui,erhui, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 10 >>> 异常报警升级测试
11:24:48.833 [sms-sender-1-1] DEBUG alarm - Do send msg by EmailExecute to user:yihui@xxx.com,erhui@xxx.com, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 8 >>> 异常报警升级测试
11:24:48.843 [Thread-29] DEBUG alarm - Do send msg by WeiXinExecute to user:yihui,erhui, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 15 >>> 异常报警升级测试
11:24:48.838 [Thread-19] DEBUG alarm - Do send msg by SmsExecute to user:345345345345,123123123123, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 29 >>> 异常报警升级测试
11:24:48.833 [sms-sender-1-2] DEBUG alarm - Do send msg by WeiXinExecute to user:yihui,erhui, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 17 >>> 异常报警升级测试
11:24:48.835 [Thread-11] DEBUG alarm - Do send msg by SmsExecute to user:345345345345,123123123123, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 21 >>> 异常报警升级测试
11:24:48.839 [Thread-16] DEBUG alarm - Do send msg by SmsExecute to user:345345345345,123123123123, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 26 >>> 异常报警升级测试
11:24:48.833 [sms-sender-1-3] DEBUG alarm - Do send msg by EmailExecute to user:yihui@xxx.com,erhui@xxx.com, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 9 >>> 异常报警升级测试
11:24:48.843 [Thread-31] DEBUG alarm - Do send msg by WeiXinExecute to user:yihui,erhui, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 16 >>> 异常报警升级测试
11:24:48.839 [Thread-18] DEBUG alarm - Do send msg by SmsExecute to user:345345345345,123123123123, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 28 >>> 异常报警升级测试
11:24:48.842 [Thread-3] DEBUG alarm - Do send msg by WeiXinExecute to user:yihui,erhui, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 11 >>> 异常报警升级测试
11:24:48.834 [Thread-32] DEBUG alarm - Do send msg by EmailExecute to user:yihui@xxx.com,erhui@xxx.com, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 7 >>> 异常报警升级测试
11:24:48.835 [sms-sender-1-4] DEBUG alarm - Do send msg by EmailExecute to user:yihui@xxx.com,erhui@xxx.com, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 5 >>> 异常报警升级测试
11:24:48.841 [Thread-20] DEBUG alarm - Do send msg by SmsExecute to user:345345345345,123123123123, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 30 >>> 异常报警升级测试
11:24:48.841 [sms-sender-1-5] DEBUG alarm - Do send msg by SmsExecute to user:345345345345,123123123123, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 20 >>> 异常报警升级测试
11:24:48.837 [Thread-15] DEBUG alarm - Do send msg by SmsExecute to user:345345345345,123123123123, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 25 >>> 异常报警升级测试
11:24:48.835 [Thread-14] DEBUG alarm - Do send msg by SmsExecute to user:345345345345,123123123123, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 24 >>> 异常报警升级测试
11:24:48.834 [Thread-12] DEBUG alarm - Do send msg by SmsExecute to user:345345345345,123123123123, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 22 >>> 异常报警升级测试
11:24:48.841 [Thread-17] DEBUG alarm - Do send msg by SmsExecute to user:345345345345,123123123123, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 27 >>> 异常报警升级测试
11:24:48.852 [sms-sender-1-1] DEBUG alarm - Do send msg by WeiXinExecute to user:yihui,erhui, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 14 >>> 异常报警升级测试
11:24:48.856 [sms-sender-1-2] DEBUG alarm - Do send msg by WeiXinExecute to user:yihui,erhui, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 18 >>> 异常报警升级测试
11:24:48.858 [sms-sender-1-3] DEBUG alarm - Do send msg by WeiXinExecute to user:yihui,erhui, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 12 >>> 异常报警升级测试
11:24:48.860 [sms-sender-1-4] DEBUG alarm - Do send msg by WeiXinExecute to user:yihui,erhui, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 19 >>> 异常报警升级测试
11:24:48.861 [sms-sender-1-5] DEBUG alarm - Do send msg by WeiXinExecute to user:yihui,erhui, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 13 >>> 异常报警升级测试
11:24:48.862 [sms-sender-1-1] DEBUG alarm - Do send msg by EmailExecute to user:yihui@xxx.com, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 4 >>> 异常报警升级测试
11:24:48.862 [sms-sender-1-2] DEBUG alarm - Do send msg by EmailExecute to user:yihui@xxx.com,erhui@xxx.com, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 6 >>> 异常报警升级测试
11:24:48.862 [sms-sender-1-3] DEBUG alarm - Do send msg by EmailExecute to user:yihui@xxx.com, title: [alarm], msg:  ip:172.17.37.73 >>> key:YYY >>> 异常数: 3 >>> 异常报警升级测试

```


## 4. 待完善

上面其实只是搭了一个架子，具体的执行报警的代码没有，配置项的设计可能也不是完全能满足各种需求~~（如典型的不同报警类型，报警用户应该是区分开来的，上面全部都是一种）~~

**TODO LIST**

- 实现具体的报警业务代码
- ~~报警用户看是否有必要和报警方式绑定~~ (已完成)


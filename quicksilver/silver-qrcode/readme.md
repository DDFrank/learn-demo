# 二维码工具篇

> 利用google的开源库 `zxing` 来实现二维码的生成，并实际修复一些常见的问题

项目地址： [http://git.oschina.net/liuyueyi/quicksilver](http://git.oschina.net/liuyueyi/quicksilver/tree/master/silver-qrcode?dir=1&filepath=silver-qrcode)

## 1. 设计思路
> 二维码生成，采用现在用得比较多的开源框架 `Zxing`

既然都不是自己来生成二维码了，为什么要做这个东西呢？ 我要生成二维码直接用官方的api不就行了，你这个不是化蛇添足么！！！

    - 官方的接入比较麻烦，特别是你想定制生成个性化的二维码时，需要了解到zxing内部的一些设置参数，这个工具则降低了这些成本，与zxing打交道的配置都有它来做，对外暴露一些友好的，易懂的参数配置
    - 实现对实际二维码生成工具的解耦，假设zxing被爆出了什么安全漏洞，这里进行切换别的框架相对成本更低
    - 个性化的定制 （如加logo）

目标

    - 最开始是希望设计个通用的，与具体的二维码生成工具解耦（即作为一个适配器层），实际上没这么玩...
    - 制定对外暴露的配置项，用户根据需要设置二维码生成的参数，生成二维码
        - 即对用户而言，就两部，设置参数， 生成二维码， 总得交互就两个接口

设计

    - 设置参数采用builder模式， 生成配置项
    - 一个适配层，将配置项适配为zxing的二维码生成参数
    - 实际的处理层，生成二维码
    - 输出层，可以根据需求选择输出方式（输出为stream, 文件， bufferedImage）

## 2. 实现说明

1. 配置参数

    ```java
 /**
    * The message to put into QrCode
    */
    private String msg;


    /**
    * qrcode center logo
    */
    private String logo;


    /**
    * qrcode image width
    */
    private Integer w;


    /**
    * qrcode image height
    */
    private Integer h;


    /**
    * qrcode bgcolor, default white
    */
    private Integer offColor;


    /**
    * qrcode msg color, default black
    */
    private Integer onColor;


    /**
    * qrcode message's code, default UTF-8
    */
    private String code;


    /**
    * 0 - 4
    */
    private Integer padding;


    /**
    * error level, default H
    */
    private ErrorCorrectionLevel errorCorrection;


    /**
    * output qrcode image type, default png
    */
    private String picType;
```

2. 二维码生成

    生成二维码核心代码
`QRCode code = Encoder.encode(qrCodeConfig.getMsg(), errorCorrectionLevel, qrCodeConfig.getHints());`

    生成的code中， 就包含了二维码矩阵， 剩下的就是将矩阵渲染输出的问题， 输出没什么好说的，这里指出一点原生的zxing生成二维码的白边可能特别大，本工具类内部做了兼容，详情后续博文写出

    ```java
    /**
     * 对 zxing 的 QRCodeWriter 进行扩展, 解决白边过多的问题
     * <p/>
     * 源码参考 {@link com.google.zxing.qrcode.QRCodeWriter#encode(String, BarcodeFormat, int, int, Map)}
     */
    private static BitMatrix encode(QrCodeConfig qrCodeConfig) throws WriterException {
        ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;
        int quietZone = 1;
        if (qrCodeConfig.getHints() != null) {
            if (qrCodeConfig.getHints().containsKey(EncodeHintType.ERROR_CORRECTION)) {
                errorCorrectionLevel = ErrorCorrectionLevel.valueOf(qrCodeConfig.getHints().get(EncodeHintType.ERROR_CORRECTION).toString());
            }
            if (qrCodeConfig.getHints().containsKey(EncodeHintType.MARGIN)) {
                quietZone = Integer.parseInt(qrCodeConfig.getHints().get(EncodeHintType.MARGIN).toString());
            }

            if (quietZone > QUIET_ZONE_SIZE) {
                quietZone = QUIET_ZONE_SIZE;
            } else if (quietZone < 0) {
                quietZone = 0;
            }
        }

        QRCode code = Encoder.encode(qrCodeConfig.getMsg(), errorCorrectionLevel, qrCodeConfig.getHints());
        return renderResult(code, qrCodeConfig.getW(), qrCodeConfig.getH(), quietZone);
    }
    ```

## 3. 使用说明

写完了就要开始实际用，写了个测试类，贴出如下

```java
/**
     * 测试二维码
     */
    @Test
    public void testGenQrCode() {
        String msg = "create qrcode!!!";

        // 简单的生成
        QrCodeConfig qrCodeConfig = QrCodeGenWrapper.createQrCodeConfig()
                .setMsg(msg)
                .build();

        try {
            boolean ans = QrCodeGenWrapper.asFile(qrCodeConfig, "qrcode/gen.png");
            System.out.println(ans);
        } catch (Exception e) {
            System.out.println("create qrcode error! e: " + e);
            Assert.assertTrue(false);
        }


        //生成红色的二维码 300x300, 无边框
        try {
            boolean ans = QrCodeGenWrapper.createQrCodeConfig()
                    .setMsg(msg)
                    .setW(300)
                    .setOnColor(0xffff0000)
                    .setOffColor(0xffffffff)
                    .setPadding(0)
                    .asFile("qrcode/gen_300x300.png");
            System.out.println(ans);
        } catch (Exception e) {
            System.out.println("create qrcode error! e: " + e);
            Assert.assertTrue(false);
        }


        // 生成带logo的二维码
        try {
            String logo = "https://git.oschina.net/uploads/34/2334_liuyueyi.png";
            boolean ans = QrCodeGenWrapper.createQrCodeConfig()
                    .setMsg(msg)
                    .setW(300)
                    .setOnColor(0xffff0000)
                    .setOffColor(0xffffffff)
                    .setPadding(0)
                    .setLogo(logo)
                    .asFile("qrcode/gen_300x300_logo.png");
            System.out.println(ans);
        } catch (Exception e) {
            System.out.println("create qrcode error! e: " + e);
            Assert.assertTrue(false);
        }


        // 根据本地文件生成待logo的二维码
        try {
            String logo = "qrcode/logo.png";
            boolean ans = QrCodeGenWrapper.createQrCodeConfig()
                    .setMsg(msg)
                    .setW(300)
                    .setOnColor(0xffff0000)
                    .setOffColor(0xffffffff)
                    .setPadding(0)
                    .setLogo(logo)
                    .asFile("qrcode/gen_300x300_logo_v2.png");
            System.out.println(ans);
        } catch (Exception e) {
            System.out.println("create qrcode error! e: " + e);
            Assert.assertTrue(false);
        }
    }
```

从上面可以看出，实际用的时候有两种方式，没什么特别的，看自己需求选择使用
    - 先生成配置项， 然后根据配置项生成二维码
    - 设置参数，然后直接调用build的生成二维码方法


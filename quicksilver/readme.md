# quicksilver
> 小工具集合


## module

### 1. `silver-qrcode`
> 二维码生成

封装了一个二维码生成和读取识别二维码图片的工具类, 主要借用开源工具 `zxing`来实现上述功能, 并在生成二维码中扩展了生成logo, 生成圆角白边, 修复zxing中边距过大的缺陷

详细说明参考: [二维码说明文档](silver-qrcode/readme.md)


### 2. `silver-alarm`
> 报警模块

主要实现了一个抽象的报警框架, 可以根据配置项,动态加载不同的报警类型对应的报警参数, 支持根据报警的频率来自动升级报警的严重程度


详情文档参考: [报警模块说明文档](silver-alarm/README.md)


### 3. `silver-file`
> java各种姿势读文件的工具包

这个module主要实现的就是各种姿势的读取文件

- 支持相对路径,绝对路径,网络路径;
- 支持字节读, 字符读, 行读;
- 支持读json, csv, ini, properties, yaml, xml等格式的配置文件


详情参考文档: [文件读取说明文档](silver-file/readme.md)


### 4. `silver-text`
> 文本解析工具包

目前实现了一个字符串的高逼格替换的工具类, 详情可以参考文档

[https://my.oschina.net/u/566591/blog/868728](https://my.oschina.net/u/566591/blog/868728)

[https://my.oschina.net/u/566591/blog/869472](https://my.oschina.net/u/566591/blog/869472)


### 5. `silver-spi`
> 基于jdk原生ServiceLoader + Groovy动态编译实现的spi框架
> 
> 详情可参考 [silver-spi/readme.md](silver-spi/readme.md)

了解细节可以参考一下几篇博文

- [SPI框架实现之旅一：背景介绍](https://my.oschina.net/u/566591/blog/911054)
- [SPI框架实现之旅二：整体设计](https://my.oschina.net/u/566591/blog/911055)
- [SPI框架实现之旅三：实现说明](https://my.oschina.net/u/566591/blog/911056)
- [SPI框架实现之旅四：使用测试](https://my.oschina.net/u/566591/blog/911076)

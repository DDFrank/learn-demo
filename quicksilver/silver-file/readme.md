## silver-file
> 文件读写辅助工具类


### 0. 目录创建

`com.hust.hui.quicksilver.file.DirUtil#mkDir`

### 1.基本文件的读取

**`FileUtil`**

- 提供三中方式读取文件
    - `createByteRead` 按字节读取
    - `createCharRead` 按字符读取
    - `getStreamByFileName`  按行读取
- `getStreamByFileName` 提供各种路径方式获取文件，支持绝对路径，相对路径，网络地址获取文件流
- `getMagicNum` 新增获取文件的魔数接口, 判断文件的真是类型

### 2. Csv文件读取

`com.hust.hui.quicksilver.file.CsvUtil#read`


### 3. Ini文件读取

`com.hust.hui.quicksilver.file.IniUtil#read`

### 4. json文件读取

`com.hust.hui.quicksilver.file.JsonUtil#read`

### 5. properties文件读取

`com.hust.hui.quicksilver.file.PropertiesUtil#read`


### 6. yaml文件读取

`com.hust.hui.quicksilver.file.YamlUtil#read`


### 7. 写文件工具类

`com.hust.hui.quicksilver.file.FileWriteUtil`

- 字符方式输出
- 字节方式输出 （默认utf8编码）
- 支持追加输出，覆盖输出
    
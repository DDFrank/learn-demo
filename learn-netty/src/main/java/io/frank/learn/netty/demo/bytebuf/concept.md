### Buffer类有四个重要属性
- capacity 容量
- position 读写位置
- limit 读写限制
- mark position 的临时存入标记

### capacity
- 表示内部容量的大小
- 一旦初始化就不能改变
- 不是表示 byte[] 数组的字节的数量,而是写入的数据对象的数量，
比如 DoubleBuffer, capacity 是 100 的话，表示可以写入100个double数据

### position
- 表示读写指针的当前位置
- position的变化规则跟 读写模式有关系， flip() 方法可以转换读写模式
- 新建一个缓冲区时，此时处于写模式
    - 初始值为0, 写入一个数据加1，最多到达 limit - 1 的位置
- 刚进入读模式时，position会被重置为0
    - 读一个数据加1，最大为 limit
    
### limit
- 写模式下，为可以写入的数据最大上限
    - 刚进入写模式时，limit的值会被设置为缓冲区的 capacity 容量
- 读模式, 表示最多能从缓冲区读取到多少数据
- 写模式下调用flip()进行模式切换时，会将 limit 设置为 position，然后position重置为0


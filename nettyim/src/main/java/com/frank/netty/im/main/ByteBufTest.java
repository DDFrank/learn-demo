package com.frank.netty.im.main;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * Package com.frank.netty.im.main
 * Description: ByteBuf 的API 示例
 * author 016039
 * date 2018/11/17上午5:06
 */
public class ByteBufTest {
    public static void main(String[] args) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(9, 100);

        print("allocate ByteBuf(9, 100) ", byteBuf);

        // write 方法改 写指针，写完之后指针未到 capacity 的时候，buffer 仍然可写
        byteBuf.writeBytes(new byte[]{1,2,3,4});
        print("writeBytes(1,2,3,4) ", byteBuf);

        // write 方法改变 写指针，写完之后指针未到 capacity的时候，buffer 仍然可写
        // 写完 int 类型后，指针增加4
        byteBuf.writeInt(12);
        print("byteBuf.writeInt(12) ", byteBuf);

        // write 方法改变写指针，写完之后指针等于 capacity 的时候， buffer 不可写
        byteBuf.writeBytes(new byte[5]);
        print("writeBytes(5)", byteBuf);

        // write 方法改变写指针，写的时候发现 buffer 不可写则开始扩容，扩容之后 capacity 随机改变
        byteBuf.writeBytes(new byte[]{6});
        print("writeBytes(6)", byteBuf);

        // get方法不改变读写指针
        System.out.println("getByte(3) return: " + byteBuf.getByte(3));
        System.out.println("getShort(3) return: " + byteBuf.getShort(3));
        System.out.println("getInt(3) return: " + byteBuf.getInt(3));
        print("getByte()", byteBuf);

        // set 方法不改变读写指针
        byteBuf.setByte(byteBuf.readableBytes() + 1, 0);
        print("setByte()", byteBuf);

        // read 方法改变读指针
        byte[] dst = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(dst);
        print("readBytes(" + dst.length + ")", byteBuf);


    }

    private static void print (String action, ByteBuf byteBuf) {
        System.out.println("after ======== " + action + "=============");
        System.out.println("capacity(): " + byteBuf.capacity());
        System.out.println("maxCapacity(): " + byteBuf.maxCapacity());
        System.out.println("readerIndex()" + byteBuf.readerIndex());
        System.out.println("readableBytes()" + byteBuf.readableBytes());
        System.out.println("isReadable()" + byteBuf.isReadable());
        System.out.println("writerIndex()" + byteBuf.writerIndex());
        System.out.println("writableBytes()" + byteBuf.writableBytes());
        System.out.println("isWritable()" + byteBuf.isWritable());
        System.out.println("maxWritableBytes()" + byteBuf.maxWritableBytes());
        System.out.println();
    }
}

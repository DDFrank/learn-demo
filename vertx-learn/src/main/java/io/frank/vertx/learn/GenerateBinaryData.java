package io.frank.vertx.learn;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;

/**
 * @author jinjunliang
 **/
public class GenerateBinaryData {
  private static final int KB_LENGTH = 512;
  public static void main(String[] args) {
    Buffer b = Buffer.buffer();
    byte b1 = 1;
    long l = KB_LENGTH * 1024;
    for (int i=0;i<l;i++)  {
      b.appendByte(b1);
    }
    Vertx vertx = Vertx.vertx();
    vertx.fileSystem().writeFileBlocking("result.txt", b);
    System.out.println("生成完成");
  }
}

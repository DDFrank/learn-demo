import io.frank.vertx.learn.MainVerticle;
import io.vertx.core.Vertx;

/**
 * @author jinjunliang
 **/
public class Debug {
  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new MainVerticle());
  }
}

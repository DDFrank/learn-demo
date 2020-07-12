package tomcat;

import io.frank.learn.netty.custom.tomcat.GPTomcat;

/**
 * @author jinjunliang
 **/
public class TomcatTest {
    public static void main(String[] args) throws Exception {
        GPTomcat tomcat = new GPTomcat();
        tomcat.start();
    }
}

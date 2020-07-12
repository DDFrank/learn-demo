package com.hust.hui.quicksilver.file.test;

import com.hust.hui.quicksilver.file.PropertiesUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by yihui on 2017/5/8.
 */
public class PropertiesUtilTest {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtilTest.class);


    @Test
    public void testReadProperties() throws IOException {
        String fileName = "jdbc.properties";
        Properties properties = PropertiesUtil.read(fileName);
        logger.info("properties: {}", properties);
    }

}

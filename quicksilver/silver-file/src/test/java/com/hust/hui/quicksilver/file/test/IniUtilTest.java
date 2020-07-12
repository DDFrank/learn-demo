package com.hust.hui.quicksilver.file.test;

import com.hust.hui.quicksilver.file.IniUtil;
import org.ini4j.Ini;
import org.ini4j.Profile;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by yihui on 2017/5/8.
 */
public class IniUtilTest {

    private static final Logger logger = LoggerFactory.getLogger(IniUtilTest.class);

    @Test
    public void testIniUtilRead() throws IOException {

        String fileName = "test.ini";


        Ini ini = IniUtil.read(fileName);
        Profile.Section section = ini.get("system");
        logger.info("section: {}", section);
    }

}

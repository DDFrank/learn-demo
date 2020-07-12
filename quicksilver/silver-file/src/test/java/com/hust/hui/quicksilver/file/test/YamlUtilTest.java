package com.hust.hui.quicksilver.file.test;

import com.hust.hui.quicksilver.file.YamlUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by yihui on 2017/5/8.
 */
public class YamlUtilTest {
    private static final Logger logger = LoggerFactory.getLogger(YamlUtilTest.class);

    @Getter
    @Setter
    @ToString
    public static class Me {
        private Integer age;
        private String name;
        private Map<String, Object> params;
        private List<String> favoriteBooks;

        public Me() {
        }
    }

    @Test
    public void testYamlRead() throws IOException {
        String fileName = "test.yaml";
        Me me = YamlUtil.read(fileName, Me.class);
        logger.info("me: {}", me);
    }

}

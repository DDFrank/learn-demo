package com.hust.hui.quicksilver.file.test;

import com.alibaba.fastjson.TypeReference;
import com.hust.hui.quicksilver.file.JsonUtil;
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
 * Created by yihui on 2017/5/6.
 */
public class JsonUtilTest {

    private static final Logger logger = LoggerFactory.getLogger(FileReadUtilTest.class);


    @Getter
    @Setter
    @ToString
    private static class JsonDO {
        private User user;

        private Address address;

        @Getter
        @Setter
        @ToString
        static class User {
            private List<String> name;

            private Info info;
        }


        @Getter
        @Setter
        @ToString
        static class Info {
            private Integer age;

            private String sex;
        }


        @Getter
        @Setter
        @ToString
        static class Address {
            private Long phone;

            private Boolean qq;

            private Map<String, String> email;
        }
    }

    @Test
    public void testJsonRead() throws IOException {
        String fileName = "info.json";

        TypeReference<JsonDO> typeReference = new TypeReference<JsonDO>() {};

        JsonDO jsonDO = JsonUtil.read(fileName, typeReference);
        logger.info("the csv words: {}", jsonDO);
    }

}

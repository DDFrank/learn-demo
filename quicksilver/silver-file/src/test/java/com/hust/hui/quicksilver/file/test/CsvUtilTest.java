package com.hust.hui.quicksilver.file.test;

import com.hust.hui.quicksilver.file.CsvUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.csv.CSVRecord;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yihui on 2017/5/6.
 */
public class CsvUtilTest {

    private static final Logger logger = LoggerFactory.getLogger(FileReadUtilTest.class);


    @Getter
    @Setter
    @ToString
    private static class WordDO {
        long dicId;

        String name;

        Boolean rootWord;

        Float weight;

        public WordDO() {
        }
    }


    @Test
    public void testRef() throws IllegalAccessException, InstantiationException {
        WordDO wordDO = WordDO.class.newInstance();
        logger.info("----");
    }


    @Test
    public void testCsvRead() throws IOException {
        String fileName = "word.csv";
        List<CSVRecord> list = CsvUtil.read(fileName, new String[]{"dicId", "name", "rootWord", "weight"});
        Assert.assertTrue(list != null && list.size() > 0);

        List<WordDO> words = list.stream()
                .filter(csvRecord -> !"dicId".equals(csvRecord.get("dicId")))
                .map(this::parseDO).collect(Collectors.toList());

        words.stream().forEach(
            word -> logger.info("the csv words: {}", word)
        );
    }


    private WordDO parseDO(CSVRecord csvRecord) {
        WordDO wordDO = new WordDO();
        wordDO.dicId = Integer.parseInt(csvRecord.get("dicId"));
        wordDO.name = csvRecord.get("name");
        wordDO.rootWord = Boolean.valueOf(csvRecord.get("rootWord"));
        wordDO.weight = Float.valueOf(csvRecord.get("weight"));
        return wordDO;
    }



    @Test
    public void testCsvReadV2() throws IOException {
        String fileName = "word.csv";
        List<CSVRecord> list = CsvUtil.read(fileName, new String[]{"dicId", "name", "rootWord", "weight"});
        Assert.assertTrue(list != null && list.size() > 0);

        try {
            List<WordDO> words = new ArrayList<>(list.size() - 1);
            for (int i = 1; i < list.size(); i++) {
                words.add(parseDO(list.get(i), WordDO.class));
            }

            words.stream().forEach(
                    word -> logger.info("the csv words: {}", word)
            );
        } catch (Exception e) {
            logger.error("parse DO error! e: {}", e);
        }
    }


    private <T> T parseDO(CSVRecord csvRecord, Class<T> clz) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        // 创建DO对象
        T obj = clz.newInstance();


        // 获取声明的所有成员变量
        Field[] fields = clz.getDeclaredFields();


        // 保存属性对应的csvRecord中的值
        String value;
        String fieldSetMethodName;
        Object fieldValue;
        for (Field field : fields) {
            // 设置为可访问
            field.setAccessible(true);

            // 将value转换为目标类型
            value = csvRecord.get(field.getName());
            if (value == null) {
                continue;
            }
            fieldValue = this.parseType(value, field.getType());


            // 获取属性对应的设置方法
            fieldSetMethodName = "set" + upperCase(field.getName());


            Method method = clz.getDeclaredMethod(fieldSetMethodName, field.getType());

            method.invoke(obj, fieldValue);
        }


        return obj;
    }


    // 首字母变大写
    private String upperCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }


    /**
     * 类型转换
     *
     * @param value 原始数据格式
     * @param type 期待转换的类型
     * @return 转换后的数据对象
     */
    private Object parseType(String value, Class type) {

        if (type == String.class) {
            return value;
        } else if (type == int.class) {
            return value == null ? 0 : Integer.parseInt(value);
        } else if (type == float.class) {
            return value == null ? 0f : Float.parseFloat(value);
        } else if (type == long.class) {
            return value == null ? 0L : Long.parseLong(value);
        } else if (type == double.class) {
            return value == null ? 0D : Double.parseDouble(value);
        } else if (type == boolean.class) {
            return value != null && Boolean.parseBoolean(value);
        } else if (type == byte.class) {
            return value == null || value.length() == 0 ? 0 : value.getBytes()[0];
        } else if (type == char.class) {
            if (value == null || value.length() == 0) {
                return 0;
            }

            char[] chars = new char[1];
            value.getChars(0, 1, chars, 0);
            return chars[0];
        }

        // 非基本类型,
        if (StringUtils.isEmpty(value)) {
            return null;
        }


        if (type == Integer.class) {
            return Integer.valueOf(value);
        } else if (type == Long.class) {
            return Long.valueOf(value);
        } else if (type == Float.class) {
            return Float.valueOf(value);
        } else if (type == Double.class) {
            return Double.valueOf(value);
        } else if (type == Boolean.class) {
            return Boolean.valueOf(value);
        } else if (type == Byte.class) {
            return value.getBytes()[0];
        } else if (type == Character.class) {
            char[] chars = new char[1];
            value.getChars(0, 1, chars, 0);
            return chars[0];
        }


        throw new IllegalStateException("argument not basic type! now type:" + type.getName());
    }
}

package io.frank.spring.jooq.common.util;

import org.jooq.Field;
import org.jooq.impl.DSL;

/**
 * 封装JOOQ的Plain Sql， 提供一些特殊的查询字段
 *
 * @author jinjunliang
 **/
public class FileldDslUtil {
    /**
     * 提供Sql字段的格式化
     * @param str 被格式化的字段名
     * @return 字段
     */
    public static Field<String>  TimeDataFormat(String str, String alias) {
        return DSL.field("DATE_FORMAT({0}, '%H:%i:%s')", String.class, str).as(alias);
    }

    public static Field<String>  TimeDataFormat(String str) {
        return DSL.field("DATE_FORMAT({0}, '%H:%i:%s')", String.class, str).as(str);
    }
}

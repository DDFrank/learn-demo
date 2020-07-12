package com.hust.hui.quicksilver.spi.selector.api;

import com.hust.hui.quicksilver.spi.api.SpiConf;
import lombok.*;

import java.util.Map;

/**
 * Created by yihui on 2017/5/24.
 *
 * SPI实现类的包装类，包含了 name属性，解析后的参数map，优先级并可比较
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SpiImplWrapper<T> implements Comparable<SpiImplWrapper> {

    public static final int DEFAULT_ORDER = 10;

    private T spiImpl;

    private int order;

    /**
     * spiImpl 的标识name, 要求唯一
     * <p/>
     * {@link com.hust.hui.quicksilver.spi.selector.DefaultSelector 选择具体的SpiImpl 时使用}
     */
    private String name;


    /**
     * 参数校验规则
     * <p/>
     * {@link com.hust.hui.quicksilver.spi.selector.ParamsSelector} 选择具体的SpiImpl 时使用
     * 要求每个实现类都有注解  {@link SpiConf}
     */
    private Map<String, String> paramCondition;

    @Override
    public int compareTo(SpiImplWrapper o) {
        if (o == null) {
            throw new IllegalArgumentException("compable object should not be null!");
        }

        return order - o.getOrder();
    }
}

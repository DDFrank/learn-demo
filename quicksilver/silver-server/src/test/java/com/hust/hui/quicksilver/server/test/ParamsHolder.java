package com.hust.hui.quicksilver.server.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by yihui on 2017/4/27.
 */
public class ParamsHolder {
    private static final ThreadLocal<Params> PARAMS_INFO = new ThreadLocal<>();


    @ToString
    @Getter
    @Setter
    public static class Params {
        private String mk;

        public Params(String mk) {
            this.mk = mk;
        }
    }


    public static void setParams(Params params) {
        PARAMS_INFO.set(params);
    }


    public static void clear() {
        PARAMS_INFO.remove();
    }


    public static Params get() {
        return PARAMS_INFO.get();
    }
}


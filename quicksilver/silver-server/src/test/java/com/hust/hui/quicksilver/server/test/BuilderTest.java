package com.hust.hui.quicksilver.server.test;


/**
 * Created by yihui on 2017/4/20.
 */
public class BuilderTest {

    private int x;

    private int y;

    public BuilderTest() {
    }

    public BuilderTest(Builder builder) {
        this.x = builder.getX();
        this.y = builder.getY();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static class Builder {
        private int x;
        private int y;

        public int getX() {
            return x;
        }

        public Builder setX(int x) {
            this.x = x;
            return this;
        }

        public int getY() {
            return y;
        }

        public Builder setY(int y) {
            this.y = y;
            return this;
        }


        public BuilderTest build() {
            return new BuilderTest(this);
        }
    }
}

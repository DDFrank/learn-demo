package com.hust.hui.quicksilver.commons.test.listener.thread;

import org.junit.Test;

/**
 * Created by yihui on 2017/6/6.
 */
public class ThreadCalculateTest {


    public static class CalculateThread extends Thread {

        private int start;
        private int end;

        private int ans;

        public CalculateThread(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public void run() {
            for (int i = start; i <= end; i++) {
                ans += i;
            }
        }

        public int getAns() {
            return ans;
        }
    }


    @Test
    public void testCalculate() throws InterruptedException {
        CalculateThread c1 = new CalculateThread(1, 25);
        CalculateThread c2 = new CalculateThread(26, 50);
        CalculateThread c3 = new CalculateThread(51, 75);
        CalculateThread c4 = new CalculateThread(76, 100);

        c1.start();
        c2.start();
        c3.start();
        c4.start();


        c1.join();
        c2.join();
        c3.join();
        c4.join();

        System.out.println("ans1: " + c1.getAns() + " ans2: " + c2.getAns() + " ans3: " + c3.getAns() + " ans4: " + c4.getAns());
        int ans = c1.getAns() + c2.getAns() + c3.getAns() + c4.getAns();
        System.out.println("ans : " + ans);
    }
}

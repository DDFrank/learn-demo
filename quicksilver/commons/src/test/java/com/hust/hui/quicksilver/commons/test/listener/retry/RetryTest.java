//package com.hust.hui.quicksilver.commons.test.listener.retry;
//
//import com.hust.hui.quicksilver.commons.retry.RetryTemplate;
//import org.junit.Test;
//
///**
// * Created by yihui on 2017/8/30.
// */
//public class RetryTest {
//
//
//    public void retryDemo() throws InterruptedException {
//        RetryTemplate template = new RetryTemplate() {
//            @Override
//            protected Object doBiz() throws Exception {
//                int temp = (int) (Math.random() * 10);
//                System.out.println(temp);
//
//                if (temp > 3) {
//                    throw new Exception("generate value bigger then 3! need retry");
//                }
//
//                return temp;
//            }
//        };
//
//
//        template.setRetryTime(10).setSleepTime(10).execute();
//
//
//        Object ans = new RetryTemplate() {
//            @Override
//            protected Object doBiz() throws Exception {
//                int temp = (int) (Math.random() * 10);
//                System.out.println(temp);
//
//                if (temp > 3) {
//                    throw new Exception("generate value bigger then 3! need retry");
//                }
//
//                return temp;
//            }
//        }.setRetryTime(10).setSleepTime(10).execute();
//        System.out.println(ans);
//    }
//
//
//    @Test
//    public void testRetry() throws InterruptedException {
//        for(int i =0 ; i< 10; i++) {
//            System.out.println("--------------");
//            retryDemo();
//        }
//    }
//
//}

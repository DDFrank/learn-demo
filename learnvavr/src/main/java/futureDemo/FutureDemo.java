package futureDemo;

import io.vavr.concurrent.Future;

/**
 * @author jinjunliang
 **/
public class FutureDemo {
    public static void main(String[] args) {
        //run1();
        //run2();
        run3();
    }

    public static void run1() {
        Future.of(() -> "first ")
                .map(s -> s + "second ")
                .flatMap(s -> Future.of(() -> s + "last"))
                .onSuccess(System.out::println)
                .onFailure(s -> System.out.println("失败了"));
    }
    // 不处理最终的错误
    public static void run2() {
        Future.of(() -> {throw new RuntimeException("报错了");})
                .map(s -> s + "second ")
                .flatMap(s -> Future.of(() -> {
                    System.out.println("执行了操作2");
                    return s + "last";
                }))
                .onSuccess(System.out::println)
                .onFailure(throwable -> System.out.println("失败了:" + throwable.getMessage()));
    }

    // 利用 Try 来处理错误
    public static void run3() {
        Future.of(() -> "aa")
                .filter(s -> s.equalsIgnoreCase("bb"))
                .map(s -> s + "second ")
                .flatMap(s -> Future.of(() -> {
                    System.out.println("执行了操作2");
                    return s + "last";
                }))
                .onSuccess(System.out::println)
                .onFailure(throwable ->
                        System.out.println("失败了:" + throwable.getMessage()));
    }

}

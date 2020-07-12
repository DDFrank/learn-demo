package io.frank.learn.jdk14.flow;

import java.util.concurrent.Flow;

/**
 * @author jinjunliang
 **/
public class DockerXDemoSubscriber<T> implements Flow.Subscriber<T> {
    private String name;
    private Flow.Subscription subscription;
    final long bufferSize;
    long count;

    public DockerXDemoSubscriber(String name, long bufferSize) {
        this.name = name;
        this.bufferSize = bufferSize;
    }

    public String getName() {
        return name;
    }

    public Flow.Subscription getSubscription() {
        return subscription;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        // count = bufferSIze = bufferSize / 2;
        // 在订阅上的时候先请求了一波，此时因为没有数据，所以肯定会收到null
        (this.subscription = subscription).request(bufferSize);
        System.out.println("开始onSubscribe订阅");
        try {
            Thread.sleep(100);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNext(T item) {
        //if (--count <= 0) subscription.request(count = bufferSize - bufferSize/2);
        System.out.println(String.format(" ##### %s name: %s item: %s #####", Thread.currentThread().getName(), name, item));
        System.out.println(String.format("%s received: %s", name, item));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("Completed");
    }
}

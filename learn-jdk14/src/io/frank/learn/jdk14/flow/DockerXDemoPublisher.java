package io.frank.learn.jdk14.flow;

import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow;
import java.util.concurrent.Future;

/**
 * @author jinjunliang
 **/
public class DockerXDemoPublisher<T> implements Flow.Publisher<T>, AutoCloseable {
    private final ExecutorService executor;
    private CopyOnWriteArrayList<DockerXDemoSubscription<T>> list = new CopyOnWriteArrayList<>();

    public DockerXDemoPublisher(ExecutorService executor) {
        this.executor = executor;
    }

    public void submit(final T item) {
        System.out.println(String.format("**************** 开始发布元素item: %s *************", item.toString()));
        list.forEach(subscription ->
                subscription.future = executor.submit(() -> subscription.subscriber.onNext(item))
        );
    }

    @Override
    public void close() {
        list.forEach(e -> executor.submit(e.subscriber::onComplete));
    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        subscriber.onSubscribe(new DockerXDemoSubscription<>(subscriber, executor));
        list.add(new DockerXDemoSubscription<>(subscriber, executor));
    }


    static class DockerXDemoSubscription<T> implements Flow.Subscription {
        private final Flow.Subscriber<? super T> subscriber;
        private final ExecutorService executor;
        private Future<?> future;
        private T item;
        private boolean completed;

        public DockerXDemoSubscription(Flow.Subscriber<? super T> subscriber, ExecutorService executor) {
            this.subscriber = subscriber;
            this.executor = executor;
        }

        @Override
        public void request(long n) {
            if (n !=0 && !completed) {
                if (n < 0) {
                    executor.execute(() -> subscriber.onError(new IllegalArgumentException()));
                } else {
                    future = executor.submit(() -> subscriber.onNext(item));
                }
            } else {
                subscriber.onComplete();
            }
        }

        @Override
        public void cancel() {
            completed = true;
            if (Objects.nonNull(future) && !future.isCancelled()) {
                this.future.cancel(true);
            }
        }
    }
}

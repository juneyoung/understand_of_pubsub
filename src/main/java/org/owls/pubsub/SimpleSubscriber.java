package org.owls.pubsub;

import org.junit.jupiter.api.Assertions;
import org.owls.pubsub.vo.Message;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

public class SimpleSubscriber<T extends Message, R> implements Subscriber<T> {

    private final String name;
    private final Consumer<T> callback;
    private final BlockingQueue<T> receivedQueue;
    private static final int RECEIVE_QUEUE_SIZE = 3;
    public SimpleSubscriber(String name, Consumer<T> callback) {
        Assertions.assertNotNull(callback, "callback can not be empty");
        this.name = name;
        this.callback = callback;
        this.receivedQueue = new ArrayBlockingQueue<>(RECEIVE_QUEUE_SIZE);
    }

    @Override
    public void handleMessage(T message) {
        this.receivedQueue.offer(message);
    }

    @Override
    public void run() {
        while (true) {
            T pick = this.receivedQueue.poll();
            if (pick != null) {
                System.out.println(String.format("subscriber [ %s ] received message: %s", this.name, pick));
                this.callback.accept(pick);
            }
        }
    }
}

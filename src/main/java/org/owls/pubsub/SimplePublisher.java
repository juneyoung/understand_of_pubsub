package org.owls.pubsub;

import org.owls.pubsub.vo.Message;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SimplePublisher<T extends Message> implements Publisher<T> {

    private final String name;

    private final BlockingQueue<T> messageQueue;
    private final Set<Subscriber> subscribers;
    private final Set<Thread> subscriberManager;

    public SimplePublisher(String name, int queueSize) {
        this.name = name;
        this.messageQueue = new ArrayBlockingQueue<>(queueSize);
        this.subscribers = new HashSet<>();
        this.subscriberManager = new HashSet<>();
    }

    @Override
    public void registerSubscriber(Subscriber subscriber) {
        this.subscribers.add(subscriber);
    }

    @Override
    public void issueMessage(T message) {
        /**
         * queue 가 꽉 찼을 때, add 는 예외를 던지고, offer 는 false 를 반환
         */
        this.messageQueue.offer(message);
    }

    @Override
    public void run() {
        for(Subscriber subscriber: this.subscribers) {
            Thread t = new Thread(subscriber);
            subscriberManager.add(t);
            t.start();
        }

        while(true) {
            /**
             * poll - null 반환
             * take - null 이 아닌게 나올 때까지 대기
             */
            T pick = this.messageQueue.poll();
            if (pick != null) {
                System.out.println(String.format("publisher [ %s ] issues a message: %s", this.name, pick));
                for (Subscriber subscriber : this.subscribers) {
                    subscriber.handleMessage(pick);
                }
            }
        }
    }
}

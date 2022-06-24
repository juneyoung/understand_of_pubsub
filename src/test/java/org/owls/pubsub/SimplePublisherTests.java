package org.owls.pubsub;

import org.junit.jupiter.api.Test;
import org.owls.pubsub.vo.SimpleMessage;

import java.util.Arrays;
import java.util.List;

public class SimplePublisherTests {
    private static final int MAX_QUEUE_SIZE = 5;

    @Test
    public void testSubscribe() {

        Publisher publisher = new SimplePublisher("pub1", MAX_QUEUE_SIZE);
        List<Subscriber> subscribers = Arrays.asList(
                new SimpleSubscriber<>("sub1", (message) -> {
                    System.out.println(String.format("sub1 - getMessage: %s", message));
                }),
                new SimpleSubscriber<>("sub2", (message) -> {
                    System.out.println(String.format("sub2 - getMessage: %s", message));
                }),
                new SimpleSubscriber<>("sub3", (message) -> {
                    System.out.println(String.format("sub3 - getMessage: %s", message));
                })
        );
        for (Subscriber s : subscribers) {
            publisher.registerSubscriber(s);
        }
        Thread publisherThread = new Thread(publisher);
        publisherThread.start();

        publisher.issueMessage(new SimpleMessage("에라이 메세지다", "빨리 발행하지 못해잇!!!"));

        try {
            publisherThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

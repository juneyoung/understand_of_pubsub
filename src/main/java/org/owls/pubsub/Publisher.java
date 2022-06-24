package org.owls.pubsub;

import org.owls.pubsub.vo.Message;

public interface Publisher<T extends Message> extends Runnable {
    void registerSubscriber(Subscriber subscriber);
    void issueMessage(T message);
}

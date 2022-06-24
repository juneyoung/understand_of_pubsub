package org.owls.pubsub;

import org.owls.pubsub.vo.Message;

public interface Subscriber<T extends Message> extends Runnable {
    void handleMessage(T message);
}

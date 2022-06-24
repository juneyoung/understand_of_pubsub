package org.owls.pubsub.vo;

public class SimpleMessage implements Message {
    private final String title;
    private final String message;

    public SimpleMessage(String title, String message) {
        this.title = title;
        this.message = message;
    }

    @Override
    public String toString() {
        return "SimpleMessage{" +
                "title='" + title + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

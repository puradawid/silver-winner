package edu.pw.javabus;

public interface Bus {

    <T extends Message> void register(Consumer<T> consumer, Topic topic, Class<T> messageType);

    <T extends Message> void unregister(Consumer<T> consumer, Topic topic);

    <T extends Message> void send(Topic topic, T message);
}

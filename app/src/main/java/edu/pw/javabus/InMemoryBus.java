package edu.pw.javabus;

public class InMemoryBus implements Bus {

    private final RegisteredConsumers registeredConsumers = new RegisteredConsumers();

    private final MessageDelivery messageDelivery;

    InMemoryBus(MessageDelivery messageDelivery) {
        this.messageDelivery = messageDelivery;
    }

    @Override
    public <T extends Message> void register(Consumer<T> consumer, Topic topic, Class<T> messageType) {
        registeredConsumers.add(consumer, topic, messageType);
    }

    @Override
    public <T extends Message> void unregister(Consumer<T> consumer, Topic topic) {
        registeredConsumers.remove(consumer, topic);
    }

    @Override
    public <T extends Message> void send(Topic topic, T message) {
        messageDelivery.deliver(registeredConsumers, topic, message);
    }

}

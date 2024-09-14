package edu.pw.javabus;

import java.util.concurrent.Executor;

public class InMemoryBus implements Bus {

    private final RegisteredConsumers registeredConsumers = new RegisteredConsumers();

    private final DeliveryStrategy deliveryStrategy;

    public InMemoryBus(DeliveryStrategy deliveryStrategy) {
        this.deliveryStrategy = deliveryStrategy;
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
        deliveryStrategy.deliver(registeredConsumers, topic, message);
    }

}

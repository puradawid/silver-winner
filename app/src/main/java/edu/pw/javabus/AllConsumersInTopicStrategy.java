package edu.pw.javabus;

public class AllConsumersInTopicStrategy implements DeliveryStrategy {
    @Override
    public void deliver(RegisteredConsumers consumers, Topic topic, Message message) {
        consumers.find(topic, message.getClass())
                .stream().map(ConsumeMethodObject::new)
                .forEach(consumer -> consumer.consume(message));
    }
}

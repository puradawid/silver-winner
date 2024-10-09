package edu.pw.javabus;

import java.util.List;

public class AllConsumersInTopicStrategy implements MessageDelivery {

    @Override
    public void deliver(RegisteredConsumers consumers, Topic topic, Message message) {
        while (topic != null) {
            List<Consumer<? extends Message>> potentialConsumers = consumers.find(topic, message.getClass());
            for (Consumer<? extends Message> consumer : potentialConsumers) {
                new ConsumeMethodObject(consumer).consume(message);
            }
            topic = topic.getParent();
        }
    }
}

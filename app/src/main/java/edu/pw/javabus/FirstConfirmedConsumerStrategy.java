package edu.pw.javabus;

import java.util.Iterator;
import java.util.List;

class FirstConfirmedConsumerStrategy implements DeliveryStrategy {

    @Override
    public void deliver(RegisteredConsumers consumers, Topic topic, Message message) {
        boolean sent = false;
        while (!sent && topic != null) {
            List<Consumer<? extends Message>> potentialConsumers = consumers.find(topic, message.getClass());
            Iterator<Consumer<? extends Message>> iterator = potentialConsumers.iterator();
            while (iterator.hasNext()) {
                Consumer<? extends Message> consumer = iterator.next();
                Confirmation c = new ConsumeMethodObject(consumer).consume(message);
                if (c.isSuccess()) {
                    sent = true;
                    break;
                }
            }
            if (!sent) {
                topic = topic.getParent();
            }
        }
    }

}

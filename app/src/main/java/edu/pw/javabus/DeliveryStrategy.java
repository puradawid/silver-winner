package edu.pw.javabus;

interface DeliveryStrategy {
    void deliver(RegisteredConsumers consumers, Topic topic, Message message);
}

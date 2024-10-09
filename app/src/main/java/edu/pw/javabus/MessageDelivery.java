package edu.pw.javabus;

/**
 * Addresses the message delivery method. It's called in {@link InMemoryBus} when the bus is about to send method to its
 * registered consumers.
 */
interface MessageDelivery {
    /**
     * Embodies delivery method. It should get necessary consumers from the consumers parameter of the required topic and
     * send the appropriate message. Also, this method is responsible for sending the message - it can also send no messages at all.
     *
     * @param consumers registered consumers
     * @param topic     topic of the message to be sent
     * @param message   a message to be sent
     */
    void deliver(RegisteredConsumers consumers, Topic topic, Message message);
}

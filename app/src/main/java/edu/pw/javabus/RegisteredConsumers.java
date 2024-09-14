package edu.pw.javabus;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class RegisteredConsumers {

    private final Map<Topic, Map<Class<? extends Message>, List<Consumer<? extends Message>>>> consumers = new HashMap<>();

    void add(Consumer<?> consumer, Topic topic, Class<? extends Message> messageType) {
        Map<Class<? extends Message>, List<Consumer<? extends Message>>> messageToConsumers = consumers.getOrDefault(topic, new HashMap<>());
        List<Consumer<? extends Message>> otherConsumers = messageToConsumers.getOrDefault(messageType, new LinkedList<>());
        otherConsumers.add(consumer);
        messageToConsumers.put(messageType, otherConsumers);
        consumers.put(topic, messageToConsumers);
    }

    void remove(Consumer<?> consumer, Topic topic) {
        consumers.getOrDefault(topic, Map.of()).entrySet().forEach(x -> x.getValue().remove(consumer));
    }

    List<Consumer<? extends Message>> find(Topic topic, Class<? extends Message> messageType) {
        return Optional.ofNullable(consumers.get(topic)).map(m -> m.get(messageType)).orElse(List.of());
    }

}

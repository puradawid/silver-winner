package edu.pw.javabus;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BusTest {

    @Test
    void shouldRegisterConsumer() {
        Bus bus = new InMemoryBus(new AllConsumersInTopicStrategy());
        DummyConsumer consumer = new DummyConsumer();
        bus.register(consumer, new StringNamedTopic("testTopic"), DummyMessage.class);

        bus.send(new StringNamedTopic("testTopic"), new DummyMessage());

        assertTrue(consumer.wasCalled());
    }

    @Test
    void shouldNotifyBothConsumers() {
        Bus bus = new InMemoryBus(new AllConsumersInTopicStrategy());
        Topic topic = new StringNamedTopic("testTopic");
        DummyConsumer consumer = new DummyConsumer();
        bus.register(consumer, topic, DummyMessage.class);
        DummyMessage message = new DummyMessage();
        DummyConsumer consumer2 = new DummyConsumer();
        bus.register(consumer2, topic, DummyMessage.class);

        bus.send(topic, message);

        assertTrue(consumer.wasCalled());
        assertTrue(consumer2.wasCalled());
    }

    @Test
    void shouldUnregisterConsumer() {
        Bus bus = new InMemoryBus(new AllConsumersInTopicStrategy());
        DummyConsumer consumer = new DummyConsumer();
        bus.register(consumer, new StringNamedTopic("testTopic"), DummyMessage.class);
        bus.unregister(consumer, new StringNamedTopic("testTopic"));

        bus.send(new StringNamedTopic("testTopic"), new DummyMessage());

        assertFalse(consumer.wasCalled());
    }

    @Test
    void shouldCallParentTopic() {
        Bus bus = new InMemoryBus(new AllConsumersInTopicStrategy());
        DummyConsumer consumer = new DummyConsumer();
        StringNamedTopic mainTopic = new StringNamedTopic("test2");
        StringNamedTopic secondaryTopic = new StringNamedTopic("test1", mainTopic);

        bus.register(consumer, mainTopic, DummyMessage.class);
        bus.send(secondaryTopic, new DummyMessage());

        assertTrue(consumer.wasCalled());
    }

    @Test
    @Disabled
    void shouldCallInheritedMessage() {
        Bus bus = new InMemoryBus(new AllConsumersInTopicStrategy());
        DummyConsumer consumer = new DummyConsumer();
        StringNamedTopic mainTopic = new StringNamedTopic("test2");
        StringNamedTopic secondaryTopic = new StringNamedTopic("test1", mainTopic);

        bus.register(consumer, mainTopic, DummyMessage.class);
        bus.send(secondaryTopic, new InheritedDummyMessage());

        assertTrue(consumer.wasCalled());
    }

    @Test
    @Disabled
    void shouldCallOnlyFirstConsumerWithinTheTopic() {
        Bus bus = new InMemoryBus(new AllConsumersInTopicStrategy());
        DummyConsumer consumer = new DummyConsumer();
        DummyConsumer consumer1 = new DummyConsumer();

        StringNamedTopic topic = new StringNamedTopic("test1");

        bus.register(consumer, topic, DummyMessage.class);
        bus.register(consumer1, topic, DummyMessage.class);

        bus.send(topic, new DummyMessage());

        assertTrue(consumer.wasCalled());
        assertFalse(consumer1.wasCalled());
    }

    @Test
    @Disabled
    void shouldCallNextConsumerInCaseOfFailure() {
        Bus bus = new InMemoryBus(new AllConsumersInTopicStrategy());
        DummyConsumer consumer = new FailingConsumer();
        DummyConsumer consumer1 = new DummyConsumer();
        DummyConsumer consumer2 = new DummyConsumer();

        StringNamedTopic topic = new StringNamedTopic("test1");

        bus.register(consumer, topic, DummyMessage.class);
        bus.register(consumer1, topic, DummyMessage.class);
        bus.register(consumer2, topic, DummyMessage.class);

        bus.send(topic, new DummyMessage());

        assertTrue(consumer.wasCalled());
        assertTrue(consumer1.wasCalled());
        assertFalse(consumer2.wasCalled());
    }

}
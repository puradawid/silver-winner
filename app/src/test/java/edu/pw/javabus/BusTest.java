package edu.pw.javabus;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BusTest {

    @Test
    void shouldRegisterConsumer() {
        Bus bus = new InMemoryBus(new FirstConfirmedConsumerStrategy());
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
        Bus bus = new InMemoryBus(new FirstConfirmedConsumerStrategy());
        DummyConsumer consumer = new DummyConsumer();
        bus.register(consumer, new StringNamedTopic("testTopic"), DummyMessage.class);
        bus.unregister(consumer, new StringNamedTopic("testTopic"));

        bus.send(new StringNamedTopic("testTopic"), new DummyMessage());

        assertFalse(consumer.wasCalled());
    }

    @Test
    void shouldCallParentTopic() {
        Bus bus = new InMemoryBus(new FirstConfirmedConsumerStrategy());
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
        Bus bus = new InMemoryBus(new FirstConfirmedConsumerStrategy());
        DummyConsumer consumer = new DummyConsumer();
        StringNamedTopic mainTopic = new StringNamedTopic("test2");
        StringNamedTopic secondaryTopic = new StringNamedTopic("test1", mainTopic);

        bus.register(consumer, mainTopic, DummyMessage.class);
        bus.send(secondaryTopic, new InheritedDummyMessage());

        assertTrue(consumer.wasCalled());
    }

    @Test
    void shouldCallOnlyFirstConsumerWithinTheTopic() {
        Bus bus = new InMemoryBus(new FirstConfirmedConsumerStrategy());
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
    void shouldCallNextConsumerInCaseOfFailure() {
        Bus bus = new InMemoryBus(new FirstConfirmedConsumerStrategy());
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

/*
    Requirements:
    - when consumer is registered, all events sent to the topic are handled by the consumer
    - when consumer is deregistered, none events are sent
    - topic can be inherited, meaning some topics are subtopics (tree implementation)
    - if the confirmation is "broken", then the processing stop or continues
    - there are different strategies of passing the message by
    -- bubbling up
    -- only first item
    -- all items within the topic
    -- all consumers in the topic and above
    - also, there are different strategies for sending the event, too

    My problem is: can I present in this use case two classes that can have different coupling factors between them?

    A -> B OR A -> B' where coupling(A -> B) < coupling (A -> B')

    This was possible in case of a class that carries a data vs methods, but I am not sure if this is possible in this project.

    Which classes can represent data?
    * Topic
    * Message

    I have introduced ConsumersCollection as a class used internally, with which InMemoryBus uses to store
    consumers and related topics.

    This class has the find method, which returns the item. Move find method to InMemoryBus will increase coupling by
    number of method calls? Not so much if this is only getting the collection back, but perhaps I could write something
    else, like getTopic registered or something similar.

    Then, what is the task to be performed? I could ask to change the behaviour of item to pick the first topic only.
    I have to model this solution, too.



     */


}
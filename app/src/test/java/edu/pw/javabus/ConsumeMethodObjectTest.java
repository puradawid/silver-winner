package edu.pw.javabus;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsumeMethodObjectTest {

    @Test
    void shouldRunConsumeInRegularClasses() {
        DummyConsumer consumer = new DummyConsumer();
        ConsumeMethodObject consumeMethodObject = new ConsumeMethodObject(consumer);

        consumeMethodObject.consume(new DummyMessage());

        assertTrue(consumer.wasCalled());
    }

    @Test void shouldRunInheritedMessage() {
        DummyConsumer consumer = new DummyConsumer();
        ConsumeMethodObject consumeMethodObject = new ConsumeMethodObject(consumer);

        consumeMethodObject.consume(new DummyMessage() {

        });

        assertTrue(consumer.wasCalled());
    }

}
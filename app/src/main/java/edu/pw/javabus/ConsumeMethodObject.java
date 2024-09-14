package edu.pw.javabus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Wraps a {@link Consumer} object that can pass consume message of any class inheriting from {@link Message}
 */
class ConsumeMethodObject {

    private final Consumer<? extends Message> consumer;

    ConsumeMethodObject(Consumer<? extends Message> consumer) {
        this.consumer = consumer;
    }

    Confirmation consume(Message message) {
        try {
            Method m = consumer.getClass().getMethod("consume", message.getClass());
            Object result = m.invoke(consumer, message);
            if (result instanceof Confirmation) {
                return (Confirmation) result;
            } else {
                return new Failure();
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}

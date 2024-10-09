package edu.pw.javabus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Wraps a {@link Consumer} object that can pass consume message of any class inheriting from {@link Message}.
 * It solves the problem with different methods that are using
 * <code>
 *     // given some consumer with RandomMessage
 *     class MethodicalConsumer implements Consumer<RandomMessage> {
 *         Confirmation consume(RandomMessage m) {}
 *     };
 *     // and its instance with broader generic
 *     Consumer<? extends Message> consumer = new MethodicalConsumer();
 *     // calling the consume method is actually easy and fun!
 *     new ConsumeMethodObject(consumer).consume(new RandomMessage());
 * </code>
 */
class ConsumeMethodObject {

    private static final String METHOD_NAME = "consume";

    private final Consumer<? extends Message> consumer;

    ConsumeMethodObject(Consumer<? extends Message> consumer) {
        this.consumer = consumer;
    }

    Confirmation consume(Message message) {
        try {
            Method[] methods = consumer.getClass().getMethods();

            for (Method method : methods) {
                if (METHOD_NAME.equals(method.getName())) {
                    return readConfirmation(method.invoke(consumer, message));
                }
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return new Failure();
    }

    private Confirmation readConfirmation(Object result) {
        if (result instanceof Confirmation) {
            return (Confirmation) result;
        } else {
            return new Failure();
        }
    }


}

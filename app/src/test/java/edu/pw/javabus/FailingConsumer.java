package edu.pw.javabus;

public class FailingConsumer extends DummyConsumer {

    @Override
    public Confirmation consume(DummyMessage dummyMessage) {
        super.consume(dummyMessage);
        return new Failure();
    }
}

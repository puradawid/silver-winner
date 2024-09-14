package edu.pw.javabus;

public class DummyConsumer implements Consumer<DummyMessage> {

    private boolean called = false;

    @Override
    public Confirmation consume(DummyMessage dummyMessage) {
        this.called = true;
        return new PlainConfirmation();
    }

    boolean wasCalled() {
        return called;
    }


}

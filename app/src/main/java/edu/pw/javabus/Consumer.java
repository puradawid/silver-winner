package edu.pw.javabus;

public interface Consumer<MessageType extends Message> {

    Confirmation consume(MessageType messageType);
}

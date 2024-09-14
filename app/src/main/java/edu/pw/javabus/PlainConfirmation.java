package edu.pw.javabus;

public class PlainConfirmation implements Confirmation {

    @Override
    public boolean isSuccess() {
        return true;
    }
}

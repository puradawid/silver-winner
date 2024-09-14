package edu.pw.javabus;

class Failure implements Confirmation {

    @Override
    public boolean isSuccess() {
        return false;
    }
}

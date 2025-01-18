package agh.ics.oop.errors;

public class FailedToReadConfig extends RuntimeException {
    public FailedToReadConfig() {
        super("Reading configuration failed.");
    }
}

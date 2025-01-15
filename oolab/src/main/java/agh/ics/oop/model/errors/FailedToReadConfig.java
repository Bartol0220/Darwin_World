package agh.ics.oop.model.errors;

public class FailedToReadConfig extends RuntimeException {
    public FailedToReadConfig() {
        super("Reading configuration failed.");
    }
}

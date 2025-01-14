package agh.ics.oop.model.errors;

public class MinMaxException extends RuntimeException {
    public MinMaxException() {
        super("Minimum value can not be higher that maximum value.");
    }
}

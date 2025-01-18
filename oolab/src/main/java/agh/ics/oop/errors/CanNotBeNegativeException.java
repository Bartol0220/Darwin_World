package agh.ics.oop.errors;

public class CanNotBeNegativeException extends RuntimeException {
    public CanNotBeNegativeException(String valueName) {
        super(valueName + " can not be negative.");
    }
}

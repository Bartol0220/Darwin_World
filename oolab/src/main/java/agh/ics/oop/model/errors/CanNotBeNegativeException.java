package agh.ics.oop.model.errors;

public class CanNotBeNegativeException extends RuntimeException {
    public CanNotBeNegativeException(String valueName) {
        super(valueName + " can not be negative.");
    }
}

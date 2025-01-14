package agh.ics.oop.model.errors;

public class HasToBePositiveException extends Exception {
    public HasToBePositiveException(String valueName) {
        super(valueName + " has to pe positive.");
    }
}

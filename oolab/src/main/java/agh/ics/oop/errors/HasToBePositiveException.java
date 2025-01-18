package agh.ics.oop.errors;

public class HasToBePositiveException extends Exception {
    public HasToBePositiveException(String valueName) {
        super(valueName + " has to pe positive.");
    }
}

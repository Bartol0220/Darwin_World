package agh.ics.oop.model.errors;

public class HasToBeBit extends Exception {
    public HasToBeBit(String valueName) {
        super(valueName + " has to be 0 or 1.");
    }
}

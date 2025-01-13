package agh.ics.oop.model.errors;

public class MutationChangesCanNotExceedSize extends RuntimeException {
    public MutationChangesCanNotExceedSize() {
        super("Number of mutations changes cannot exceed number of genes.");
    }
}

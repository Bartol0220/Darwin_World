package agh.ics.oop.model.errors;

public class MinMaxGeneException extends RuntimeException {
    public MinMaxGeneException() {
        super("Minimum number of mutations can not exceed maximum number of mutations.");
    }
}

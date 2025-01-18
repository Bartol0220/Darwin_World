package agh.ics.oop.errors;

public class MinMaxGeneException extends RuntimeException {
    public MinMaxGeneException() {
        super("Minimum number of mutations can not exceed maximum number of mutations.");
    }
}

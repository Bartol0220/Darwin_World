package agh.ics.oop.model.errors;

public class BreedingCanNotKillAnimals extends Exception {
    public BreedingCanNotKillAnimals() {
        super("Energy used while breeding can not be higher than energy needed for breeding.");
    }
}

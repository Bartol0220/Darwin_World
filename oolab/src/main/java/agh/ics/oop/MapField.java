package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapField {
    private final Vector2d position;
    private final List<Animal> animals = new ArrayList<Animal>();
    private boolean hasGrass = false;
    private boolean isBetterPosition = false;
    private int lastDeathDate = 0;

    public MapField(Vector2d position) {
        this.position = position;
    }

    public void animalDiedOnField(int date) {
        lastDeathDate = date;
    }

    public void setHasGrass(boolean hasGrass) { this.hasGrass = hasGrass; }

    public void setIsBetterPosition(boolean betterPosition) { this.isBetterPosition = betterPosition; }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public List<Animal> getAniamls() { return  List.copyOf(animals);}

    public List<Animal> listOfBestAnimals(int minimalEnergy) {
        if (!animals.isEmpty()) {
            return animals.stream()
                    .filter(animal -> animal.getEnergy() >= minimalEnergy)
                    .sorted()
                    .limit(2)
                    .toList();
        }
        return Collections.emptyList();
    }
}

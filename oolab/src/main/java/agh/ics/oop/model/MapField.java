package agh.ics.oop.model;

import agh.ics.oop.model.grass.Grass;

import java.util.*;

public class MapField {
    private final Vector2d position;
    private final List<Animal> animals = new ArrayList<>();
    private Optional<Grass> grass = Optional.empty();
    private boolean isBetterPosition = false;
    private int lastDeathDate = 0;

    public MapField(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }

    public Optional<Grass> getGrass() {
        return grass;
    }

    public int getNumberOfAnimals() {
        return animals.size();
    }

    public int getLastDeathDate() {
        return lastDeathDate;
    }

    public boolean hasGrass() {
        return grass.isPresent();
    }

    public boolean isBetterPosition() {
        return isBetterPosition;
    }

    public void makePositionBetter() {
        this.isBetterPosition = true;
    }

    public void makePositionWorse() {
        this.isBetterPosition = false;
    }

    public void animalDiedOnField(int date) {
        lastDeathDate = date;
    }

    public void addGrass(Grass grass) {
        this.grass = Optional.of(grass);
    }

    public void removeGrass() {
        this.grass = Optional.empty();
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

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

    public Optional<WorldElement> objectAt() {
        if (!animals.isEmpty()) {
            return Optional.of(animals.getFirst());
        }
        return Optional.ofNullable(grass.orElse(null));
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapField that = (MapField) o;
        return this.position == that.position;
    }

    @Override
    public String toString() {
        return position.toString();
    }
}

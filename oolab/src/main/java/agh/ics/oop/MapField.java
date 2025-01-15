package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldElement;
import agh.ics.oop.model.grass.Grass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MapField {
    private final Vector2d position;
    private final List<Animal> animals = new ArrayList<Animal>();
    private Optional<Grass> grass = Optional.empty();
    private boolean isBetterPosition = false;
    private int lastDeathDate = 0;

    public MapField(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() { return position;}

    public boolean hasGrass() { return grass.isPresent();}

    public Optional<Grass> getGrass() {
        return grass;
    }

    public boolean isBetterPosition() { return isBetterPosition;}

    public void makePositionBetter() { this.isBetterPosition = true;}

    public void makePositionWorse() { this.isBetterPosition = false;}

    public void animalDiedOnField(int date) {
        lastDeathDate = date;
    }

    public void addGrass(Grass grass){
        this.grass = Optional.of(grass);
    }

    public Optional<Grass> removeGrass(){
        Optional<Grass> grass = this.grass;
        this.grass = Optional.empty();
        return grass;
    }

    public void setIsBetterPosition(boolean betterPosition) { this.isBetterPosition = betterPosition; }

    public int getNumberOfAnimals(){
        return animals.size();
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public List<Animal> getAnimals() { return  List.copyOf(animals);}

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

    public Optional<WorldElement> objectAt(){
        if (!animals.isEmpty()){
            return Optional.of(animals.getFirst());
        }
        return Optional.ofNullable(grass.orElse(null));
    }

}

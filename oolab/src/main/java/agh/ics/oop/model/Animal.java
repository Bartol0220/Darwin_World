package agh.ics.oop.model;

import agh.ics.oop.model.genes.Genes;

import java.util.Random;

public class Animal implements WorldElement, Comparable<Animal> {
    private MapDirection orientation = MapDirection.randomOrientation();
    private Vector2d position;
    private final int energyProvidedByEatingGrass;
    private final AnimalStats animalStats;
    private final Random random = new Random();


    public Animal(Vector2d position, Genes genes, int energy, int energyProvidedByEatingGrass, Animal parent1, Animal parent2){
        this.position = position;
        this.energyProvidedByEatingGrass = energyProvidedByEatingGrass;
        this.animalStats = new AnimalStats(genes, energy, parent1, parent2);
    }

    public AnimalStats getAnimalStats(){
        return animalStats;
    }

    public int getEnergy(){
        return animalStats.getEnergy();
    }


    public int[] getGenes() { return animalStats.getGenotype();}

    public int getCurrentGene() { return animalStats.getGenes().getCurrentGene();}

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public String toString() {
        return orientation.toString();
    }
  
    public String getName() { return "wolf_" + orientation + ".png"; }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public void move(MoveValidator validator) {
        orientation = orientation.nextOrientation(animalStats.getGenes().useCurrentGene());
        getAnimalStats().increaseAge();
        Vector2d newPosition = validator.handleBoundsPositions(position.add(orientation.toUnitVector()));
        if (validator.canMoveTo(newPosition)) {
            position = newPosition;
        } else {
            // obroc sie w przeciwna strone
            orientation = orientation.nextOrientation(4);
        }
        animalStats.decreaseEnergy(1);
//        decreaseEnergy(1);
    }

    public Animal breed(Animal animal, int energyUsedWhileBreeding, int dayNumber, AnimalCreator animalCreator){
        animalStats.decreaseEnergy(energyUsedWhileBreeding);
//        decreaseEnergy(energyUsedWhileBreeding);
        animalStats.increaseChildrenCount();
        animal.animalStats.decreaseEnergy(energyUsedWhileBreeding);
//        animal.decreaseEnergy(energyUsedWhileBreeding);
        animal.animalStats.increaseChildrenCount();

        return animalCreator.createAnimal(dayNumber, this, animal);
    }

    public void eat() {
        animalStats.increseEnergy(energyProvidedByEatingGrass);
        animalStats.increaseEatenGrass();
//        increaseEnergy(energyProvidedByEatingGrass);
    }

    @Override
    public int compareTo(Animal animal) {
        AnimalStats thisStats = animalStats;
        AnimalStats otherStats = animal.animalStats;
        if (thisStats.getEnergy() != otherStats.getEnergy()) return otherStats.getEnergy() - thisStats.getEnergy();
        if (thisStats.getAge() != otherStats.getAge()) return otherStats.getAge() - thisStats.getAge();
        if (thisStats.getChildrenCount() != otherStats.getChildrenCount()) return thisStats.getChildrenCount() - otherStats.getChildrenCount();

        return random.nextInt(-1, 2);
    }
}

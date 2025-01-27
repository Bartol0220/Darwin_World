package agh.ics.oop.model;

import agh.ics.oop.model.genes.Genes;
import agh.ics.oop.statistics.AnimalStats;

import java.util.Objects;
import java.util.Random;

public class Animal implements WorldElement, Comparable<Animal> {
    private MapDirection orientation = MapDirection.randomOrientation();
    private Vector2d position;
    private final int energyProvidedByEatingGrass;
    private final AnimalStats animalStats;
    private final Random random = new Random(); // static?
    private final int id;

    public Animal(int id, Vector2d position, Genes genes, int energy, int energyProvidedByEatingGrass) {
        this(id, position, genes, energy, energyProvidedByEatingGrass, null, null);
    }

    public Animal(int id, Vector2d position, Genes genes, int energy, int energyProvidedByEatingGrass, Animal parent1, Animal parent2) {
        this.position = position;
        this.energyProvidedByEatingGrass = energyProvidedByEatingGrass;
        this.animalStats = new AnimalStats(genes, energy, parent1, parent2);
        this.id = id;
    }

    public AnimalStats getAnimalStats() {
        return animalStats;
    }

    public int getEnergy() {
        return animalStats.getEnergy();
    }

    public int[] getGenes() {
        return animalStats.getGenotypeArray();
    }

    public int getCurrentGene() {
        return animalStats.getGenes().getCurrentGene();
    }

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public String toString() {
        return orientation.toString();
    }

    public String getName() {
        return "wolf_" + orientation + ".png";
    }

    public void move(MoveValidator validator) {
        orientation = orientation.nextOrientation(animalStats.getGenes().useCurrentGene());
        getAnimalStats().increaseAge();
        Vector2d newPosition = validator.handleBoundsPositions(position.add(orientation.toUnitVector()));
        if (validator.canMoveTo(newPosition)) {
            position = newPosition;
        } else {
            orientation = orientation.nextOrientation(4);
        }
        animalStats.decreaseEnergy(1);
    }

    public Animal breed(Animal otherParent, int energyUsedWhileBreeding, AnimalCreator animalCreator) { // czy to nie powinno być statyczne?
        this.animalStats.decreaseEnergy(energyUsedWhileBreeding);
        this.animalStats.increaseChildrenCount();
        otherParent.animalStats.decreaseEnergy(energyUsedWhileBreeding);
        otherParent.animalStats.increaseChildrenCount();

        return animalCreator.createAnimal(this, otherParent);
    }

    public void eat() {
        animalStats.increaseEnergy(energyProvidedByEatingGrass);
        animalStats.increaseEatenGrass();
    }

    public boolean isAlive() {
        return this.animalStats.getDeathDate().isEmpty(); // zwierzę musi zapytać statystyk, żeby wiedzieć, czy żyje?
    }

    @Override
    public int compareTo(Animal otherAnimal) {
        AnimalStats thisStats = this.animalStats;
        AnimalStats otherStats = otherAnimal.animalStats;
        if (thisStats.getEnergy() != otherStats.getEnergy()) return otherStats.getEnergy() - thisStats.getEnergy();
        if (thisStats.getAge() != otherStats.getAge()) return otherStats.getAge() - thisStats.getAge();
        if (thisStats.getChildrenCount() != otherStats.getChildrenCount())
            return otherStats.getChildrenCount() - thisStats.getChildrenCount();

        return random.nextInt(-1, 2);
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Animal that))
            return false;
        return this.id == that.id;
    }
}

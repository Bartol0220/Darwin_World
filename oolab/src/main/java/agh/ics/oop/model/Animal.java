package agh.ics.oop.model;

import agh.ics.oop.model.genes.Genes;

import java.util.Random;

public class Animal implements WorldElement, Comparable<Animal> {
    private MapDirection orientation = MapDirection.randomOrientation();
    private Vector2d position;
    private int energy;
    private final int birthDay;
    private final int energyProvidedByEatingGrass;
    private int childrenCount = 0;
    private final Genes genes;
    private final Random random = new Random();


    public Animal(Vector2d position, Genes genes, int birthDay, int energy, int energyProvidedByEatingGrass){
        this.position = position;
        this.genes = genes;
        this.birthDay = birthDay;
        this.energy = energy;
        this.energyProvidedByEatingGrass = energyProvidedByEatingGrass;
    }

    public int getEnergy(){
        return energy;
    }

    public int[] getGenes() { return genes.getGenes();}

    public int getCurrentGene() { return genes.getCurrentGene();}

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public String toString() {
        return orientation.toString();
    }

    public int getChildrenCount() { return childrenCount;}

    public int getBirthDay() { return birthDay;}

    public String breedString(){
        return "energy: %d\n children: %d\n birthday: %d\n".formatted(energy, childrenCount, birthDay);
    }

    private void decreaseEnergy(int energy) { this.energy -= energy;}

    private void increaseEnergy(int energy) { this.energy += energy;}

    private void addChildren() { childrenCount++;}

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public void move(MoveValidator validator) {
        orientation = orientation.nextOrientation(genes.useCurrentGene());
        Vector2d newPosition = validator.handleBoundsPositions(position.add(orientation.toUnitVector()));
        if (validator.canMoveTo(newPosition)) {
            position = newPosition;
        } else {
            // obroc sie w przeciwna strone
            orientation = orientation.nextOrientation(4);
        }
        decreaseEnergy(1);
    }

    public Animal breed(Animal animal, int energyUsedWhileBreeding, int dayNumber, AnimalCreator animalCreator){
        decreaseEnergy(energyUsedWhileBreeding);
        addChildren();
        animal.decreaseEnergy(energyUsedWhileBreeding);
        animal.addChildren();

        return animalCreator.createAnimal(dayNumber, this, animal);
    }

    public void eat() {
        increaseEnergy(energyProvidedByEatingGrass);
    }

    @Override
    public int compareTo(Animal animal) {
        if (this.energy != animal.energy) return animal.energy - this.energy;
        if (this.birthDay != animal.birthDay) return this.birthDay - animal.birthDay;
        if (this.childrenCount != animal.childrenCount) return animal.childrenCount - this.childrenCount;

        return random.nextInt(-1, 2);
    }
}

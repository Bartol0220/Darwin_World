package agh.ics.oop.model;

import agh.ics.oop.model.genes.Genes;
import agh.ics.oop.model.genes.GenesFactory;

import java.util.Random;

public class Animal implements WorldElement, Comparable<Animal> {
    private MapDirection orientation = MapDirection.randomOrientation();
    private Vector2d position;
    private int energy;
    private final int birthDay;
    private int childrenCount = 0;
    private final Genes genes;
    private final GenesFactory genesFactory;
    private final Random random = new Random();


    public Animal(Vector2d position, int energy, Genes genes, int birthDay, GenesFactory genesFactory){
        this.position = position;
        this.energy = energy;
        this.genes = genes;
        this.birthDay = birthDay;
        this.genesFactory = genesFactory;
    }

    public int getEnergy(){
        return energy;
    }

    public int[] getGenes() { return genes.getGenes();}

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public String toString() {
        return orientation.toString();
    }

    public String breedString(){
        return "energy: %d\n children: %d\n birthday: %d\n".formatted(energy, childrenCount, birthDay);
    }

    private void decreaseEnergy(int energy) { this.energy -= energy;}

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

    public Animal breed(Animal animal, int energyUsedWhileBreeding, int dayNumber){
        decreaseEnergy(energyUsedWhileBreeding);
        addChildren();
        animal.decreaseEnergy(energyUsedWhileBreeding);
        animal.addChildren();
        Genes kidGenes = genesFactory.makeGenes(this, animal);
        //czy on dostaje energie "od obu rodzicow" (2*energy) czy po prostu energy?
        return new Animal(this.getPosition(), 2*energyUsedWhileBreeding, kidGenes, dayNumber, genesFactory);
    }

    @Override
    public int compareTo(Animal animal) {
        if (this.energy != animal.energy) return animal.energy - this.energy;
        if (this.birthDay != animal.birthDay) return this.birthDay - animal.birthDay;
        if (this.childrenCount != animal.childrenCount) return animal.childrenCount - this.childrenCount;

        return random.nextInt(-1, 2);
    }

}

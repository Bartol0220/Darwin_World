package agh.ics.oop.model;

import agh.ics.oop.model.genes.GenesFactory;

import java.util.Random;

public class Animal implements WorldElement, Comparable{
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;
    private int energy;
    private final int birthDay;
    private int childrenCount = 0;
    private final Genes genes;
    private Random random = new Random();
    private final GenesFactory genesFactory;


    public Animal(Vector2d position, int energy, Genes genes, int birthDay, GenesFactory genesFactory){
        this.position = position;
        this.energy = energy;
        this.genes = genes;
        this.birthDay = birthDay;
        this.genesFactory = genesFactory;
    }

    //w mniejszym uzywam wiekszego
    public Animal(int energy, Genes genes, int birthDay, GenesFactory genesFactory){
        //orientation defaultowo na NORTH
        this(new Vector2d(2, 2), energy, genes, birthDay, genesFactory);
    }

    public int getEnergy(){
        return energy;
    }

    public int[] getGenes(){
        return genes.getGenes();
    }

    private void decreaseEnergy(){
        energy -= 1;
    }

    public String toString() {
        return orientation.toString();
    }

    public String breedString(){
        return "energy: %d\n children: %d\n birthday: %d\n".formatted(energy, childrenCount, birthDay);
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getOrientation() {
        return orientation;
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
        decreaseEnergy();
    }

    public Animal breed(Animal animal, int energyToBeGiven, int dayNumber){
        this.energy -= energyToBeGiven;
        this.childrenCount++;
        animal.energy -= energyToBeGiven;
        animal.childrenCount++;
//        Genes kidGenes = new Genes(this, animal, geneMutator, genes.getNumberOfGenes());
        Genes kidGenes = genesFactory.makeGenes(this, animal);
        //czy on dostaje energie "od obu rodzicow" (2*energy) czy po prostu energy?
        return new Animal(this.getPosition(), 2*energyToBeGiven, kidGenes, dayNumber, genesFactory);
    }

    @Override
    public int compareTo(Object object) {
        Animal other = (Animal) object;
        if (this.energy != other.energy) return other.energy - this.energy;
        if (this.birthDay != other.birthDay) return this.birthDay - other.birthDay;
        if (this.childrenCount != other.childrenCount) return other.childrenCount - this.childrenCount;
        return 0;
    }

}

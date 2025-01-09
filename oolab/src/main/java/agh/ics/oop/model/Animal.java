package agh.ics.oop.model;

import java.util.Random;

public class Animal implements WorldElement, Comparable{
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;
    private int energy;
    private final int birthDay;
    private int childrenCount = 0;
    private final Genes genes;
    private Random random = new Random();


    public Animal(Vector2d position, int energy, Genes genes, int birthDay){
        this.position = position;
        this.energy = energy;
        this.genes = genes;
        this.birthDay = birthDay;
    }

    //w mniejszym uzywam wiekszego
    public Animal(int energy, Genes genes, int birthDay){
        //orientation defaultowo na NORTH
        this(new Vector2d(2, 2), energy, genes, birthDay);
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
        Vector2d newPosition = validator.specialMove(position.add(orientation.toUnitVector()));
        if (validator.canMoveTo(newPosition)) {
            position = newPosition;
        } else {
            // obroc sie w przeciwna strone
            orientation = orientation.nextOrientation(4);
        }
        decreaseEnergy();
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

package agh.ics.oop.model;

import java.util.Random;

public class Animal implements WorldElement{
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;
    private int energy;
    private final int[] genes;
    private int geneIndex;
    private Random random = new Random();

    public Animal(Vector2d position, int energy, int[] genes){
        this.position = position;
        this.energy = energy;
        this.genes = genes;
        this.geneIndex = random.nextInt(genes.length);
    }

    public int getEnergy(){
        return energy;
    }

    private void decreaseEnergy(){
        energy -= 1;
    }

    //w mniejszym uzywam wiekszego
    public Animal(int energy, int[] genes){
        //orientation defaultowo na NORTH
        this(new Vector2d(2, 2), energy, genes);
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
        orientation = orientation.nextOrientation(genes[geneIndex]);
        geneIndex = (geneIndex + 1) % genes.length;
        Vector2d newPosition = validator.handleBoundsPositions(position.add(orientation.toUnitVector()));
        if (validator.canMoveTo(newPosition)) {
            position = newPosition;
        } else {
            // obroc sie w przeciwna strone
            orientation = orientation.nextOrientation(4);
        }
        decreaseEnergy();
    }
}

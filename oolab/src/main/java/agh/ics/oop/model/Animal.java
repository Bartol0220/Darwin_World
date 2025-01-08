package agh.ics.oop.model;

import javax.crypto.spec.PSource;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Animal implements WorldElement{
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;
    private int energy;
    private final Genes genes;


    public Animal(Vector2d position, int energy, Genes genes){
        this.position = position;
        this.energy = energy;
        this.genes = genes;
    }

    //w mniejszym uzywam wiekszego
    public Animal(int energy, Genes genes){
        //orientation defaultowo na NORTH
        this(new Vector2d(2, 2), energy, genes);
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
}

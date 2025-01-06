package agh.ics.oop.model;

import agh.ics.oop.model.util.RandomVector2d;

import java.util.ArrayList;
import java.util.Random;

public class Animal implements WorldElement{
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;
    private int energy;
    private final int[] gens;
    private int geneIndex;
    private Random random = new Random();

    public Animal(Vector2d position, int energy, int[] gens){
        this.position = position;
        this.energy = energy;
        this.gens = gens;
        this.geneIndex = random.nextInt(gens.length);
    }

    //w mniejszym uzywam wiekszego
    public Animal(int energy, int[] gens){
        //orientation defaultowo na NORTH
        this(new Vector2d(2, 2), energy, gens);
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
        orientation = orientation.nextOrientation(gens[geneIndex]);
        geneIndex = (geneIndex + 1) % gens.length;
        Vector2d newPosition = validator.specialMove(position.add(orientation.toUnitVector()));
        if (validator.canMoveTo(newPosition)) {
            position = newPosition;
        } else {
            // obroc sie w przeciwna strone
            orientation = orientation.nextOrientation(4);
        }
    }
}

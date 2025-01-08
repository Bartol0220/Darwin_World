package agh.ics.oop.model;

import agh.ics.oop.model.grass.AbstractGrassMaker;
import agh.ics.oop.model.util.Boundary;

import java.util.ArrayList;

public class Globe extends AbstractWorldMap{
    private final Vector2d upperRight;
    private final Vector2d lowerLeft;
    private final int width;
    private final Boundary bounds;;

    public Globe(int width, int height, int id, AbstractGrassMaker grassMaker) {
        super(id, grassMaker);
        upperRight = new Vector2d(width - 1, height - 1);
        lowerLeft = new Vector2d(0, 0);
        this.width = width;
        bounds = new Boundary(lowerLeft, upperRight);
    }

    @Override
    public Boundary getCurrentBounds() {
        return bounds;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.higher(lowerLeft) && position.lower(upperRight);
    }

    @Override
    public Vector2d specialMove(Vector2d position){
        return new Vector2d((position.getX()+width)%width, position.getY());
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return super.isOccupied(position) || grassMaker.grassMap.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        WorldElement animalPosition = super.objectAt(position);
        if (animalPosition != null) {
            return animalPosition;
        }
        return grassMaker.grassMap.get(position);
    }

    @Override
    public ArrayList<WorldElement> getElements() {
        ArrayList<WorldElement> elements = super.getElements();
        elements.addAll(grassMaker.grassMap.values());
        return elements;
    }
}

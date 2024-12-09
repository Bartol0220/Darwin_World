package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;

public class RectangularMap extends AbstractWorldMap{
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private final Boundary bounds;

    public RectangularMap(int width, int height, int id) {
        super(id);
        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width - 1, height - 1);
        bounds = new Boundary(lowerLeft, upperRight);
    }

    @Override
    public Boundary getCurrentBounds(){
        return bounds;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return super.canMoveTo(position) && lowerLeft.precedes(position) && upperRight.follows(position);
    }
}

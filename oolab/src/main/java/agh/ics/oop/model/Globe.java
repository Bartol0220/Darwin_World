package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;

public class Globe extends AbstractWorldMap{
    private final Vector2d upperRight;
    private final Vector2d lowerLeft;
    private final int width;
    private final Boundary bounds;

    public Globe(int width, int height, int id) {
        super(id);
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
        //dziala, poniewaz mapa zaczyna sie od (0,0)
        if (canMoveTo(position)){
            return new Vector2d(position.getX()%width, position.getY());
        }
        return position;
    }
}

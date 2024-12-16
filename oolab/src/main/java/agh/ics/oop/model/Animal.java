package agh.ics.oop.model;

public class Animal implements WorldElement{
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;

    public Animal(Vector2d position){
        this.position = position;
    }

    //w mniejszym uzywam wiekszego
    public Animal(){
        //facing defaultowo na NORTH
        this(new Vector2d(2, 2));
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

    public void move(MoveDirection direction, MoveValidator validator) {
        switch (direction){
            case FORWARD -> {
                Vector2d newPosition = position.add(orientation.toUnitVector());
                if (validator.canMoveTo(newPosition)) {
                    position = newPosition;
                }
            }
            case BACKWARD -> {
                Vector2d newPosition = position.add(orientation.toUnitVector().opposite());
                if (validator.canMoveTo(newPosition)) {
                    position = newPosition;
                }
            }
            case LEFT -> orientation = orientation.previous();
            case RIGHT -> orientation = orientation.next();
        }
    }
}

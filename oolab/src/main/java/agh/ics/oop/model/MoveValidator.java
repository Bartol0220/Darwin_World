package agh.ics.oop.model;

import agh.ics.oop.model.Vector2d;

public interface MoveValidator {

    /**
     * Indicate if any object can move to the given position.
     *
     * @param position
     *            The position checked for the movement possibility.
     * @return True if the object can move to that position.
     */
    boolean canMoveTo(Vector2d position);

    /**
     * Indicate if animal must be moved differently than given position.
     *
     * @param position
     *            The position that animal was supposed to go.
     * @return special position or given position
     */
    Vector2d specialMove(Vector2d position);
}

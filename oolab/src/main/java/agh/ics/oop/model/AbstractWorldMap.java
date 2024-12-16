package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements WorldMap {
    protected final Map<Vector2d, Animal> animalsMap = new HashMap<>();
    private final MapVisualizer mapVisualizer = new MapVisualizer(this);
    private final List<MapChangeListener> observers = new ArrayList<>();
    private final int id;

    protected AbstractWorldMap(int id) {
        this.id = id;
    }

    public void registerObserver(final MapChangeListener observer) {
        observers.add(observer);
    }

    public void unregisterObserver(final MapChangeListener observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String message){
        for(MapChangeListener observer : observers){
            observer.mapChanged(this, message);
        }
    }

    @Override
    public void place(Animal animal) throws IncorrectPositionException{
        if(canMoveTo(animal.getPosition())) {
            animalsMap.put(animal.getPosition(), animal);
            notifyObservers("Animal placed at %s.".formatted(animal.getPosition()));
        }
        else {
            throw new IncorrectPositionException(animal.getPosition());
        }
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        Vector2d previousPosition = animal.getPosition();
        MapDirection previousOrientation = animal.getOrientation();

        animalsMap.remove(animal.getPosition());
        animal.move(direction, this);
        animalsMap.put(animal.getPosition(), animal);

        if(!animal.getPosition().equals(previousPosition)) {
            notifyObservers("Animal moved from %s to %s.".formatted(previousPosition, animal.getPosition()));
        }
        else if(!animal.getOrientation().equals(previousOrientation)) {
            notifyObservers("Animal at %s turned to the %s.".formatted(previousPosition, animal.getOrientation()));
        }

    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animalsMap.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return animalsMap.get(position);
    }
    
    public boolean isOccupiedByAnimal(Vector2d position) {
        return (objectAt(position) instanceof Animal);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isOccupiedByAnimal(position);
    }

    @Override
    public ArrayList<WorldElement> getElements() {
        return new ArrayList<>(animalsMap.values());
    }

    @Override
    public String toString() {
        Boundary boundary = getCurrentBounds();
        return mapVisualizer.draw(boundary.lowerLeft(), boundary.upperRight());
    }

    @Override
    public abstract Boundary getCurrentBounds();

    @Override
    public int getId() {
        return id;
    }
}

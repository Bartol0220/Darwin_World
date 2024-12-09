package agh.ics.oop.model;

import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements WorldMap {
    protected final Map<Vector2d, Animal> animalsMap = new HashMap<>();
    protected final MapVisualizer mapVisualizer = new MapVisualizer(this);
    protected final List<MapChangeListener> observers = new ArrayList<>();
    protected final int id;

    protected AbstractWorldMap(int id) {
        this.id = id;
    }

    public void registerObserver(final MapChangeListener observer) {
        observers.add(observer);
    }

    public void unregisterObserver(final MapChangeListener observer) {
        observers.remove(observer);
    }

    private void mapChanged(String message){
        for(MapChangeListener observer : observers){
            observer.mapChanged(this, message);
        }
    }

    @Override
    public void place(Animal animal) throws IncorrectPositionException{
        if(canMoveTo(animal.getPosition())) {
            animalsMap.put(animal.getPosition(), animal);
            mapChanged("Animal placed at %s.".formatted(animal.getPosition()));
        }
        else {
            throw new IncorrectPositionException(animal.getPosition());
        }
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        animalsMap.remove(animal.getPosition());

        Vector2d previousPosition = animal.getPosition();
        MapDirection previousOrientation = animal.getOrientation();

        animal.move(direction, this);

        animalsMap.put(animal.getPosition(), animal);

        if(!animal.getPosition().equals(previousPosition)) {
            mapChanged("Animal moved from %s to %s.".formatted(previousPosition, animal.getPosition()));
        }
        else if(!animal.getOrientation().equals(previousOrientation)) {
            mapChanged("Animal at %s turned to the %s.".formatted(previousPosition, animal.getOrientation()));
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

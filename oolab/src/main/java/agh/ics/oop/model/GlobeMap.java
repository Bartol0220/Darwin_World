package agh.ics.oop.model;

import agh.ics.oop.model.grass.Grass;
import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.sort;

public class GlobeMap implements MoveValidator{
    private final int id;
    private final int width;
    private final int height;
    private final Boundary bounds;
    // zamiast Arraylist<Animal> mozna dac Set - pomyslec
    protected final Map<Vector2d, ArrayList<Animal>> animalsMap = new HashMap<>();
    protected final HashSet<Vector2d> whereAnimalsMeet = new HashSet<>();
    protected final HashSet<Vector2d> animalsOnGrass = new HashSet<>();
    public final Map<Vector2d, WorldElement> grassMap = new HashMap<>();
    private final MapVisualizer mapVisualizer = new MapVisualizer(this);
    private final List<MapChangeListener> observers = new ArrayList<>();

    public GlobeMap(int width, int height, int id) {
        this.id = id;
        this.width = width;
        this.height = height;
        bounds = new Boundary(new Vector2d(0, 0), new Vector2d(width - 1, height - 1));
    }

    public int getId() { return id;}

    public int getHeight() { return height;}

    public int getWidth() { return width;}

    public Boundary getCurrentBounds() { return bounds;}

    public List<Vector2d> getPositionsOfAnimalsOnGrass() { return animalsOnGrass.stream().toList();}

    public HashSet<Vector2d> getWhereAnimalsMeet() { return whereAnimalsMeet;}

    public void clearMapOfAnimalsOnGrass() { animalsOnGrass.clear();}

    public void registerObserver(final MapChangeListener observer) { observers.add(observer);}

    public void unregisterObserver(final MapChangeListener observer) { observers.remove(observer);}

    private void notifyObservers(String message){
        for(MapChangeListener observer : observers){
            observer.mapChanged(this, message);
        }
    }

    public void addGrass(Vector2d position) {
        grassMap.put(position, new Grass(position));
    }

    public void place(Animal animal) throws IncorrectPositionException{
        if(canMoveTo(animal.getPosition())) {
            addAnimalToMap(animal);
            notifyObservers("Animal placed at %s.".formatted(animal.getPosition()));
        }
        else {
            throw new IncorrectPositionException(animal.getPosition());
        }
    }

    public boolean isOccupiedByAnimal(Vector2d position) {
        return (objectAt(position) instanceof Animal);
    }

    public boolean isOccupied(Vector2d position) {
        return animalsMap.containsKey(position) || grassMap.containsKey(position);
    }

    public WorldElement objectAt(Vector2d position) {
        if (animalsMap.get(position) != null) {
            return animalsMap.get(position).getFirst();
        }
        return grassMap.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.higher(bounds.lowerLeft()) && position.lower(bounds.upperRight());
    }

    private void updateWhereAnimalsMeet(Vector2d position){
        if (isOccupiedByAnimal(position)){
            whereAnimalsMeet.add(position);
        }

    }

    public void move(Animal animal) {
        Vector2d previousPosition = animal.getPosition();

        removeAnimalFromMap(animal);
        animal.move(this);
        addAnimalToMap(animal);

        addAnimalOnGrass(animal);
        updateWhereAnimalsMeet(animal.getPosition());

        notifyObservers("Animal or: %s, pos: %s -> %s.".formatted(animal.getOrientation(), previousPosition, animal.getPosition()));
    }

    public void breedAnimals(int requiredEnergy, int energyToGive, int dayNumber){
        for (Vector2d position : whereAnimalsMeet){
            List<Animal> animals = animalsMap.get(position);
            if (animals != null) {
                List<Animal> breedingPair = animals.stream()
                        .filter(animal -> animal.getEnergy() >= requiredEnergy)
                        .sorted()
                        .limit(2)
                        .toList();
                if (breedingPair.size() == 2){
                    Animal kid = breedingPair.getFirst().breed(breedingPair.get(1), energyToGive, dayNumber);
                    addAnimalToMap(kid);
                }
            }
        }
        whereAnimalsMeet.clear();
    }

    private void addAnimalOnGrass(Animal animal) {
        if (grassMap.containsKey(animal.getPosition())) {
            animalsOnGrass.add(animal.getPosition());
        }
    }

    private void removeAnimalFromMap(Animal animal) {
        animalsMap.get(animal.getPosition()).remove(animal);
        if (animalsMap.get(animal.getPosition()).isEmpty()) {
            animalsMap.remove(animal.getPosition());
        }
    }

    private void addAnimalToMap(Animal animal) {
        if (animalsMap.get(animal.getPosition()) == null) {
            ArrayList<Animal> animalList = new ArrayList<>();
            animalList.add(animal);
            animalsMap.put(animal.getPosition(), animalList);
        } else {
            animalsMap.get(animal.getPosition()).add(animal);
        }
    }

    @Override
    public Vector2d handleBoundsPositions(Vector2d position){
        return new Vector2d((position.getX()+width)%width, position.getY());
    }

    public ArrayList<WorldElement> getElements() {
        // TODO powinno zwracac liste wszystkich zwierzakow
        ArrayList<WorldElement> elements = new ArrayList<>();
        elements.addAll(grassMap.values());
        return elements;
    }

    public String toString() {
        Boundary boundary = getCurrentBounds();
        return mapVisualizer.draw(boundary.lowerLeft(), boundary.upperRight());
    }
}

package agh.ics.oop.model;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.errors.IncorrectPositionException;
import agh.ics.oop.model.grass.Grass;
import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;
import java.util.stream.Stream;

public class GlobeMap implements MoveValidator{
    private final int id;
    private final int width;
    private final int height;
    private final Boundary bounds;
    private final Map<Vector2d, ArrayList<Animal>> animalsMap = new HashMap<>();
    private final HashSet<Vector2d> whereAnimalsMeet = new HashSet<>();
    private final HashSet<Vector2d> animalsOnGrass = new HashSet<>();
    private final Map<Vector2d, Grass> grassMap = new HashMap<>();
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

    public int getFreeSpace() {
        return height*width - (int) Stream
                .concat(
                        animalsMap.keySet().stream(),
                        grassMap.keySet().stream()
                )
                .distinct()
                .count();
    }

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

    public void removeGrass(Grass grass) { grassMap.remove(grass.getPosition()); }

    public void place(Animal animal) throws IncorrectPositionException {
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
        return position.follows(bounds.lowerLeft()) && position.precedes(bounds.upperRight());
    }

    private void updateWhereAnimalsMeet(Vector2d position){
        if (isOccupiedByAnimal(position)){
            whereAnimalsMeet.add(position);
        }
    }

    public void move(Animal animal) {
        MapDirection previousOrientation = animal.getOrientation();
        Vector2d previousPosition = animal.getPosition();
        int gene = animal.getCurrentGene();

        removeAnimalFromMap(animal);
        animal.move(this);
        addAnimalToMap(animal);

        addAnimalOnGrass(animal);
        updateWhereAnimalsMeet(animal.getPosition());

        notifyObservers("Animal gn: %d, or: %s -> %s, pos: %s -> %s."
                .formatted(gene, previousOrientation, animal.getOrientation(), previousPosition, animal.getPosition())
        );
    }

    private List<Animal> listOfBestAnimalsAtPosition(Vector2d position, int minimalEnergy) {
        List<Animal> animals = animalsMap.get(position);
        if (animals != null) {
            return animals.stream()
                    .filter(animal -> animal.getEnergy() >= minimalEnergy)
                    .sorted()
                    .limit(2)
                    .toList();
        }
        return Collections.emptyList();
    }

    public void findAnimalsToBreed(Breeding breeding, Simulation simulation){
        for (Vector2d position : whereAnimalsMeet){
            List<Animal> breedingPair = listOfBestAnimalsAtPosition(position, breeding.getEnergyNeededForBreeding());
            Optional<Animal> kid = breeding.breedPair(breedingPair);
            kid.ifPresent(presentKid -> {
                this.addAnimalToMap(presentKid);
                presentKid.getAnimalStats().increaseSuccesorCount();
                simulation.addToAnimals(presentKid);
                });
        }
        whereAnimalsMeet.clear();
    }

    public List<Optional<Grass>> feedAnimals() {
        ArrayList<Optional<Grass>> grassEaten = new ArrayList<>();
        for (Vector2d position : animalsOnGrass.stream().toList()) {
            Optional<Grass> grass = findAnimalToFeed(position);
            grassEaten.add(grass);
        }
        animalsOnGrass.clear();
        return grassEaten;
    }

    public Optional<Grass> findAnimalToFeed(Vector2d position){
        List<Animal> bestAnimalsAtPosition = listOfBestAnimalsAtPosition(position, 0);
        if (!bestAnimalsAtPosition.isEmpty()){
            Animal animal = bestAnimalsAtPosition.getFirst();
            animal.eat();
            return Optional.of(grassMap.get(position));
        }
        return Optional.empty();
    }

    private void addAnimalOnGrass(Animal animal) {
        if (grassMap.containsKey(animal.getPosition())) {
            animalsOnGrass.add(animal.getPosition());
        }
    }

    public void removeAnimalFromMap(Animal animal) {
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

    public String toString() {
        Boundary boundary = getCurrentBounds();
        return mapVisualizer.draw(boundary.lowerLeft(), boundary.upperRight());
    }
}

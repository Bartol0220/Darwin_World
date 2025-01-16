package agh.ics.oop.model;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.errors.IncorrectPositionException;
import agh.ics.oop.model.grass.Grass;
import agh.ics.oop.model.observers.MapChangeObserver;
import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;
import java.util.stream.Stream;

public class GlobeMap implements MoveValidator {
    private final int id;
    private final int width;
    private final int height;
    private final Boundary bounds;
    private final Map<Vector2d, MapField> allFields = new HashMap<>();
    private final HashSet<MapField> animalsMap = new HashSet<>();
    private final HashSet<Vector2d> whereAnimalsMeet = new HashSet<>();
    private final HashSet<Vector2d> animalsOnGrass = new HashSet<>();
    private final HashSet<MapField> grassMap = new HashSet<>();
    private final MapVisualizer mapVisualizer = new MapVisualizer(this);
    private final List<MapChangeObserver> observers = new ArrayList<>();

    public GlobeMap(int width, int height, int id) {
        this.id = id;
        this.width = width;
        this.height = height;
        bounds = new Boundary(new Vector2d(0, 0), new Vector2d(width - 1, height - 1));
        for (int x = 0; x < width; x++){
            for (int y = 0; y < width; y++){
                Vector2d position = new Vector2d(x, y);
                allFields.put(position, new MapField(position));
            }
        }
    }

    public int getId() { return id;}

    public int getHeight() { return height;}

    public int getWidth() { return width;}

    public Boundary getCurrentBounds() { return bounds;}

    public int getFreeSpace() {
        return height*width - (int) Stream
                .concat(
                        animalsMap.stream().map(MapField::getPosition),
                        grassMap.stream().map(MapField::getPosition)
                )
                .distinct()
                .count();
    }

    public boolean areMultipleAnimalsOnField(Vector2d position) { return allFields.get(position).getNumberOfAnimals() > 1;}

    public boolean isFieldBetter(Vector2d position) { return allFields.get(position).isBetterPosition(); }

    public void changeFieldToBetter(Vector2d position) { allFields.get(position).makePositionBetter();}

    public void changeFieldToWorse(Vector2d position) { allFields.get(position).makePositionWorse();}

    public void registerObserver(MapChangeObserver observer) { observers.add(observer);}

    public void unregisterObserver(MapChangeObserver observer) { observers.remove(observer);}

    public void notifyObservers(String message) {
        for(MapChangeObserver observer : observers){
            observer.mapChanged(this, message);
        }
    }

    public void addGrass(Vector2d position) {
        MapField mapField = allFields.get(position);
        mapField.addGrass(new Grass(position));
        grassMap.add(mapField);
    }

    public void removeGrass(Grass grass) {
        MapField mapField = allFields.get(grass.getPosition());
        mapField.removeGrass();
        grassMap.remove(mapField);
    }

    public void place(Animal animal) throws IncorrectPositionException {
        if(canMoveTo(animal.getPosition())) {
            addAnimalToMap(animal);
//            notifyAnimalDiedObservers("Animal placed at %s.".formatted(animal.getPosition()));
        }
        else {
            throw new IncorrectPositionException(animal.getPosition());
        }
    }

    public boolean isOccupiedByAnimal(Vector2d position) {
        return objectAt(position)
                .map(object -> object instanceof Animal)
                .orElse(false);
    }

    public Optional<WorldElement> objectAt(Vector2d position) {
        MapField mapField = allFields.get(position);
        return  mapField.objectAt();
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

//        notifyAnimalDiedObservers("Animal gn: %d, or: %s -> %s, pos: %s -> %s."
//                .formatted(gene, previousOrientation, animal.getOrientation(), previousPosition, animal.getPosition())
//        );
    }


    public void findAnimalsToBreed(Breeding breeding, Simulation simulation){
        for (Vector2d position : whereAnimalsMeet){
            MapField mapField = allFields.get(position);
            List<Animal> breedingPair = mapField.listOfBestAnimals(breeding.getEnergyNeededForBreeding());
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
        MapField mapField = allFields.get(position);
        List<Animal> bestAnimalsAtPosition = mapField.listOfBestAnimals(0);
        if (!bestAnimalsAtPosition.isEmpty()){
            Animal animal = bestAnimalsAtPosition.getFirst();
            animal.eat();
            return allFields.get(position).getGrass();
        }
        return Optional.empty();
    }

    private void addAnimalOnGrass(Animal animal) {
        if (allFields.get(animal.getPosition()).hasGrass()) {
            animalsOnGrass.add(animal.getPosition());
        }
    }

    public void removeAnimalFromMap(Animal animal) {
        MapField mapField = allFields.get(animal.getPosition());
        mapField.removeAnimal(animal);
        if (mapField.getNumberOfAnimals() < 1){
            animalsMap.remove(mapField);
        }
    }

    private void addAnimalToMap(Animal animal) {
        MapField mapField = allFields.get(animal.getPosition());
        mapField.addAnimal(animal);
        animalsMap.add(mapField);
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

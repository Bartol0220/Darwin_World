package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.errors.IncorrectPositionException;
import agh.ics.oop.model.grass.AbstractGrassMaker;
import agh.ics.oop.model.grass.Grass;
import agh.ics.oop.model.util.RandomVector2d;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


public class Simulation implements Runnable {
    private final GlobeMap map;
    private final List<Animal> animals = new LinkedList<>();
    private final AbstractGrassMaker grassMaker;
    private final AnimalCreator animalCreator;
    private final Breeding breeding;
    private final Stats stats;
    private int dayNumber = 0;
    private boolean running = true;
    private final Random random = new Random();

    public Simulation(GlobeMap map, AbstractGrassMaker grassMaker, Breeding breeding, AnimalCreator animalCreator, int startNumberOfAnimals, Stats stats) {
        this.map = map;
        this.grassMaker = grassMaker;
        this.animalCreator = animalCreator;
        this.breeding = breeding;
        this.stats = stats;

        createAnimals(map, startNumberOfAnimals);
    }

    private void createAnimals(GlobeMap map, int startNumberOfAnimals) {
        RandomVector2d positions = new RandomVector2d(map.getWidth(), map.getHeight(), startNumberOfAnimals);
        for(Vector2d position : positions) {
            Animal animal = animalCreator.createStartingAnimal(position, dayNumber);
            try {
                map.place(animal);
                animals.add(animal);
            } catch (IncorrectPositionException e) {
                System.err.println("Animal cannot be placed. " + e.getMessage());
                stats.animalNotPlaced(animal);
            }
        }
    }

    public void pause() {
        running = false;
    }

    public void play() {
        running = true;
    }

    public void run() {
        stats.updateGeneralStats(animals);

        try {
            while (!animals.isEmpty() && running) {
                Thread.sleep(700);
                dayNumber++;
                runDay();
                map.notifyObservers("Day " + dayNumber);
                stats.updateGeneralStats(animals);
            }
        } catch (InterruptedException e) {
            // TODO
        }
    }

    private void runDay() {
        removeDeadAnimals();
        moveAnimals();
        feedAnimals();
        breeding.breedAnimals(dayNumber, this);
        grassMaker.grow();
        stats.calculateAverageBirthRate(animals);
    }

    private void removeDeadAnimals() {
        animals.removeIf(animal -> {
            if (animal.getEnergy() < 1){
                map.removeAnimalFromMap(animal);
                grassMaker.deadAnimal(animal);
                animal.getAnimalStats().setDeathDate(dayNumber);
                stats.animalDied(animal);
                stats.calculateNewAverageLifeSpan(animal.getAnimalStats().getAge());
                stats.calculateAverageBirthRate(animals);
                return true;
            }
            return false;
        });
    }

    private void moveAnimals() {
        for (Animal animal : animals) {
//            try {
//                Thread.sleep(700);
//            } catch (InterruptedException e) {
//                // TODO zamienić z ignore
//                // TODO CO TO ZA CATCH W PETLI??!
//            }
            map.move(animal);
        }
    }

    private void feedAnimals() {
        List<Optional<Grass>> grassList = map.feedAnimals();
        for (Optional<Grass> grass : grassList) {
            grass.ifPresent(presentGrass -> {
                grassMaker.grassEaten(presentGrass);
                stats.updateUponEating();
            });
        }
    }

    public void addToAnimals(Animal animal){ animals.add(animal); }

    public Stats getStats() { return stats; }
}

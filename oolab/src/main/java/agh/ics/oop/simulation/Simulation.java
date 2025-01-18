package agh.ics.oop.simulation;

import agh.ics.oop.model.*;
import agh.ics.oop.errors.IncorrectPositionException;
import agh.ics.oop.model.grass.AbstractGrassMaker;
import agh.ics.oop.model.grass.Grass;
import agh.ics.oop.model.observers.AnimalDiedObserver;
import agh.ics.oop.model.observers.NewDayObserver;
import agh.ics.oop.model.observers.SimulationErrorObserver;
import agh.ics.oop.stats.Stats;
import agh.ics.oop.model.util.RandomVector2d;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.*;


public class Simulation implements Runnable {
    private final List<AnimalDiedObserver> animalDiedObservers = new ArrayList<>();
    private final List<NewDayObserver> newDayObservers = new ArrayList<>();
    private final List<SimulationErrorObserver> simulationErrorObservers = new ArrayList<>();
    private final List<Animal> animals = new LinkedList<>();
    private final GlobeMap map;
    private final AbstractGrassMaker grassMaker;
    private final AnimalCreator animalCreator;
    private final Breeding breeding;
    private final Stats stats;
    private int dayNumber = 0;
    private int threadSleepTime = 700;
    private boolean running = true;

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
            Animal animal = animalCreator.createStartingAnimal(position);
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

    public void setThreadSleep(int time) {
        threadSleepTime = time;
    }

    public void run() {
        stats.updateGeneralStats(animals);

        try {
            while (!animals.isEmpty() && running) {
                Thread.sleep(threadSleepTime);
                dayNumber++;
                runDay();
                stats.calculateAverageBirthRate(animals);
                stats.updateGeneralStats(getAnimals());
                map.notifyObservers("Day " + dayNumber);
                notifyNewDayObservers(dayNumber);
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            pause();
            notifySimulationErrorObserver();
        }
    }

    private void runDay() {
        removeDeadAnimals();
        moveAnimals();
        feedAnimals();
        breeding.breedAnimals(this);
        grassMaker.grow();
    }

    public void registerAnimalDiedObserver(AnimalDiedObserver observer) { animalDiedObservers.add(observer);}

    public void unregisterAnimalDiedObserver(AnimalDiedObserver observer) { animalDiedObservers.remove(observer);}

    public void notifyAnimalDiedObservers(Animal animal){
        for(AnimalDiedObserver observer : animalDiedObservers){
            observer.animalDied(animal);
        }
    }

    public void registerNewDayObserver(NewDayObserver observer) { newDayObservers.add(observer);}

    public void unregisterNewDayObserver(NewDayObserver observer) { newDayObservers.remove(observer);}

    public void notifyNewDayObservers(int dayNumber) {
        for(NewDayObserver observer : newDayObservers){
            observer.newDay(dayNumber);
        }
    }

    public void registerSimulationErrorObserver(SimulationErrorObserver observer) { simulationErrorObservers.add(observer);}

    public void unregisterSimulationErrorObserver(SimulationErrorObserver observer) { simulationErrorObservers.remove(observer);}

    public void notifySimulationErrorObserver() {
        for(SimulationErrorObserver observer : simulationErrorObservers){
            observer.simulationErrorOccured();
        }
    }

    private void removeDeadAnimals() {
        animals.removeIf(animal -> {
            if (animal.getEnergy() < 1){
                map.removeAnimalFromMap(animal);
                notifyAnimalDiedObservers(animal);
                animal.getAnimalStats().setDeathDate(dayNumber);
                stats.calculateNewAverageLifeSpan(animal.getAnimalStats().getAge());
                stats.calculateAverageBirthRate(animals);
                return true;
            }
            return false;
        });
    }

    private void moveAnimals() {
        for (Animal animal : animals) {
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

    private List<Animal> getAnimals() { return List.copyOf(animals); }
}

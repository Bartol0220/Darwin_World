package agh.ics.oop.model;

import agh.ics.oop.simulation.Simulation;

import java.util.List;
import java.util.Optional;

public class Breeding {
    private final int energyNeededForBreeding;
    private final int energyUsedWhileBreeding;
    private final GlobeMap map;
    private final AnimalCreator animalCreator;
    private int dayNumber = 1;

    public Breeding(int energyNeededForBreeding, int energyUsedWhileBreeding, GlobeMap map, AnimalCreator animalCreator) {
        this.energyNeededForBreeding = energyNeededForBreeding;
        this.energyUsedWhileBreeding = energyUsedWhileBreeding;
        this.map = map;
        this.animalCreator = animalCreator;
    }

    public int getEnergyNeededForBreeding() { return energyNeededForBreeding;}

    public void breedAnimals(int dayNumber, Simulation simulation) {
        this.dayNumber = dayNumber;
        map.findAnimalsToBreed(this, simulation);
    }

    public Optional<Animal> breedPair(List<Animal> breedingPair) {
        if (breedingPair.size() == 2){
            return Optional.of(breedingPair.getFirst().breed(breedingPair.get(1), energyUsedWhileBreeding, dayNumber, animalCreator));
        }
        return Optional.empty();
    }
}

package agh.ics.oop.model;

import java.util.List;
import java.util.Optional;

public class Breeding {
    private final int energyNeededForBreeding;
    private final int energyUsedWhileBreeding;
    private final GlobeMap map;
    private int dayNumber = 1;

    public Breeding(int energyNeededForBreeding, int energyUsedWhileBreeding, GlobeMap map) {
        this.energyNeededForBreeding = energyNeededForBreeding;
        this.energyUsedWhileBreeding = energyUsedWhileBreeding;
        this.map = map;
    }

    public int getEnergyNeededForBreeding() { return energyNeededForBreeding;}

    public void breedAnimals(int dayNumber) {
        this.dayNumber = dayNumber;
        map.breedAnimals(this);
    }

    public Optional<Animal> breedPair(List<Animal> breedingPair) {
        if (breedingPair.size() == 2){
            return Optional.of(breedingPair.getFirst().breed(breedingPair.get(1), energyUsedWhileBreeding, dayNumber));
        }
        return Optional.empty();
    }
}

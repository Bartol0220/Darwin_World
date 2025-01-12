package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.genes.*;
import agh.ics.oop.model.grass.AbstractGrassMaker;
import agh.ics.oop.model.grass.GrassMakerDeadAnimal;
import agh.ics.oop.model.grass.GrassMakerEquator;

public class World {
    public static void main(String[] args) {

        // Wszystkie parametry smymulacji:
        int width = 5; // TODO wyjątek jeśli < 0
        int height = 5; // TODO wyjątek jeśli < 0
        int startGrassNumber = 1; // TODO wyjątek jeśli < 0
        int dayGrassNumber = 1; // TODO wyjątek jeśli < 0
        int energyProvidedByEatingGrass = 1; // TODO wyjątek jeśli < 0
        boolean isDeathGivingLife = false;
        int startNumberOfAnimals = 2; // TODO wyjątek jeśli < 0
        int startingEnergy = 10; // TODO wyjątek jeśli < 0
        int energyNeededForBreeding = 3; // TODO wyjątek jeśli < 0
        int energyUsedWhileBreeding = 2; // TODO wyjątek jeśli < energyNeededForBreeding
        int minimumNumberOfMutations = 0; // TODO wyjątek jeśli < 0
        int maximumNumberOfMutations = 5; // TODO wyjątek jeśli < minimumNumberOfMutations i jeśli > genesNumber
        boolean isSlightCorrection = false;
        int genesNumber = 5; // TODO wyjątek jeśli < 0

        GlobeMap map = new GlobeMap(width, height, 0);
        MapChangeListener listener = new ConsoleMapDisplay();
        map.registerObserver(listener);

        AbstractGrassMaker grassMaker;
        if (isDeathGivingLife) {
            grassMaker = new GrassMakerDeadAnimal(startGrassNumber, dayGrassNumber, map);
        } else {
            grassMaker = new GrassMakerEquator(startGrassNumber, dayGrassNumber, map);
        }

        GeneMutator geneMutator;
        if (isSlightCorrection) {
            geneMutator = new ClassicMutation(minimumNumberOfMutations, maximumNumberOfMutations);
        } else {
            geneMutator = new SlightCorrection(minimumNumberOfMutations, maximumNumberOfMutations);
        }
        GenesFactory genesFactory = new GenesFactory(geneMutator, genesNumber);

        AnimalCreator animalCreator = new AnimalCreator(startingEnergy, energyUsedWhileBreeding, energyProvidedByEatingGrass, genesFactory);
        Breeding breeding = new Breeding(energyNeededForBreeding, energyUsedWhileBreeding, map, animalCreator);

        Simulation simulation = new Simulation(map, grassMaker, breeding, animalCreator, startNumberOfAnimals);
        simulation.run();
}
}

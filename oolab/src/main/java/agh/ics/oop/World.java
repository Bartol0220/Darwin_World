package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.genes.ClassicMutation;
import agh.ics.oop.model.genes.GeneMutator;
import agh.ics.oop.model.genes.GenesFactory;
import agh.ics.oop.model.genes.SlightCorrection;
import agh.ics.oop.model.grass.AbstractGrassMaker;
import agh.ics.oop.model.grass.GrassMakerDeadAnimal;
import agh.ics.oop.model.grass.GrassMakerEquator;

import java.util.List;

public class World {
    public static void main(String[] args) {

        // Wszystkie parametry smymulacji:
        int width = 2; // TODO wyjątek jeśli < 0
        int height = 2; // TODO wyjątek jeśli < 0
        int startGrassNumber = 1; // TODO wyjątek jeśli < 0
        int dayGrassNumber = 1; // TODO wyjątek jeśli < 0
        int energyProvidedByEatingGrass = 1; // TODO wyjątek jeśli < 0
        boolean isLifeGivingDeath = false;
        int startNumberOfAnimals = 0; // TODO wyjątek jeśli < 0
        int energyNeededForBreeding = 10; // TODO wyjątek jeśli < 0
        int energyUsedWhileBreeding = 10; // TODO wyjątek jeśli < energyNeededForBreeding
        int minimumNumberOfMutations = 0; // TODO wyjątek jeśli < 0
        int maximumNumberOfMutations = 10; // TODO wyjątek jeśli < minimumNumberOfMutations i jeśli > genesNumber
        boolean isSlightCorrection = false;
        int genesNumber = 5; // TODO wyjątek jeśli < 0

        GlobeMap map = new GlobeMap(width, height, 0);
        MapChangeListener listener = new ConsoleMapDisplay();
        map.registerObserver(listener);

        AbstractGrassMaker grassMaker;
        if (isLifeGivingDeath) {
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

        Breeding breeding = new Breeding(energyNeededForBreeding, energyUsedWhileBreeding, map);
        List<Vector2d> animalPositions = List.of(new Vector2d(0,1), new Vector2d(0,0));

        Simulation simulation1 = new Simulation(animalPositions, map, 100, grassMaker, breeding, genesFactory);
        simulation1.run();
}
}

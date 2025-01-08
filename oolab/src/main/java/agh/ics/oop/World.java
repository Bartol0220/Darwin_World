package agh.ics.oop;

import agh.ics.oop.model.*;

public class World {
    public static void main(String[] args) {
        Genes fatherGenes = new Genes(5);
        System.out.println("silniejszy " + fatherGenes);
        Animal father = new Animal(10, fatherGenes);
        Genes motherGenes = new Genes(5);
        System.out.println("slabszy " + motherGenes);
        Animal mother = new Animal(10, motherGenes);
        GeneMutator geneRandomizer = new SlightCorrection();
        Genes kidGenes = new Genes(father, mother, geneRandomizer, 5);


//        Globe globe = new Globe(3, 3, 0);
//        List<MoveDirection> directions1 = new ArrayList<>();
//        MapChangeListener listener = new ConsoleMapDisplay();
//        globe.registerObserver(listener);
//        List<Vector2d> positions1 = List.of(new Vector2d(0,1), new Vector2d(2,2));
//        Simulation simulation1 = new Simulation(positions1, globe, directions1, 10, 3);
//        simulation1.run();
}
}

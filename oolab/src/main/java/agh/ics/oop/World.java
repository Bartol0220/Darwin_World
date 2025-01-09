package agh.ics.oop;

import agh.ics.oop.model.*;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

import static java.util.Collections.sort;

public class World {
    public static void main(String[] args) {
        Genes fatherGenes = new Genes(5);
        System.out.println("silniejszy " + fatherGenes);
        Animal father = new Animal(12, fatherGenes, 0);
        Genes motherGenes = new Genes(5);
        System.out.println("slabszy " + motherGenes);
        Animal mother = new Animal(10, motherGenes, 0);
        GeneMutator geneRandomizer = new SlightCorrection();
        Genes kidGenes = new Genes(father, mother, geneRandomizer, 5);
        Animal kid = new Animal(3, kidGenes, 0);

        ArrayList<Animal> lista = new ArrayList<>();
        lista.add(mother);
        lista.add(kid);
        lista.add(father);
        sort(lista);
        for (Animal animal : lista) System.out.println(animal.getEnergy());


//        Globe globe = new Globe(3, 3, 0);
//        List<MoveDirection> directions1 = new ArrayList<>();
//        MapChangeListener listener = new ConsoleMapDisplay();
//        globe.registerObserver(listener);
//        List<Vector2d> positions1 = List.of(new Vector2d(0,1), new Vector2d(2,2));
//        Simulation simulation1 = new Simulation(positions1, globe, directions1, 10, 3);
//        simulation1.run();
}
}

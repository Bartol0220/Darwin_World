package agh.ics.oop.model;

import agh.ics.oop.model.util.RandomGenes;

import java.util.Arrays;

public class ClassicMutation extends AbstractGeneMutator {
    @Override
    public void mutate(int[] genes) {
        int[] genesToChange = randomGeneIndex(genes);
        for (int geneToChange : genesToChange){
            RandomGenes randomPositionGenerator = new RandomGenes(8, genes[geneToChange], 1);
            genes[geneToChange] = randomPositionGenerator.iterator().next();
        }
    }
}

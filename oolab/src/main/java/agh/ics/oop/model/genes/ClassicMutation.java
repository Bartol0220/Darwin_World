package agh.ics.oop.model.genes;

import agh.ics.oop.model.util.RandomGenes;

public class ClassicMutation extends AbstractGeneMutator {

    public ClassicMutation(int minimumNumberOfMutations, int maximumNumberOfMutations) {
        super(minimumNumberOfMutations, maximumNumberOfMutations);
    }

    @Override
    protected void mutateGene(int geneToChange, int[] genes) {
        RandomGenes randomPositionGenerator = new RandomGenes(8, genes[geneToChange], 1);
        genes[geneToChange] = randomPositionGenerator.iterator().next();
    }
}

package agh.ics.oop.model.genes;

import agh.ics.oop.model.util.RandomGenes;

public class ClassicMutation extends AbstractGeneMutator {

    public ClassicMutation(int minimumNumberOfMutations, int maximumNumberOfMutations) {
        super(minimumNumberOfMutations, maximumNumberOfMutations);
    }

//    @Override
//    public void mutate(int[] genes) {
//        int howManyChanged = random.nextInt(minimumNumberOfMutations, maximumNumberOfMutations+1);
//        RandomGenes genesToChange = new RandomGenes(genes.length, -1, howManyChanged);
//
//        for (int geneToChange : genesToChange){
//            RandomGenes randomPositionGenerator = new RandomGenes(8, genes[geneToChange], 1);
//            genes[geneToChange] = randomPositionGenerator.iterator().next();
//        }
//    }

    @Override
    protected void mutateGene(int geneToChange, int[] genes) {
        RandomGenes randomPositionGenerator = new RandomGenes(8, genes[geneToChange], 1);
        genes[geneToChange] = randomPositionGenerator.iterator().next();
    }
}

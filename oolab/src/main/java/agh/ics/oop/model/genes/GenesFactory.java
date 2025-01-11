package agh.ics.oop.model.genes;

import agh.ics.oop.model.Animal;

public class GenesFactory {
    private final GeneMutator geneMutator;
    private final int genesNumber;

    public GenesFactory(GeneMutator geneMutator, int genesNumber) {
        this.geneMutator = geneMutator;
        this.genesNumber = genesNumber;
    }

    public Genes makeStartingGenes() {
        return new Genes(genesNumber);
    }

    public Genes makeGenes(Animal stronger, Animal weaker) {
        return new Genes(stronger, weaker, geneMutator, genesNumber);
    }
}

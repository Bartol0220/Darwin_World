package agh.ics.oop.model.grass;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.RandomGrassGrow;

import java.util.*;

public abstract class AbstractGrassMaker implements GrassMaker {
    protected final HashSet<Vector2d> allBetterGrassPositions = new HashSet<>();
    protected final List<Vector2d> freeBetterGrassPositions = new ArrayList<>();
    protected final List<Vector2d> freeWorseGrassPositions = new ArrayList<>();
    protected final int dayGrassNumber;
    protected final GlobeMap map;
    protected int currentGrassNumber = 0;

    protected AbstractGrassMaker(int dayGrassNumber, GlobeMap map) {
        this.dayGrassNumber = dayGrassNumber;
        this.map = map;
    }

    public int getCurrentGrassNumber() { return currentGrassNumber;}

    @Override
    public void grow() {
        growNumberOfGrasss(dayGrassNumber);
    }

    protected void growNumberOfGrasss(int numberOfGrasss) {
        RandomGrassGrow randomPositionGenerator = new RandomGrassGrow(freeBetterGrassPositions, freeWorseGrassPositions, numberOfGrasss);
        for(Vector2d grassPosition : randomPositionGenerator) {
            map.addGrass(grassPosition);
            currentGrassNumber++;
        }
    }

    protected void changePositionToBetter(Vector2d position) {
        freeBetterGrassPositions.add(position);
        allBetterGrassPositions.add(position);
        freeWorseGrassPositions.remove(position);
    }

    protected void changePositionToWorse(Vector2d position) {
        freeWorseGrassPositions.add(position);
        allBetterGrassPositions.remove(position);
        freeBetterGrassPositions.remove(position);
    }

    protected void addPositionToPossiblePositions(Vector2d position) {
        if (allBetterGrassPositions.contains(position)) {
            freeBetterGrassPositions.add(position);
        } else {
            freeWorseGrassPositions.add(position);
        }
    }

    @Override
    public void grassEaten(Grass grass) {
        addPositionToPossiblePositions(grass.getPosition());
        map.removeGrass(grass);
        currentGrassNumber--;
    }
}

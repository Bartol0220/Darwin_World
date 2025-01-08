package agh.ics.oop.model.grass;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.RandomGrassGrow;

import java.util.*;

public abstract class AbstractGrassMaker implements GrassMaker {
    protected final int height;
    protected final int width;
    protected final HashSet<Vector2d> allBetterGrassPositions = new HashSet<>();
    protected final List<Vector2d> freeBetterGrassPositions = new ArrayList<>();
    protected final List<Vector2d> freeWorseGrassPositions = new ArrayList<>();
    protected final int dayGrassNumber;
    public final Map<Vector2d, WorldElement> grassMap = new HashMap<>();

    protected AbstractGrassMaker(int dayGrassNumber, int height, int width) {
        this.dayGrassNumber = dayGrassNumber;
        this.height = height;
        this.width = width;
    }

    protected void growStartGrass(int startGrassNumber) {
        allBetterGrassPositions.addAll(freeBetterGrassPositions);
        RandomGrassGrow randomPositionGenerator = new RandomGrassGrow(freeBetterGrassPositions, freeWorseGrassPositions, startGrassNumber);
        for(Vector2d grassPosition : randomPositionGenerator) {
            grassMap.put(grassPosition, new Grass(grassPosition));
        }
    }

    @Override
    public void grow() {
        RandomGrassGrow randomPositionGenerator = new RandomGrassGrow(freeBetterGrassPositions, freeWorseGrassPositions, dayGrassNumber);
        for(Vector2d grassPosition : randomPositionGenerator) {
            grassMap.put(grassPosition, new Grass(grassPosition));
        }
    }

    protected void changePositionToFirstCategory(Vector2d position) {
        freeBetterGrassPositions.add(position);
        allBetterGrassPositions.add(position);
        freeWorseGrassPositions.remove(position);
    }

    protected void changePositionToSecondCategory(Vector2d position) {
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
    }
}

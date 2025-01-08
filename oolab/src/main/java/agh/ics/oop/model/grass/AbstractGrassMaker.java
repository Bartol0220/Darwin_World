package agh.ics.oop.model.grass;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.RandomGrassGrow;

import java.util.*;

public abstract class AbstractGrassMaker implements GrassMaker {
    protected final int height;
    protected final int width;
    public final HashSet<Vector2d> allFirstCategoryPositions = new HashSet<>();
    protected final List<Vector2d> firstCategoryPositions = new ArrayList<>();
    protected final List<Vector2d> secondCategoryPositions = new ArrayList<>();
    protected final int dayGrassNumber;
    public final Map<Vector2d, WorldElement> grassMap = new HashMap<>();

    protected AbstractGrassMaker(int dayGrassNumber, int height, int width) {
        this.dayGrassNumber = dayGrassNumber;
        this.height = height;
        this.width = width;
    }

    protected void growStartGrass(int startGrassNumber) {
        allFirstCategoryPositions.addAll(firstCategoryPositions);
        RandomGrassGrow randomPositionGenerator = new RandomGrassGrow(firstCategoryPositions, secondCategoryPositions, startGrassNumber);
        for(Vector2d grassPosition : randomPositionGenerator) {
            grassMap.put(grassPosition, new Grass(grassPosition));
        }
    }

    @Override
    public void grow() {
        RandomGrassGrow randomPositionGenerator = new RandomGrassGrow(firstCategoryPositions, secondCategoryPositions, dayGrassNumber);
        for(Vector2d grassPosition : randomPositionGenerator) {
            grassMap.put(grassPosition, new Grass(grassPosition));
        }
    }

    protected void changePositionToFirstCategory(Vector2d position) {
        firstCategoryPositions.add(position);
        allFirstCategoryPositions.add(position);
        secondCategoryPositions.remove(position);
    }

    protected void changePositionToSecondCategory(Vector2d position) {
        secondCategoryPositions.add(position);
        allFirstCategoryPositions.remove(position);
        firstCategoryPositions.remove(position);
    }

    protected void addPositionToPossiblePositions(Vector2d position) {
        if (allFirstCategoryPositions.contains(position)) {
            firstCategoryPositions.add(position);
        } else {
            secondCategoryPositions.add(position);
        }
    }

    @Override
    public void grassEaten(Grass grass) {
        addPositionToPossiblePositions(grass.getPosition());
    }
}

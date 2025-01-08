package agh.ics.oop.model;

import agh.ics.oop.model.grass.Grass;
import agh.ics.oop.model.util.Boundary;
import agh.ics.oop.model.util.RandomVector2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrassField extends AbstractWorldMap{
    private final Map<Vector2d, WorldElement> grassMap = new HashMap<>();

    public GrassField(int grassNumber, int id) {
        super(id);
        RandomVector2d randomPositionGenerator = new RandomVector2d(grassNumber);
        for(Vector2d grassPosition : randomPositionGenerator) {
            grassMap.put(grassPosition, new Grass(grassPosition));
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return super.isOccupied(position) || grassMap.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        WorldElement animalPosition = super.objectAt(position);
        if (animalPosition != null) {
            return animalPosition;
        }
        return grassMap.get(position);
    }

    @Override
    public Boundary getCurrentBounds(){
        Vector2d drawLowerLeft = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Vector2d drawUpperRight = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);

        List<WorldElement> elements = getElements();

        for(WorldElement element : elements) {
            drawLowerLeft = drawLowerLeft.lowerLeft(element.getPosition());
            drawUpperRight = drawUpperRight.upperRight(element.getPosition());
        }

        return new Boundary(drawLowerLeft, drawUpperRight);
    }

    @Override
    public ArrayList<WorldElement> getElements() {
        ArrayList<WorldElement> elements = super.getElements();
        elements.addAll(grassMap.values());
        return elements;
    }
}

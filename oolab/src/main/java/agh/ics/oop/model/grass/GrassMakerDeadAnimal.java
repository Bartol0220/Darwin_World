package agh.ics.oop.model.grass;

import agh.ics.oop.model.MapField;
import agh.ics.oop.model.*;
import agh.ics.oop.model.observers.AnimalDiedObserver;
import agh.ics.oop.model.observers.NewDayObserver;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class GrassMakerDeadAnimal extends AbstractGrassMaker implements AnimalDiedObserver, NewDayObserver {
    public static final int FEW_DAYS = 5;
    private int currentDay=0;

    public GrassMakerDeadAnimal(int startGrassNumber, int dayGrassNumber, GlobeMap map) {
        super(dayGrassNumber, map);

        for (int y=0; y < map.getHeight(); y++) {
            for (int x=0; x < map.getWidth(); x++) {
                freeWorseGrassPositions.add(new Vector2d(x, y));
            }
        }

        growNumberOfGrasss(startGrassNumber);
    }

    @Override
    public void animalDied(Animal animal) {
        for (int y=max(animal.getPosition().getY()-1, 0); y <= min(animal.getPosition().getY()+1, map.getHeight()-1); y++) {
            for (int x=animal.getPosition().getX()-1; x <= animal.getPosition().getX()+1; x++) {
                changePositionToBetter(map.handleBoundsPositions(new Vector2d(x, y)));
            }
        }
    }

    @Override
    public void newDay(int day) {
        currentDay = day;
        changePosition();
    }

    private void changePosition() { // TODO nazwa na bardziej pomysłowa
        // TODO weźmie z mapo stosu BFS-owego pozycje i odjemie 1, jak 0 to wywoła changePositionToWorseAfterFewDays(Vector2d position)
    }

    public void changePositionToWorseAfterFewDays(Vector2d position) {
//        MapField currentField = map.getMapField(position);
        MapField currentField = new MapField(position); // TODO zamienić na to wyżej
        if (currentDay - currentField.getLastDeathDate() == FEW_DAYS) {
            changePositionToWorse(map.handleBoundsPositions(position));
        }
    }
}

package agh.ics.oop.model.grass;

import agh.ics.oop.model.MapField;
import agh.ics.oop.model.*;
import agh.ics.oop.model.observers.AnimalDiedObserver;
import agh.ics.oop.model.observers.NewDayObserver;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class GrassMakerDeadAnimal extends AbstractGrassMaker implements AnimalDiedObserver, NewDayObserver {
    public static final int FEW_DAYS = 5;
    private int currentDay=0;
    private final HashSet<MapField> temporrarilyBetter = new HashSet<>();

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
                Vector2d currentPosition = new Vector2d(x,y);
                changePositionToBetter(map.handleBoundsPositions(currentPosition));
                MapField mapField = map.getMapField(map.handleBoundsPositions(currentPosition));
                mapField.animalDiedOnField(currentDay);
                temporrarilyBetter.add(mapField);
            }
        }
    }

    @Override
    public void newDay(int day) {
        currentDay = day;
        changeFieldsPositionToWorseAfterFewDays();
    }

    private List<MapField> getPositionsToDowngrade() {
        return temporrarilyBetter.stream()
                .filter(mapField -> mapField.getLastDeathDate() == currentDay - FEW_DAYS)
                .toList();
    }

    public void changeFieldsPositionToWorseAfterFewDays() {
        List<MapField> fields = getPositionsToDowngrade();
        for (MapField mapField : fields){
            changePositionToWorse(map.handleBoundsPositions(mapField.getPosition()));
            temporrarilyBetter.remove(mapField);
        }
    }

    public Set<MapField> getTemporarilyBetterPositions(){
        return Set.copyOf(temporrarilyBetter);
    }
}

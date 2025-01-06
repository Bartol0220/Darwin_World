package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.List;

public class OptionsParser {
    public static List<MoveDirection> parse(String[] args) {

        List<MoveDirection> moveDirectionsList = new ArrayList<>();

        for(String element : args){
            switch(element) {
                case "f" -> moveDirectionsList.add(MoveDirection.FORWARD);
                case "b" -> moveDirectionsList.add(MoveDirection.BACKWARD);
                case "l" -> moveDirectionsList.add(MoveDirection.LEFT);
                case "r" -> moveDirectionsList.add(MoveDirection.RIGHT);
                default -> throw new IllegalArgumentException("Invalid move direction: " + element);
            }
        }

        return moveDirectionsList;
    }
}

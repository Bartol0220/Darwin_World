package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OptionsParserTest {
    @Test
    void doesParseWorkWithCorrectDirections() {
        // given
        String[] array1 = new String[0]; // empty array
        List<MoveDirection> correctList1 = List.of();

        String[] array2 = {"f", "r", "b", "l"}; // only valid values
        List<MoveDirection> correctList2 = List.of(MoveDirection.FORWARD, MoveDirection.RIGHT, MoveDirection.BACKWARD, MoveDirection.LEFT);

        String[] array3 = {"f", "f", "f", "f", "f"}; // only valid values, only one direction
        List<MoveDirection> correctList3 = List.of(MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD);


        // when
        List<MoveDirection> resultList1 = OptionsParser.parse(array1);
        List<MoveDirection> resultList2 = OptionsParser.parse(array2);
        List<MoveDirection> resultList3 = OptionsParser.parse(array3);

        // then
        assertEquals(correctList1, resultList1);
        assertEquals(correctList2, resultList2);
        assertEquals(correctList3, resultList3);
    }

    @Test
    void doesParseWorkWithIncorrectDirections() {
        // given
        String[] array1 = {"g", "t", "n", "k"}; // only invalid values

        String[] array2 = {"r", "f", "u", "i", "l", "z", "b"}; // all values

        // when, then
        assertThrows(IllegalArgumentException.class, () -> OptionsParser.parse(array1));
        assertThrows(IllegalArgumentException.class, () -> OptionsParser.parse(array2));

    }
}
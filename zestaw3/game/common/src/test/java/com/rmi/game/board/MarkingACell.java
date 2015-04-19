package com.rmi.game.board;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by novy on 18.04.15.
 */

public class MarkingACell {

    @Test
    public void shouldReturnFalseAskedAboutAlreadyMarkedCell() throws Exception {

        final BoardImpl objectUnderTest = new BoardImpl();
        objectUnderTest.makeMovement(
                MovementFactory.movementOf(0, 0, BoardCell.CROSS)
        );

        assertThat(objectUnderTest.movePossible(Coordinates.of(0, 0))).isFalse();
    }

    @Test
    public void shouldReturnFalseAskedAboutMarkingPossibilityWhenSomeoneWon() throws Exception {

        final BoardImpl objectUnderTest = new BoardImpl();
        objectUnderTest.makeMovement(
                MovementFactory.movementOf(0, 0, BoardCell.CROSS)
        );
        objectUnderTest.makeMovement(
                MovementFactory.movementOf(0, 1, BoardCell.CROSS)
        );
        objectUnderTest.makeMovement(
                MovementFactory.movementOf(0, 2, BoardCell.CROSS)
        );


        assertThat(objectUnderTest.movePossible(Coordinates.of(2, 2))).isFalse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotBeAbleToMarkAlreadyMarkedCell() throws Exception {

        final BoardImpl objectUnderTest = new BoardImpl();
        objectUnderTest.makeMovement(
                MovementFactory.movementOf(0, 0, BoardCell.CROSS)
        );

        objectUnderTest.makeMovement(
                MovementFactory.movementOf(0, 0, BoardCell.NOUGHT)
        );

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotBeAbleToMarkCellWhenSomeoneWon() throws Exception {

        final BoardImpl objectUnderTest = new BoardImpl();
        objectUnderTest.makeMovement(
                MovementFactory.movementOf(0, 0, BoardCell.CROSS)
        );
        objectUnderTest.makeMovement(
                MovementFactory.movementOf(0, 1, BoardCell.CROSS)
        );
        objectUnderTest.makeMovement(
                MovementFactory.movementOf(0, 2, BoardCell.CROSS)
        );

        objectUnderTest.makeMovement(
                MovementFactory.movementOf(2, 2, BoardCell.NOUGHT)
        );
    }
}
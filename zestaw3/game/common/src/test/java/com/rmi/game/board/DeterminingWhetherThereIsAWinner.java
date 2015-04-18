package com.rmi.game.board;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by novy on 18.04.15.
 */
public class DeterminingWhetherThereIsAWinner {

    @Test
    public void shouldReturnTrueWhenThereIsAWinningRow() throws Exception {

        final BoardImpl objectUnderTest = new BoardImpl();

        objectUnderTest.makeMovement(
                MovementFactory.movementOf(1, 0, BoardCell.NOUGHT)
        );
        objectUnderTest.makeMovement(
                MovementFactory.movementOf(1, 1, BoardCell.NOUGHT)
        );
        objectUnderTest.makeMovement(
                MovementFactory.movementOf(1, 2, BoardCell.NOUGHT)
        );

        assertThat(objectUnderTest.thereIsAWinner()).isTrue();

    }

    @Test
    public void shouldReturnTrueWhenThereIsAWinningColumn() throws Exception {

        final BoardImpl objectUnderTest = new BoardImpl();

        objectUnderTest.makeMovement(
                MovementFactory.movementOf(0, 1, BoardCell.NOUGHT)
        );
        objectUnderTest.makeMovement(
                MovementFactory.movementOf(1, 1, BoardCell.NOUGHT)
        );
        objectUnderTest.makeMovement(
                MovementFactory.movementOf(2, 1, BoardCell.NOUGHT)
        );

        assertThat(objectUnderTest.thereIsAWinner()).isTrue();
    }

    @Test
    public void shouldReturnTrueWhenThereIsAWinningLeftDiagonal() throws Exception {

        final BoardImpl objectUnderTest = new BoardImpl();

        objectUnderTest.makeMovement(
                MovementFactory.movementOf(0, 0, BoardCell.NOUGHT)
        );
        objectUnderTest.makeMovement(
                MovementFactory.movementOf(1, 1, BoardCell.NOUGHT)
        );
        objectUnderTest.makeMovement(
                MovementFactory.movementOf(2, 2, BoardCell.NOUGHT)
        );

        assertThat(objectUnderTest.thereIsAWinner()).isTrue();

    }

    @Test
    public void shouldReturnTrueWhenThereIsAWinningRightDiagonal() throws Exception {

        final BoardImpl objectUnderTest = new BoardImpl();

        objectUnderTest.makeMovement(
                MovementFactory.movementOf(0, 2, BoardCell.NOUGHT)
        );
        objectUnderTest.makeMovement(
                MovementFactory.movementOf(1, 1, BoardCell.NOUGHT)
        );
        objectUnderTest.makeMovement(
                MovementFactory.movementOf(2, 0, BoardCell.NOUGHT)
        );

        assertThat(objectUnderTest.thereIsAWinner()).isTrue();
    }

    @Test
    public void shouldReturnFalseOnDraw() throws Exception {

        final Board objectUnderTest = new BoardImpl();

        markAllCellsToBeADraw(objectUnderTest);

        assertThat(objectUnderTest.thereIsAWinner()).isFalse();
    }

    @Test
    public void shouldReturnFalseOtherwise() throws Exception {

        final BoardImpl objectUnderTest = new BoardImpl();

        assertThat(objectUnderTest.thereIsAWinner()).isFalse();
    }

    private void markAllCellsToBeADraw(Board objectUnderTest) throws Exception {

        objectUnderTest.makeMovement(
                MovementFactory.movementOf(0, 0, BoardCell.CROSS)
        );
        objectUnderTest.makeMovement(
                MovementFactory.movementOf(0, 1, BoardCell.NOUGHT)
        );
        objectUnderTest.makeMovement(
                MovementFactory.movementOf(0, 2, BoardCell.NOUGHT)
        );


        objectUnderTest.makeMovement(
                MovementFactory.movementOf(1, 0, BoardCell.NOUGHT)
        );
        objectUnderTest.makeMovement(
                MovementFactory.movementOf(1, 1, BoardCell.CROSS)
        );
        objectUnderTest.makeMovement(
                MovementFactory.movementOf(1, 2, BoardCell.CROSS)
        );

        objectUnderTest.makeMovement(
                MovementFactory.movementOf(2, 0, BoardCell.CROSS)
        );
        objectUnderTest.makeMovement(
                MovementFactory.movementOf(2, 1, BoardCell.CROSS)
        );
        objectUnderTest.makeMovement(
                MovementFactory.movementOf(2, 2, BoardCell.NOUGHT)
        );
    }

}

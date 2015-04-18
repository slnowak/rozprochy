package com.rmi.game.board;

import com.rmi.game.Constants;
import org.junit.Test;

/**
 * Created by novy on 18.04.15.
 */
public class CoordinatesTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionGivenRowCoordinateBelowZero() throws Exception {

        Coordinates.of(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionGivenRowCoordinateAboveMaximumLimit() throws Exception {

        Coordinates.of(0, Constants.BOARD_ROWS + 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionGivenColumnCoordinateBelowZero() throws Exception {

        Coordinates.of(0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionGivenColumnCoordinateAboveMaximumLimit() throws Exception {

        Coordinates.of(0, Constants.BOARD_COLUMNS + 1);
    }
}
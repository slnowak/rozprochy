package com.rmi.game.board;

import org.junit.Test;

/**
 * Created by novy on 18.04.15.
 */
public class MovementTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionGivenEMPTYMarker() throws Exception {


        Movement.of(
                Coordinates.of(0, 0),
                BoardCell.EMPTY
        );
    }
}
package com.rmi.game.board;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by novy on 18.04.15.
 */

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Movement implements Serializable {

    private final Coordinates coordinates;
    private final BoardCell marker;

    public static Movement of(Coordinates coordinates, BoardCell marker) {
        Preconditions.checkArgument(marker != BoardCell.EMPTY, "Marker cannot be EMPTY");

        return new Movement(coordinates, marker);
    }
}

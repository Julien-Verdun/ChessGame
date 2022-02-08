package com.chesspackage;

import java.util.ArrayList;
import java.util.Arrays;

public class BoardDimensions {
    public static final int WIDTHCELL = 50;
    public static final int HEIGHTCELL = 50;
    public static final int WHITESIDE = (int) Math.round(Math.random());
    public static final String WHITE = "white";
    public static final String BLACK = "black";
    public static final ArrayList<String> colors = new ArrayList<>(Arrays.asList(WHITE,BLACK));
    public static final int PADDINGX = 100;
    public static final int PADDINGY = 50;
    public static final int CELLSIZE = 50;
    public static final int GRIDSIZE = 8;
    public static final String KING = "King";
    public static final String QUEEN = "Queen";
    public static final String KNIGHT = "Knight";
    public static final String BISHOP = "Bishop";
    public static final String TOWER = "Tower";
    public static final String PAWN = "Pawn";

}

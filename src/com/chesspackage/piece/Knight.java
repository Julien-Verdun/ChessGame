package com.chesspackage.piece;

import java.awt.*;
import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(String color, int initialX) {
        super(KNIGHT, color, initialX, 600, color.equals(WHITE) ? 0 : 200);
    }

    public boolean isLegalMove(int crdX, int crdY){
        return (isNotMyCell(crdX, crdY) & Math.abs(crdX - crdPieceX) == 1 & Math.abs(crdY - crdPieceY) == 2) || (Math.abs(crdX - crdPieceX) == 2 & Math.abs(crdY - crdPieceY) == 1);
    }

    public ArrayList<Point> getLegalMoves(){
        ArrayList<Point> legalMoves = new ArrayList<>();
        if (isInBoard(crdPieceX + 2, crdPieceY -1)) legalMoves.add(new Point(crdPieceX + 2, crdPieceY -1));
        if (isInBoard(crdPieceX + 2, crdPieceY +1)) legalMoves.add(new Point(crdPieceX + 2, crdPieceY +1));
        if (isInBoard(crdPieceX - 2, crdPieceY -1)) legalMoves.add(new Point(crdPieceX - 2, crdPieceY -1));
        if (isInBoard(crdPieceX - 2, crdPieceY +1)) legalMoves.add(new Point(crdPieceX - 2, crdPieceY +1));
        if (isInBoard(crdPieceX + 1, crdPieceY +2)) legalMoves.add(new Point(crdPieceX + 1, crdPieceY +2));
        if (isInBoard(crdPieceX - 1, crdPieceY +2)) legalMoves.add(new Point(crdPieceX - 1, crdPieceY +2));
        if (isInBoard(crdPieceX + 1, crdPieceY -2)) legalMoves.add(new Point(crdPieceX + 1, crdPieceY -2));
        if (isInBoard(crdPieceX - 1, crdPieceY -2)) legalMoves.add(new Point(crdPieceX - 1, crdPieceY -2));
        return legalMoves;
    }

    public boolean canTake(int crdX, int crdY){
        return isLegalMove(crdX,crdY);
    }
}

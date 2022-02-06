package com.chesspackage.piece;

import java.awt.*;
import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(String color, int initialX) {
        super("Bishop", color, initialX, 400, color.equals(WHITE) ? 0 : 200);
    }

    public boolean isLegalMove(int crdX, int crdY){
        return (Math.abs(crdX - lastCrdX) == Math.abs(crdY - lastCrdY) );
    }

    public ArrayList<Point> getLegalMoves(){
        ArrayList<Point> legalMoves = new ArrayList<>();
        int i = 1;
        while (crdPieceX+i<=GRIDSIZE-1){
            if (crdPieceY+i <= GRIDSIZE-1) legalMoves.add(new Point(crdPieceX+i,crdPieceY+i));
            if (crdPieceY-i >= 0) legalMoves.add(new Point(crdPieceX+i,crdPieceY-i));
            i++;
        }
        int j = 1;
        while (crdPieceX-j>=0){
            if (crdPieceY+j <= GRIDSIZE-1) legalMoves.add(new Point(crdPieceX-j,crdPieceY+j));
            if (crdPieceY-j >= 0) legalMoves.add(new Point(crdPieceX-j,crdPieceY-j));
            j++;
        }

        return legalMoves;
    }

    public boolean canTake(int crdX, int crdY){
        return isNotMyCell(crdX, crdY) && isLegalMove(crdX,crdY);
    }

}

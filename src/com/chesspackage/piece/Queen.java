package com.chesspackage.piece;

import java.awt.*;
import java.util.ArrayList;

public class Queen extends Piece {
    public Queen(String color) {
        super("Queen", color, 3, 200, color.equals(WHITE) ? 0 : 200);
    }

    public boolean isLegalMove(int crdX, int crdY){
        return (crdX == lastCrdX || crdY == lastCrdY || Math.abs(crdX - lastCrdX) == Math.abs(crdY - lastCrdY) );
    }

    public ArrayList<Point> getLegalMoves(){

        ArrayList<Point> legalMoves = new ArrayList<>();

        // Tower legal moves
        int i = 1;
        while (i<=Math.max(GRIDSIZE-1-crdPieceX, crdPieceX) ){
            if (isInBoard(crdPieceX+i, crdPieceY)) legalMoves.add(new Point(crdPieceX+i,crdPieceY));
            if (isInBoard(crdPieceX-i, crdPieceY)) legalMoves.add(new Point(crdPieceX-i,crdPieceY));
            i++;
        }
        int j = 1;
        while (j<=Math.max(GRIDSIZE-1-crdPieceY, crdPieceY)){
            if (isInBoard(crdPieceX, crdPieceY+j)) legalMoves.add(new Point(crdPieceX, crdPieceY+j));
            if (isInBoard(crdPieceX, crdPieceY-j)) legalMoves.add(new Point(crdPieceX, crdPieceY-j));
            j++;
        }

        // Bishop legal moves
        i = 1;
        while (crdPieceX+i<=GRIDSIZE-1){
            if (crdPieceY+i <= GRIDSIZE-1) legalMoves.add(new Point(crdPieceX+i,crdPieceY+i));
            if (crdPieceY-i >= 0) legalMoves.add(new Point(crdPieceX+i,crdPieceY-i));
            i++;
        }
        j = 1;
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
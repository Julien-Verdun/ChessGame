package com.chesspackage.piece;

import java.awt.*;
import java.util.ArrayList;

public class Tower extends Piece {

    public Tower(String color, int initialX) {
        super("Tower", color, initialX, 800, color.equals(WHITE) ? 0 : 200);
        canRock = true;
    }

    public void rock(){
        canRock = false;
    }
    public void unrock(){
        canRock = true;
    }

    public boolean isLegalMove(int crdX, int crdY){
        return (crdX == lastCrdX || crdY == lastCrdY);
    }

    public ArrayList<Point> getLegalMoves(){
        ArrayList<Point> legalMoves = new ArrayList<>();

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
        return legalMoves;
    }


    public boolean canTake(int crdX, int crdY){
        return isNotMyCell(crdX, crdY) && isLegalMove(crdX,crdY);
    }
}

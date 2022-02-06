package com.chesspackage.piece;

import java.awt.*;
import java.util.ArrayList;

public class King extends Piece {

    public King(String color) {
        super("King", color, 4, 0, color.equals(WHITE) ? 0 : 200);
        canRock = true;
    }

    public boolean isLegalMove(int crdX, int crdY){
        return (Math.abs(crdX - lastCrdX) <= 1 & Math.abs(crdY - lastCrdY) <= 1);
    }

    public ArrayList<Point> getLegalMoves(){
        ArrayList<Point> legalMoves = new ArrayList<>();
        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                if (i != j && isInBoard(crdPieceX+i, crdPieceY+i)) legalMoves.add(new Point(crdPieceX+i,crdPieceY+j));
            }
        }
        return legalMoves;
    }

    public void rock(){canRock = false;}
    public void unrock(){canRock = true;}

    public boolean canTake(int crdX, int crdY){
        return isNotMyCell(crdX, crdY) && isLegalMove(crdX,crdY);
    }
}



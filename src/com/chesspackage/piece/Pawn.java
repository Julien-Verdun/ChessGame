package com.chesspackage.piece;

import java.awt.*;
import java.util.ArrayList;

public class Pawn extends Piece {
    public Pawn(String color, int initialX) {
        super("Pawn", color, initialX, 1000, color.equals(WHITE) ? 0 : 200);
    }

    public boolean isLegalMove(int crdX, int crdY){
        if (isTop()) return crdX == lastCrdX & ((lastCrdY == 1 & crdY - lastCrdY == 2) || crdY - lastCrdY == 1);
        return crdX == lastCrdX & ((lastCrdY == 6 & lastCrdY - crdY == 2) || lastCrdY - crdY == 1);
    }

    public ArrayList<Point> getLegalMoves(){
        ArrayList<Point> legalMoves = new ArrayList<>();
        // legal moves for pawn depending on the side and on the
        if (isInBoard(crdPieceX-1, crdPieceY+(isTop()? 1 : -1)) && canTake(crdPieceX-1, crdPieceY+(isTop()? 1 : -1))) legalMoves.add(new Point(crdPieceX-1, crdPieceY+(isTop()? 1 : -1)));
        if (isInBoard(crdPieceX, crdPieceY+(isTop()? 1 : -1)) && isLegalMove(crdPieceX, crdPieceY+(isTop()? 1 : -1)) ) legalMoves.add(new Point(crdPieceX, crdPieceY+(isTop()? 1 : -1)));
        if (isInBoard(crdPieceX+1, crdPieceY+(isTop()? 1 : -1)) && canTake(crdPieceX-1, crdPieceY+(isTop()? 1 : -1))) legalMoves.add(new Point(crdPieceX+1, crdPieceY+(isTop()? 1 : -1)));
        if (isInBoard(crdPieceX, crdPieceY+(isTop()? 2 : -2)) && isLegalMove(crdPieceX, crdPieceY+(isTop()? 2 : -2))) legalMoves.add(new Point(crdPieceX, crdPieceY+(isTop()? 2 : -2)));
        return legalMoves;
    }

    public boolean canTake(int crdX, int crdY){
        if (Math.abs(crdX - crdPieceX) == 1) return isTop() ? crdY-crdPieceY == 1 : crdPieceY-crdY == 1;
        return false;
    }
}

package com.chesspackage;

import com.chesspackage.piece.Piece;
import java.awt.*;
import java.util.ArrayList;

import static com.chesspackage.BoardDimensions.*;

public class Board extends GameLogic {

    public Board(){
         pieces = new Pieces();
    }

    public void rock(ArrayList<Piece> towers, int idx){
        for (Piece piece : towers) {
            piece.rock();
        }
        selectedPiece.rock();
        Piece tower = towers.get(idx);
        if (tower.crdPieceX == 0){
            tower.moveCrd(2,tower.crdPieceY);
            releasePiece(1, selectedPiece.crdPieceY, true);
        } else if (tower.crdPieceX == 7) {
            tower.moveCrd(5,tower.crdPieceY);
            releasePiece(6, selectedPiece.crdPieceY, true);
        } else {System.out.println("Error with rock");}
    }

    public void returnLastPos(){
        if (selectedPiece != null) selectedPiece.returnLastPos();
    }

    public void moveCrdSelectedPiece(int crdX, int crdY){
        selectedPiece.moveCrd(crdX,crdY);
    }
    public void movePosSelectedPiece(int posX, int posY){
        if (selectedPiece != null) selectedPiece.movePos(posX, posY);
    }

    public void releaseSelectedPiece(){
        selectedPiece = null;
    }

    public void selectedPieceTryEat(int crdX, int crdY){
        ArrayList<Piece> listPiecesOnCell = getPiecesOnCell(crdX,crdY);
        System.out.println("Try EAT: ");
        for (Piece piece : listPiecesOnCell){
            piece.show();
        }
        System.out.println("Can take : " + selectedPiece.canTake(crdX,crdY));
        if (selectedPiece.canTake(crdX,crdY) & !listPiecesOnCell.isEmpty()){
            boolean canEat = false;
            for (int i = 0; i < listPiecesOnCell.size(); i ++) {
                Piece piece = listPiecesOnCell.get(i);
                if (!piece.color.equals(selectedPiece.color)) {
                    // eat a piece
                    piece.remove();
                    releasePiece(crdX, crdY);
                    canEat = true;
                    returnLastPos();
                    break;
                }
            }
            if (!canEat) System.out.println("Miam Miam La Piece");
        } else returnLastPos();
    }

    public void mouseRelease(int posX, int posY){
        Point point = pos2crd(posX, posY);
        int crdX = point.x;
        int crdY = point.y;
        System.out.println("COLOR TO PLAY : " + colorToPlay);
        if (canPlay()){
            if (selectedPiece.type.equals(KING) & !selectedPiece.isLegalMove(crdX,crdY) & selectedPiece.canRock){
                ArrayList<Piece> towers = pieces.getPieces(TOWER, colorToPlay);
                if (!towers.isEmpty()){
                    boolean hasRocked = false;
                    for(int i = 0; i < towers.size(); i++){
                        if (towers.get(i).crdPieceX == crdX & towers.get(i).crdPieceY == crdY){
                            // rock between king and tower
                            if (towers.get(i).canRock){
                                rock(towers,i);
                                hasRocked = true;
                            }
                            break;
                        }
                    }
                    if (!hasRocked) returnLastPos();
                } else returnLastPos();
            } else if (selectedPiece.isLegalMove(crdX, crdY) || selectedPiece.canTake(crdX, crdY)) {
                // get piece on cell and see if it is an opponent
                System.out.println("isPieceOnTheWay : " + isPieceOnTheWay(crdX, crdY));
                if (!isPieceOnTheWay(crdX, crdY)) {
                    System.out.println("isEmptyCell : " + isEmptyCell(crdX, crdY));
                    if (isEmptyCell(crdX, crdY)) {
                        System.out.println("isLegalMove : " + selectedPiece.isLegalMove(crdX, crdY));
                        if (selectedPiece.isLegalMove(crdX, crdY)) {
                            releasePiece(crdX, crdY);
                        } else returnLastPos();
                    } else selectedPieceTryEat(crdX, crdY);
                } else returnLastPos();
            } else returnLastPos();
        } else returnLastPos();
    }

    public void releasePiece(int crdX, int crdY){
        releasePiece(crdX, crdY, false);
    }

    public void releasePiece(int crdX, int crdY,  boolean isRock){
        moveCrdSelectedPiece(crdX,crdY);
        System.out.println("colorToPlay : " + colorToPlay);
        // if the ally king is threatened
        if (isCheck()){
            System.out.println("CHECK");
            // add a sub case check in case of rock
            if (isRock){
                ArrayList<Piece> towers = pieces.getPieces(TOWER, colorToPlay);
                // reset the canRock
                for (Piece tower : towers) {
                    tower.unrock();
                }
                selectedPiece.unrock();
                // find tower and returnLastPos
                for (Piece tower : towers) {
                    if ((tower.crdPieceX == 2 & selectedPiece.crdPieceX == 1) || (tower.crdPieceX == 5 & selectedPiece.crdPieceX == 6)) tower.returnLastPos();
                }
            }
            selectedPiece.returnLastPos();
        } else {
            System.out.println(colorToPlay + " king not threaten");
            saveMove();
            releaseSelectedPiece();
            nextTurn();
            if (isCheckMat()){
                System.out.println("CHECK MAT : " + colorToPlay + "lost the game and " + getOpponentColor() + " won the game");
            }
        }
    }

}

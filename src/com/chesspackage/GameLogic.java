package com.chesspackage;

import com.chesspackage.piece.Piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

import static com.chesspackage.BoardDimensions.*;

public class GameLogic {

    public String colorToPlay;
    public Pieces pieces;
    public Piece selectedPiece = null;

    public GameLogic(){
        this.colorToPlay = WHITE;
    }

    public Point pos2crd(int posX, int posY){
        return new Point(posX / WIDTHCELL, posY / HEIGHTCELL);
    }

    public void nextTurn(){
        colorToPlay = colorToPlay.equals(WHITE) ? BLACK : WHITE;
    }

    public String getOpponentColor(){
        return colorToPlay.equals(WHITE) ? BLACK : WHITE;
    }

    public Boolean canPlay(){
        return selectedPiece != null && colorToPlay.equals(selectedPiece.color);
    }

    public void findSelectedPiece(int selectedPosX, int selectedPosY) {
        Point selectedPoint = pos2crd(selectedPosX,selectedPosY);
        for (Map.Entry<Integer, Piece> pieceElt : pieces.pieces.entrySet()) {
            Piece piece = pieceElt.getValue();
            if (piece.isAlive && piece.crdPieceX == selectedPoint.x && piece.crdPieceY == selectedPoint.y){
                selectedPiece = piece;
                return;
            }
        }
    }

    public void saveMove(){
        for (Map.Entry<Integer, Piece> pieceElt : pieces.pieces.entrySet()) {
            pieceElt.getValue().saveMove();
        }
    }

    public boolean isEmptyCell(int crdX, int crdY){
        for (Map.Entry<Integer, Piece> pieceElt : pieces.pieces.entrySet()) {
            Piece piece = pieceElt.getValue();
            if (piece.isAlive && piece.crdPieceX == crdX && piece.crdPieceY == crdY && piece != selectedPiece) return false;
        }
        return true;
    }

    public ArrayList<Piece> getPieceOnCell(int crdX, int crdY){
        ArrayList<Piece> listPieces = new ArrayList<>();
        for (Map.Entry<Integer, Piece> pieceElt : pieces.pieces.entrySet()) {
            Piece piece = pieceElt.getValue();
            if (piece.isAlive && piece.crdPieceX == crdX && piece.crdPieceY == crdY && piece != selectedPiece){
                listPieces.add(piece);
            }
        }
        return listPieces;
    }

    public boolean isPieceOnCell(int crdX, int crdY){
        ArrayList<Piece> listPiecesOnCell = getPieceOnCell(crdX, crdY);
        return !listPiecesOnCell.isEmpty();
    }

    public boolean isPieceOnTheWay(int crdX, int crdY){
        // check between selected piece crd and future crd if there is a piece
        if (selectedPiece.type.equals("Knight") || selectedPiece.type.equals("King")) return false;
        if (crdX == selectedPiece.crdPieceX && crdY != selectedPiece.crdPieceY){
            for (int j = Math.min(crdY, selectedPiece.crdPieceY) + 1; j < Math.max(crdY, selectedPiece.crdPieceY); j++) {
                if (isPieceOnCell(crdX,j)) return true;
            }
        } else if (crdX != selectedPiece.crdPieceX && crdY == selectedPiece.crdPieceY){
            for (int i = Math.min(crdX, selectedPiece.crdPieceX) + 1; i < Math.max(crdX, selectedPiece.crdPieceX); i++) {
                if (isPieceOnCell(i, crdY)) return true;
            }
        } else if (crdX != selectedPiece.crdPieceX){
            if (Math.abs(selectedPiece.crdPieceX-crdX) == Math.abs(selectedPiece.crdPieceY-crdY)) {
                for (int i = 1; i < Math.abs(crdX-selectedPiece.crdPieceX); i++) {
                    if (isPieceOnCell(Math.min(crdX, selectedPiece.crdPieceX)+i, Math.min(crdY, selectedPiece.crdPieceY)+i)) return true;
                }
            }
        }
        return false;
    }

    public boolean isKingThreatened(String kingColor){
        // loop over all alive pieces of opposite color
        // and check if piece threat the position (piece canTake)
        Piece king = pieces.getPiece("King", kingColor);
        if (king != null){
            for (Map.Entry<Integer, Piece> pieceElt : pieces.pieces.entrySet()) {
                Piece piece = pieceElt.getValue();
                if (!kingColor.equals(piece.color) && piece.isAlive && piece.canTake(king.crdPieceX, king.crdPieceY)) return true;
            }
        } else {System.out.println("KING NOT FOUND");}
        return false;
    }

    public boolean isCheck(){return isKingThreatened(colorToPlay);}

    public boolean canKingEscape(Piece king){
        // check if the king can go on an empty cell close to him and not be threatened there
        // check if a cell is empty and king not threatened on that cell
        int kingCrdX = king.crdPieceX;
        int kingCrdY = king.crdPieceY;
        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                if ((i != 0 || j != 0) && 0 <= king.crdPieceX+i && king.crdPieceX+i  <= GRIDSIZE-1 && 0 <= king.crdPieceY+j && king.crdPieceY+j <= GRIDSIZE-1){
                    // check if a cell is empty and the king is not threatened on that cell
                    if (isEmptyCell(kingCrdX+i, kingCrdY+j)){
                        // move king
                        king.moveCrd(kingCrdX+i, kingCrdY+j);
                        // check that king is not threaten
                        if (!isKingThreatened(king.color))
                        {
                            king.moveCrd(kingCrdX, kingCrdY);
                            return true;
                        }
                    }
                    // check if a cell contains an enemy and the king is not threatened on that cell
                    else {
                        ArrayList<Piece> listPiecesOnCell = getPieceOnCell(kingCrdX+i, kingCrdY+j);
                        if (listPiecesOnCell.size() != 1){
                            System.out.println("BIZARRE bizarre");
                        } else {
                            Piece pieceOnCell = listPiecesOnCell.get(0);
                            if (!pieceOnCell.color.equals(king.color)){
                                // move king
                                king.moveCrd(kingCrdX+i, kingCrdY+j);
                                // check that king is not threaten
                                if (!isKingThreatened(king.color))
                                {
                                    king.moveCrd(kingCrdX, kingCrdY);
                                    return true;
                                }
                            } //else {ally on the cell}
                        }

                    }

                }
            }
        }
        king.moveCrd(kingCrdX, kingCrdY);
        return false;
    }

    public boolean canPieceProtectKing(Piece king){
        // check if a piece can move and protect the king from the opponent threat
        for (Map.Entry<Integer, Piece> pieceElt : pieces.pieces.entrySet()) {
            Piece piece = pieceElt.getValue();
            if (!king.color.equals(piece.color) && piece.isAlive){
                ArrayList<Point> legalMoves = piece.getLegalMoves();
                // check if one of the legal moves from the piece is legal, i.e. the cell is empty or if occupy by an enemy and piece can take
                for (Point cell: legalMoves){
                    if (isEmptyCell(cell.x, cell.y) || (getPieceOnCell(cell.x, cell.y).get(0).color.equals(piece.color) && piece.canTake(cell.x, cell.y))){
                        // if in this position, the king is not more threatened, true
                        piece.moveCrd(cell.x, cell.y);
                        // check that king is not threaten
                        if (!isKingThreatened(king.color))
                        {
                            piece.moveCrd(piece.lastCrdX, piece.lastCrdY);
                            return true;
                        }
                        // else continue
                    }
                }
            }
        }
        return false;
    }

    public boolean isMat(){
        // check that king can move or that an ally piece can protect the king
        // colorToPlay is the color of the player that will play
        Piece king = pieces.getPiece("King", colorToPlay);
        if (canKingEscape(king)){
            return false;
        } else {
            return !canPieceProtectKing(king);
        }
    }

    public boolean isCheckMat(){
        return isCheck() && isMat();
    }

}

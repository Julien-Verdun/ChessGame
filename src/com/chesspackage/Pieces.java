package com.chesspackage;

import com.chesspackage.piece.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.chesspackage.BoardDimensions.*;


public class Pieces {
    HashMap<Integer, Piece> pieces = new HashMap<>();
    final HashMap<String, int[]> initPosPieces = new HashMap<>();

    public Pieces() {
        this.initPos();
        this.initPieces();
    }

    void initPos(){
        initPosPieces.put(BISHOP, new int[]{2, 5});
        initPosPieces.put(KNIGHT, new int[]{1, 6});
        initPosPieces.put(TOWER, new int[]{0, 7});
        initPosPieces.put(PAWN, new int[]{0,1,2,3,4,5,6,7});
    }

    Piece getNewInstance(String className, String color, int initialX) {
        return switch (className) {
            case BISHOP -> new Bishop(color, initialX);
            case KNIGHT -> new Knight(color, initialX);
            case TOWER -> new Tower(color, initialX);
            case PAWN -> new Pawn(color, initialX);
            default -> null;
        };
    }

    void initPieces(){
        pieces = new HashMap<>();

        pieces.put(1, new Queen(WHITE));
        pieces.put(2, new Queen(BLACK));
        pieces.put(3, new King(WHITE));
        pieces.put(4, new King(BLACK));
        int cnt = 5;
        for (Map.Entry<String, int[]> pieceType : initPosPieces.entrySet()) {
            for (String color : colors){
                for (int pos : pieceType.getValue()) {
                    pieces.put(cnt, getNewInstance(pieceType.getKey(), color, pos) );
                    // System.out.println("Legal moves " + pieceType.getKey() + " " + color + " " + pos + " " + pieces.get(cnt).getLegalMoves().toString());
                    cnt++;
                }
            }
        }
    }

    void restart(){
        for (Map.Entry<Integer, Piece> pieceElt : pieces.entrySet()) {
            Piece piece = pieceElt.getValue();
            piece.restart();
        }
    }

    void revert(){
        // detect if it was a rock with the king x move diff and reset canRock for tower
        if (pieces.get(0).listPos.size()>1) {
            for (String color: colors){
                Piece king = getPiece(KING,color);
                if (Math.abs(king.listPos.get(king.listPos.size()-1).x - king.listPos.get(king.listPos.size()-2).x) > 1){
                    king.unrock();
                    ArrayList<Piece> towers = getPieces(TOWER,color);
                    for (Piece tower: towers){
                        tower.unrock();
                    }
                }
            }
        }

        for (Map.Entry<Integer, Piece> pieceElt : pieces.entrySet()) {
            Piece piece = pieceElt.getValue();
            piece.revert();
        }
    }

    public Piece getPiece(String type, String color){
        for (Map.Entry<Integer, Piece> pieceElt : pieces.entrySet()) {
            Piece piece = pieceElt.getValue();
            if (piece.isAlive & piece.type.equals(type) & piece.color.equals(color)) return piece;
        }
        return null;
    }

    public ArrayList<Piece> getPieces(String type, String color){
        ArrayList<Piece> allPieces = new ArrayList<>();
        for (Map.Entry<Integer, Piece> pieceElt : pieces.entrySet()) {
            Piece piece = pieceElt.getValue();
            if (piece.isAlive & piece.type.equals(type) & piece.color.equals(color)) allPieces.add(piece);
        }
        return allPieces;
    }
}

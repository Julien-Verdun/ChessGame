package com.chesspackage;

import com.chesspackage.piece.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Pieces {
    HashMap<Integer, Piece> pieces = new HashMap<>();
    HashMap<String, int[]> initPosPieces = new HashMap<>();

    public Pieces() {
        this.initPos();
        this.initPieces();
    }

    void initPos(){
        initPosPieces.put("Bishop", new int[]{2, 5});
        initPosPieces.put("Knight", new int[]{1, 6});
        initPosPieces.put("Tower", new int[]{0, 7});
        initPosPieces.put("Pawn", new int[]{0,1,2,3,4,5,6,7});
    }

    Piece getNewInstance(String className, String color, int initialX) {
        return switch (className) {
            case "Bishop" -> new Bishop(color, initialX);
            case "Knight" -> new Knight(color, initialX);
            case "Tower" -> new Tower(color, initialX);
            case "Pawn" -> new Pawn(color, initialX);
            default -> null;
        };
    }

    void initPieces(){
        pieces = new HashMap<>();

        pieces.put(1, new Queen("white"));
        pieces.put(2, new Queen("black"));
        pieces.put(3, new King("white"));
        pieces.put(4, new King("black"));
        int cnt = 5;
        for (Map.Entry<String, int[]> pieceType : initPosPieces.entrySet()) {
            for (String color : new String[]{"white", "black"}){
                for (int pos : pieceType.getValue()) {
                    pieces.put(cnt, getNewInstance(pieceType.getKey(), color, pos) );
                    //System.out.println("Legal moves " + pieceType.getKey() + " " + color + " " + pos + " " + pieces.get(cnt).getLegalMoves().toString());
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
        for (Map.Entry<Integer, Piece> pieceElt : pieces.entrySet()) {
            Piece piece = pieceElt.getValue();
            piece.revert();
        }
    }

    public Piece getPiece(String type, String color){
        for (Map.Entry<Integer, Piece> pieceElt : pieces.entrySet()) {
            Piece piece = pieceElt.getValue();
            if (piece.isAlive && piece.type.equals(type) && piece.color.equals(color)) return piece;
        }
        return null;
    }

    public ArrayList<Piece> getPieces(String type, String color){
        ArrayList<Piece> allPieces = new ArrayList<>();
        for (Map.Entry<Integer, Piece> pieceElt : pieces.entrySet()) {
            Piece piece = pieceElt.getValue();
            if (piece.isAlive && piece.type.equals(type) && piece.color.equals(color)) allPieces.add(piece);
        }
        return allPieces;
    }
}

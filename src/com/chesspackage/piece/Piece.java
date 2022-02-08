package com.chesspackage.piece;

import com.chesspackage.BoardDimensions;

import java.awt.*;
import java.util.ArrayList;


public class Piece extends BoardDimensions  {
    public final String type;
    public final String color;
    public int crdPieceX;
    public int crdPieceY;
    public int posPieceX;
    public int posPieceY;
    public int lastCrdX;
    public int lastCrdY;
    public final int iniPieceX;
    public final int iniPieceY;
    public final int imagePieceX;
    public final int imagePieceY;
    public final ArrayList<Point> listPos;
    public boolean isAlive;
    public int deathTurn;
    public boolean canRock = false;

    public Piece(String type, String color, int iniPieceX, int imagePieceX, int imagePieceY) {
        this.type = type;
        this.color = color;
        this.iniPieceX = iniPieceX;
        this.iniPieceY = type.equals(PAWN) ? (isTop() ? 1 : 6) : (isTop() ? 0 : 7);
        this.imagePieceX = imagePieceX;
        this.imagePieceY = imagePieceY;
        this.listPos = new ArrayList<>();
        this.restart();
    }

    public void show(){
        System.out.println("Type: " + type + ", color: "+ color + ", coords: ("+ crdPieceX + ","+ crdPieceY + ")");
    }

    public boolean isInBoard(int crd){
        return crd>= 0 & crd<=GRIDSIZE-1;
    }

    public boolean isInBoard(int crdX, int crdY){
        return isInBoard(crdX) & isInBoard(crdY);
    }

    public Point crd2Pos(int crdX, int crdY){
        return new Point(crdX*WIDTHCELL,crdY*HEIGHTCELL);
    }

    public void convertCrd2Pos(){
        Point posPiece = this.crd2Pos(crdPieceX, crdPieceY);
        this.posPieceX = posPiece.x;
        this.posPieceY = posPiece.y;
    }

    public void moveCrd(int crdX, int crdY){
        crdPieceX = crdX;
        crdPieceY = crdY;
        convertCrd2Pos();
        if (canRock & (type.equals(KING) || type.equals(TOWER))){
            canRock = false;
        }
    }

    public void movePos(int posX, int posY){
        posPieceX = posX;
        posPieceY = posY;
    }

    public void saveMove(){
        lastCrdX = listPos.get(listPos.size() - 1).x;
        lastCrdY = listPos.get(listPos.size() - 1).y;
        listPos.add(new Point(crdPieceX, crdPieceY));
    }

    public void restart(){
        crdPieceX = iniPieceX;
        crdPieceY = iniPieceY;
        convertCrd2Pos();
        lastCrdX = iniPieceX;
        lastCrdY = iniPieceY;
        listPos.clear();
        listPos.add(new Point(iniPieceX, iniPieceY));
        isAlive = true;
        deathTurn = 0;
        unrock();
    }

    public void revert(){
        if (listPos.size()>1){
            listPos.remove(listPos.size()-1);
            crdPieceX = listPos.get(listPos.size() - 1).x;
            crdPieceY = listPos.get(listPos.size() - 1).y;
            convertCrd2Pos();
            lastCrdX = listPos.get(listPos.size() - 1).x;
            lastCrdY = listPos.get(listPos.size() - 1).y;
            if (!isAlive & listPos.size() == deathTurn) {
                isAlive = true;
                deathTurn = 0;
            }
        }
    }

    public void returnLastPos(){
        crdPieceX = listPos.get(listPos.size() - 1).x;
        crdPieceY = listPos.get(listPos.size() - 1).y;
        convertCrd2Pos();
    }

    public void remove(){
        isAlive = false;
        deathTurn = listPos.size();
    }

    public boolean isTop(){
        return (WHITESIDE == 0 & color.equals(WHITE)) || (WHITESIDE == 1 & color.equals(BLACK));
    }

    public boolean isLegalMove(int crdX, int crdY) {return false;}

    public ArrayList<Point> getLegalMoves(){return new ArrayList<>();}

    public boolean canTake(int crdX, int crdY){return true;}

    public boolean isNotMyCell(int crdX, int crdY){
        return crdX != crdPieceX || crdY != crdPieceY;
    }

    public void rock() {}
    public void unrock() {}
}



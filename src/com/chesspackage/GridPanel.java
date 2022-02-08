package com.chesspackage;
import com.chesspackage.piece.Piece;
import static com.chesspackage.BoardDimensions.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class GridPanel extends JPanel implements MouseListener, MouseMotionListener {
    BufferedImage image;
    final Board board;

    GridPanel(Board board) {
        this.board = board;
        try {
            this.image = ImageIO.read(new File("./resources/chessPieces.png"));
        } catch (IOException e){
            this.image = null;
            e.printStackTrace();
        }
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    protected void paintBackground(Graphics g){
        for (int i = 0; i < GRIDSIZE; i ++)
            for (int j = 0; j < GRIDSIZE; j++) {
                if ((i + j) % 2 == 0) g.setColor(Color.GREEN);
                else g.setColor(Color.WHITE);
                g.fillRect(PADDINGX + CELLSIZE * i, PADDINGY + CELLSIZE * j, CELLSIZE, CELLSIZE);
                g.setColor(Color.BLACK);
                g.drawRect(PADDINGX + CELLSIZE * i, PADDINGY + CELLSIZE * j, CELLSIZE, CELLSIZE);
            }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBackground(g);
        for (Map.Entry<Integer, Piece> pieceElt : board.pieces.pieces.entrySet()) {
            Piece piece = pieceElt.getValue();
            if (piece.isAlive){
                BufferedImage subImage = image.getSubimage(piece.imagePieceX, piece.imagePieceY, 200, 200);
                g.drawImage(subImage, PADDINGX+piece.posPieceX, PADDINGY+piece.posPieceY,CELLSIZE,CELLSIZE, null);
            }
        }
    }

    public void redraw(){
        revalidate();
        repaint();
    }

    private Point getRealMousePos(MouseEvent e){
        return new Point(e.getX() - PADDINGX, e.getY() - PADDINGY);
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {
        System.out.println("            --------------------");
        System.out.println("mousePressed");
        Point mousePos = getRealMousePos(e);
        board.findSelectedPiece(mousePos.x, mousePos.y);
    }

    public void mouseReleased(MouseEvent e) {
        System.out.println("mouseReleased");
        Point mousePos = getRealMousePos(e);
        board.mouseRelease(mousePos.x, mousePos.y);
        redraw();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point mousePos = getRealMousePos(e);
        board.movePosSelectedPiece(mousePos.x-CELLSIZE/2, mousePos.y-CELLSIZE/2);
        redraw();
    }

    @Override
    public void mouseMoved(MouseEvent e) {}
}

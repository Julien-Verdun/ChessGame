package com.chesspackage;

import javax.swing.*;
import java.awt.*;

public class ChessGame  {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(ChessGame::createAndShowChessGame);
    }

    private static void createAndShowChessGame() {
        JFrame frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);

        Board board = new Board();
        GridPanel gridPanel = new GridPanel(board);

        //Creating the panel at bottom and adding components
        JPanel panel = new JPanel(); // the panel is not visible in output
        JButton revert = new JButton("Revert");
        JButton restart = new JButton("Restart");

        restart.addActionListener(e -> {
            board.pieces.restart();
            gridPanel.redraw();
        });

        revert.addActionListener(e -> {
            board.pieces.revert();
            gridPanel.redraw();
        });

        panel.add(revert);
        panel.add(restart);

        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.CENTER,gridPanel);
        frame.setVisible(true);
    }

}

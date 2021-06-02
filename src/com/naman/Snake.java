package com.naman;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Snake extends JFrame {

    public Snake() {

        completeGame();
    }

    private void completeGame() {

        add(new Board());

        setResizable(false);
        pack();

        setTitle("Snake Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame ex = new Snake();
            ex.setVisible(true);
        });
    }
}

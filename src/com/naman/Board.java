package com.naman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private final int WidthB = 300;
    private final int HeightB = 300;
    private final int DotSize = 10;
    private final int AllDots = 900;
    private final int RandomPos = 29;
    private final int Delay = 140;
    private int point = 0;

    private final int x[] = new int[AllDots];
    private final int y[] = new int[AllDots];

    private int dots;
    private int xpos;
    private int ypos;

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean game = true;

    private Timer timer;
    private Image body;
    private Image apple;
    private Image head;

    public Board() {

        BoardGame();
    }

    private void BoardGame() {

        addKeyListener(new Whichkey());
        setBackground(Color.white);
        setFocusable(true);

        setPreferredSize(new Dimension(WidthB, HeightB));
        loadImages();
        initGame();
    }

    private void loadImages() {

        ImageIcon iib = new ImageIcon("src/resources/dot.png");
        body = iib.getImage();

        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/resources/head.png");
        head = iih.getImage();
    }

    private void initGame() {

        dots = 2;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

        nextApple();

        timer = new Timer(Delay, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        if (game) {

            g.drawImage(apple, xpos, ypos, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(body, x[z], y[z], this);
                }
            }


        } else {

            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);
        String msg1 = "Your points : "+point;
        g.setColor(Color.black);
        g.setFont(small);
        g.drawString(msg, (WidthB - metr.stringWidth(msg)) / 2, HeightB / 2);
        g.drawString(msg1,(WidthB - metr.stringWidth(msg)-24)/2, (HeightB/2)+30);
    }

    private void checkApple() {

        if ((x[0] == xpos) && (y[0] == ypos)) {

            dots++;
            point++;
            nextApple();
        }
    }

    private void snakemove() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (left) {
            x[0] -= DotSize;
        }

        if (right) {
            x[0] += DotSize;
        }

        if (up) {
            y[0] -= DotSize;
        }

        if (down) {
            y[0] += DotSize;
        }
    }

    private void checkCollision() {

        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                game = false;
            }
        }

        if (y[0] >= HeightB) {
            game = false;
        }

        if (y[0] < 0) {
            game = false;
        }

        if (x[0] >= WidthB) {
            game = false;
        }

        if (x[0] < 0) {
            game = false;
        }

        if (!game) {
            timer.stop();
        }
    }

    private void nextApple() {

        int r = (int) (Math.random() * RandomPos);
        xpos = ((r * DotSize));

        r = (int) (Math.random() * RandomPos);
        ypos = ((r * DotSize));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (game) {

            checkApple();
            checkCollision();
            snakemove();
        }

        repaint();
    }

    private class Whichkey extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!right)) {
                left = true;
                up = false;
                down = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!left)) {
                right = true;
                up = false;
                down = false;
            }

            if ((key == KeyEvent.VK_UP) && (!down)) {
                up = true;
                right = false;
                left = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!up)) {
                down = true;
                right = false;
                left = false;
            }
        }
    }
}

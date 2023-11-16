package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SquaresGame extends JFrame {

    static final int windowWidth = 400;
    static final int windowHeight = 500;

    static final int panelWidth = 400;
    static final int panelHeight = 500;
    static final int squareSize = 30;

    //Parameters of the game to modify if needed
    static final int creationFrequency = 750; // in milliseconds
    static final int fallingSpeed = 15;
    static final int gameDuration = 60; // 1-minute
    static final double requiredWinningScore = 0.55;  // 55%


    private JPanel gamePanel;
    private JLabel scoreLabel;
    private Timer SquaresTimer;
    private Timer gameTimer;
    private List<Square> squares;

    private int totalSquares;
    private int missedSquares;
    private int clickedSquares;
    private boolean gameOver;

    public SquaresGame() {
        setTitle("Squares Game");
        setSize(windowWidth, windowHeight);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        gamePanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Square square : squares) {
                    if (square.isVisible()) {
                        g.setColor(square.getColor());
                        g.fillRect(square.getX(), square.getY(), squareSize, squareSize);
                    }
                }
            }
        };

        gamePanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        gamePanel.setBackground(Color.ORANGE);

        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });

        scoreLabel = new JLabel(" ");
        scoreLabel.setFont(new Font("Georgia", Font.BOLD, 22));

        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(gamePanel, BorderLayout.CENTER);
        add(scoreLabel, BorderLayout.SOUTH);

        squares = new ArrayList<>();
        totalSquares = 0;
        clickedSquares = 0;
        missedSquares = 0;
        gameOver = false;

        SquaresTimer = new Timer(creationFrequency, e -> {
            createNewSquare();
            updateSquarePositions();
            updateScoreLabel();
            gamePanel.repaint();
            checkGameOver();
        });

        gameTimer = new Timer(gameDuration*1000, e -> stopGame());
    }

    private void createNewSquare() {
        Random random = new Random();
        int x = random.nextInt(panelWidth - squareSize);
        int y = 0;
        Color color = getRandomColor();
        Square square = new Square(x, y, color);
        squares.add(square);
        totalSquares++;
    }


    private Color getRandomColor() {
        Random random = new Random();
        int blue = random.nextInt(256);
        return new Color(0, 0, blue);
    }


    private void updateSquarePositions() {
        Iterator<Square> iterator = squares.iterator();
        while (iterator.hasNext()) {
            Square square = iterator.next();
            square.setY(square.getY() + fallingSpeed);
            if (square.getY() >= panelHeight) {
                iterator.remove();
                missedSquares++;
            }
        }
    }


    private void handleMouseClick(int x, int y) {
        Iterator<Square> iterator = squares.iterator();
        while (iterator.hasNext()) {
            Square square = iterator.next();
            if (square.isVisible() && square.exists(x, y)) {
                iterator.remove();
                clickedSquares++;
                break;
            }
        }
    }


    private void updateScoreLabel() {
        double score = (double)(totalSquares - missedSquares) / totalSquares;
        int scorePercentage = (int)(score * 100);
        scoreLabel.setText("Current score: " + scorePercentage + "%");
    }


    private void checkGameOver() {
        if (gameTimer.isRunning() && !gameOver && missedSquares >= totalSquares) {
            gameOver = true;
            SquaresTimer.stop();
            gameTimer.stop();
        }
    }


    private void stopGame() {
        gameOver = true;
        SquaresTimer.stop();
        gameTimer.stop();
        double score = (double) clickedSquares / totalSquares;
        //int scorePercentage = (int) (score * 100);
        String result;
        if (score >= requiredWinningScore) {
            result = "Congratulations! You Won!";
        } else {
            result = "You Lost!";
        }
        showPopUpWindow(result);
        gamePanel.removeMouseListener(gamePanel.getMouseListeners()[0]);
    }


    private void showPopUpWindow(String message) {
        String windowMessage = "Game Over. \n" + message;
        JOptionPane.showMessageDialog(this, windowMessage, "Result", JOptionPane.INFORMATION_MESSAGE);
    }


    public void startGame() {
        SquaresTimer.start();
        gameTimer.start();
        setVisible(true);
    }


    private class Square {
        private int x;
        private int y;
        private Color color;
        private boolean visible;

        public Square(int x, int y, Color color) {
            this.x = x;
            this.y = y;
            this.color = color;
            this.visible = true;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public Color getColor() {
            return color;
        }

        public boolean isVisible() {
            return visible;
        }

        public boolean exists(int x, int y) {
            return x >= this.x && x <= this.x + squareSize && y >= this.y && y <= this.y + squareSize;
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SquaresGame game = new SquaresGame();
            game.startGame();
        });
    }
}

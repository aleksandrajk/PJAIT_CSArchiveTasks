# Java Squares Game

## Overview
The Java Squares Game is a simple yet engaging interactive game where players aim to eliminate falling squares by clicking on them before they reach the bottom edge of the panel. The game dynamically updates the score based on the fraction of squares eliminated relative to the total number created.

## Gameplay
* __Objective:__ Click on falling squares to eliminate them and maintain a high elimination rate.
* __Scoring:__ The score is displayed as a fraction, representing the percentage of squares eliminated.
* __Winning Condition:__ To win, maintain a score higher than the prescribed winning threshold until the end of the game.


## Features
Randomly generated falling squares.
Real-time score display.
Game over message with outcome (win/lose).


## How to Play
1. Run the Java program.
2. A window will display with a panel and a label.
3. Click on falling squares to eliminate them and increase your score.
4. Keep an eye on the score displayed on the label.
5. The game concludes after the prescribed time; a win or lose message will be shown.


## Setting Parameters
Adjust the following parameters in the code to customize the game:
```
//Parameters of the game to modify if needed
static final int creationFrequency = 750; // Squares creation frequency in milliseconds
static final int fallingSpeed = 15; // // Speed of falling squares
static final int gameDuration = 60; // Game duration in seconds
static final double requiredWinningScore = 0.55;  // Percentage of squares to win the game - 55%

```


## Dependencies
Java Swing library for GUI components.


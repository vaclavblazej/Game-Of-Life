# Conway's game of life

As a demonstration of a simple simulation with **lwjgl**.

## Description
The game of life is a simulation on a grid divided into cells.
The simulation happens in rounds and state of each cell is devised from states of it's neighbours from the previous round.

What will be implemented:
* stateful grid
* mouse controls
* clear, pause, play controls

What will be omitted:
* settings

## Build

compile with maven

    mvn clean package
    
run as jar file

    java -jar target/gameoflife.jar

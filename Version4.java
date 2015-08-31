/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myVersions.version4;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.ArrayList;

/**
 *
 * @author Bancho
 * Probability Density Shooting
 * 
 */
public class Version4 implements BattleshipsPlayer {

    private Shooting shoot;
    private Placement placement;
    private int currentRound;
    
    public Version4(){
        placement = new Placement();
    }

    @Override
    public void startMatch(int rounds) {
        //Do nothing
    }

    @Override
    public void startRound(int round) {
        shoot = new Shooting(round);
        currentRound = round;
    }

    @Override
    public void placeShips(Fleet fleet, Board board) {
        placement.resetTakenCells();
        
        for (int i = 0; i < fleet.getNumberOfShips(); i++) {
            Ship s = fleet.getShip(i);
            placement.takeStackOverflowPrecautions();
            placement.placeShip(s, board);

        }
    }

    @Override
    public void incoming(Position pos) {
        //Do nothing
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        int coords[] = shoot.locateBestFireCoords();
        return new Position(coords[0], coords[1]);
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        ArrayList<Integer> enemyShipSizes = new ArrayList<Integer>();
        for (int i = 0; i < enemyShips.getNumberOfShips(); i++) {
            Ship ship = enemyShips.getShip(i);
            enemyShipSizes.add(ship.size());
        }
        
        shoot.adjustMapInfo(hit, enemyShipSizes);
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {
        //Do nothing
    }

    @Override
    public void endMatch(int won, int lost, int draw) {
        //Do nothing
        System.out.println("hurry, down the chimney tonight");
    }

    

}

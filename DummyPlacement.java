/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myVersions.version4;

import battleship.interfaces.Board;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;

/**
 *
 * @author Bancho
 * works best vs. SystematicShotPlayer
 * 
 */
public class DummyPlacement {
    
    private boolean one3sizedShipPlacedAlready;
    
    public DummyPlacement(){
        one3sizedShipPlacedAlready = false;
    }
    
    public void resetTakenCells(){
        one3sizedShipPlacedAlready = false;
    }
    
    public void placeShip(Ship s, Board board){
        if (s.size() == 2) {
            board.placeShip(new Position(5, 8), s, true);
        }
        if (s.size() == 3) {
            if (one3sizedShipPlacedAlready) {
                board.placeShip(new Position(7, 7), s, true);
            }
            else{
                board.placeShip(new Position(6, 7), s, true);
                one3sizedShipPlacedAlready = true;
            }
        }
        if (s.size() == 4) {
            board.placeShip(new Position(8, 6), s, true);
        }
        if (s.size() == 5) {
            board.placeShip(new Position(9, 5), s, true);
        }
    }
    
}

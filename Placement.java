/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myVersions.version4;

import battleship.interfaces.Board;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.Random;

/**
 *
 * @author Bancho
 */
public class Placement {

    private boolean[][] takenCells;
    private Random rand;
    private int sizeX;
    private int sizeY;
    private int x;
    private int y;
    private boolean vertical;
    private int recursionTimes;
    private final int RECURSION_LIMIT;

    public Placement() {
        takenCells = new boolean[10][10];
        rand = new Random();
        RECURSION_LIMIT = 1000;
    }
    
    public void resetTakenCells(){
        takenCells = new boolean[10][10];
    }
    
    public void takeStackOverflowPrecautions(){
        recursionTimes = 0;
    }

    public void placeShip(Ship s, Board board) {
        sizeX = board.sizeX();
        sizeY = board.sizeY();
        vertical = rand.nextBoolean();

        if (vertical) {
            x = rand.nextInt(sizeX);
            y = rand.nextInt(sizeY - s.size() + 1);
        } else {
            x = rand.nextInt(sizeX - s.size() + 1);
            y = rand.nextInt(sizeY);
        }

        if (willCollideOrTouch(s.size()) == false) {
            board.placeShip(new Position(x, y), s, vertical);
            adjustTakenCells(s.size());
            return;
        }
        
        
        //if it's gotten to this point, it means the method will call itself again
        //so we take precautions against that happening over and over again and causing a StackOverflow
        //even if we risk placing the current ship on top of another
        recursionTimes++;
        
        if (recursionTimes > RECURSION_LIMIT) {
            x = 0;
            y = 0;
            vertical = true;
            board.placeShip(new Position(x, y), s, vertical);
            adjustTakenCells(s.size());
            return;
        }
        
        placeShip(s, board);
    }

    
    
    private boolean willCollideOrTouch(int size) {
        boolean willCollideOrTouch = false;

        if (vertical) {
            for (int tempY = y; tempY < y + size; tempY++) {
                if (takenCells[x][tempY] == true) {
                    willCollideOrTouch = true;
                }
            }
        }
        else{
            for (int tempX = x; tempX < x + size; tempX++) {
                if (takenCells[tempX][y] == true) {
                    willCollideOrTouch = true;
                }
            }
        }
        
        return willCollideOrTouch;
    }


    
    private void adjustTakenCells(int size) {
        try{
            if (vertical) {
                
                if (y == 0) {
                    if (x == 0) {
                        for (int tempY = y; tempY < y + size + 1; tempY++){
                            takenCells[x][tempY] = true;
                            takenCells[x+1][tempY] = true;
                        }
                        
                    }
                    else if (x == 9){
                        for (int tempY = y; tempY < y + size + 1; tempY++){
                            takenCells[x][tempY] = true;
                            takenCells[x-1][tempY] = true;
                        }
                        
                    }
                    else{
                        for (int tempY = y; tempY < y + size + 1; tempY++){
                            takenCells[x][tempY] = true;
                            takenCells[x-1][tempY] = true;
                            takenCells[x+1][tempY] = true;
                        }
                        
                    }
                }
                
                else if (y + size == 9) {
                    if (x == 0){
                        for (int tempY = y - 1; tempY < y + size; tempY++){
                            takenCells[x][tempY] = true;
                            takenCells[x+1][tempY] = true;
                        }
                        
                    }
                    else if (x == 9){
                        for (int tempY = y - 1; tempY < y + size; tempY++){
                            takenCells[x][tempY] = true;
                            takenCells[x-1][tempY] = true;
                        }
                        
                    }
                    else{
                        for (int tempY = y - 1; tempY < y + size; tempY++){
                            takenCells[x][tempY] = true;
                            takenCells[x-1][tempY] = true;
                            takenCells[x+1][tempY] = true;
                        }
                        
                    }

                }
                
                else{
                    if ( x == 0){
                        for (int tempY = y - 1; tempY < y + size + 1; tempY++){
                            takenCells[x][tempY] = true;
                            takenCells[x+1][tempY] = true;
                        }
                    }
                    else if (x == 9){
                        for (int tempY = y - 1; tempY < y + size + 1; tempY++){
                            takenCells[x][tempY] = true;
                            takenCells[x-1][tempY] = true;
                        }
                    }
                    else{
                        for (int tempY = y - 1; tempY < y + size + 1; tempY++){
                            takenCells[x][tempY] = true;
                            takenCells[x-1][tempY] = true;
                            takenCells[x+1][tempY] = true;
                        }
                    }
                }
                    
            }
            else{
                if (x == 0) {
                    if (y == 0) {
                        for (int tempX = x; tempX < x + size + 1; tempX++){
                            takenCells[tempX][y] = true;
                            takenCells[tempX][y+1] = true;
                        }
                        
                    }
                    else if (y == 9){
                        for (int tempX = x; tempX < x + size + 1; tempX++){
                            takenCells[tempX][y] = true;
                            takenCells[tempX][y-1] = true;
                        }
                        
                    }
                    else{
                        for (int tempX = x; tempX < x + size + 1; tempX++){
                            takenCells[tempX][y] = true;
                            takenCells[tempX][y-1] = true;
                            takenCells[tempX][y+1] = true;
                        }
                        
                    }
                }
                else if (x + size == 9){
                    if (y == 0) {
                        for (int tempX = x - 1; tempX < x + size; tempX++){
                            takenCells[tempX][y] = true;
                            takenCells[tempX][y+1] = true;
                        }
                        
                    }
                    else if (y == 9){
                        for (int tempX = x - 1; tempX < x + size; tempX++){
                            takenCells[tempX][y] = true;
                            takenCells[tempX][y-1] = true;
                        }
                        
                    }
                    else{
                        for (int tempX = x - 1; tempX < x + size; tempX++){
                            takenCells[tempX][y] = true;
                            takenCells[tempX][y-1] = true;
                            takenCells[tempX][y+1] = true;
                        }
                        
                    }
                }
                else{
                    if (y == 0) {
                        for (int tempX = x - 1; tempX < x + size + 1; tempX++){
                            takenCells[tempX][y] = true;
                            takenCells[tempX][y+1] = true;
                        }
                        
                    }
                    else if(y == 9){
                        for (int tempX = x - 1; tempX < x + size + 1; tempX++){
                            takenCells[tempX][y] = true;
                            takenCells[tempX][y-1] = true;
                        }
                        
                    }
                    else{
                        for (int tempX = x - 1; tempX < x + size + 1; tempX++){
                            takenCells[tempX][y] = true;
                            takenCells[tempX][y-1] = true;
                            takenCells[tempX][y+1] = true;
                        }
                    }
                }
            }
        }
        catch(IndexOutOfBoundsException ex){
            //Do nothing
        }
    }
}

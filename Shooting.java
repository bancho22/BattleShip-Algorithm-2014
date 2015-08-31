/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myVersions.version4;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Bancho
 */
public class Shooting {
    
    private int[][] probs;
    private int[][] mapInfo;
    private ArrayList<Integer> enemyShips;
    private int lastFiredX;
    private int lastFiredY;
    private final int BOARD_LENGTH;
    private final int A_SHIT_TON_OF_PRBABILITY;
    private int currentRound;
    int count;
    private Random rand;
    
    
    public Shooting(int round){
        mapInfo = new int[10][10];
        //mapInfo key:
        //unvisited space - 0
        //wounded ships - 1
        //misses - 2
        //sunk ships - 3
        
        BOARD_LENGTH = 10;
        A_SHIT_TON_OF_PRBABILITY = 100;
        
        enemyShips = new ArrayList<Integer>();
        enemyShips.add(2);
        enemyShips.add(3);
        enemyShips.add(3);
        enemyShips.add(4);
        enemyShips.add(5);
        
        currentRound = round;
        count = 0;
        rand = new Random();
    }
    
    
    private void resetProbs(){
        probs = new int[10][10];
    }
    
    
    
    
    public int[] locateBestFireCoords(){
        resetProbs();
        count++;
        
        
        //making sure we don't always shoot in the centre at the beginning
        //cuz a lot of players have taken precautions against this
        if (count < 3) {
            probs[rand.nextInt(10)][rand.nextInt(10)] += A_SHIT_TON_OF_PRBABILITY;
        }
        
        for (int sizeOfEnemyShip : enemyShips) {
            checkHorizontalFits(sizeOfEnemyShip);
            checkVerticalFits(sizeOfEnemyShip);
        }
        
        int[] luckyCell = findLuckyCell();
        
        lastFiredX = luckyCell[0];
        lastFiredY = luckyCell[1];
        
        return luckyCell;
    }

    
    
    private void checkHorizontalFits(int sizeOfEnemyShip) {
        int y = 0;
        
        while (y <= 9){
            for (int x = 0; x <= BOARD_LENGTH - sizeOfEnemyShip; x++){
                
                boolean fits = true;
                for (int tempX = x; tempX < x + sizeOfEnemyShip; tempX++) {
                    if (mapInfo[tempX][y] == 2 || mapInfo[tempX][y] == 3) {
                        fits = false;
                    }
                }
                if (fits) {
                    for (int tempX = x; tempX < x + sizeOfEnemyShip; tempX++){
                        if (mapInfo[tempX][y] == 0) {
                            probs[tempX][y] += 1;
                        }
                    }
                    
                    boolean containsWoundedPart = false;
                    for (int tempX = x; tempX < x + sizeOfEnemyShip; tempX++){
                        if (mapInfo[tempX][y] == 1) {
                            containsWoundedPart = true;
                        }
                    }
                    
                    if (containsWoundedPart) {
                        for (int tempX = x; tempX < x + sizeOfEnemyShip; tempX++){
                            if (mapInfo[tempX][y] == 0) {
                                probs[tempX][y] += A_SHIT_TON_OF_PRBABILITY;
                            }
                        }
                    }
                }
            }
            y++;
        }
    }

    
    
    private void checkVerticalFits(int sizeOfEnemyShip) {
        int x = 0;
        
        while (x <= 9){
            for (int y = 0; y <= BOARD_LENGTH - sizeOfEnemyShip; y++){
                
                boolean fits = true;
                for (int tempY = y; tempY < y + sizeOfEnemyShip; tempY++) {
                    if (mapInfo[x][tempY] == 2 || mapInfo[x][tempY] == 3) {
                        fits = false;
                    }
                }
                if (fits) {
                    for (int tempY = y; tempY < y + sizeOfEnemyShip; tempY++){
                        if (mapInfo[x][tempY] == 0) {
                            probs[x][tempY] += 1;
                        }
                    }
                    
                    
                    boolean containsWoundedPart = false;
                    for (int tempY = y; tempY < y + sizeOfEnemyShip; tempY++){
                        if (mapInfo[x][tempY] == 1) {
                            containsWoundedPart = true;
                        }
                    }
                    
                    if (containsWoundedPart) {
                        for (int tempY = y; tempY < y + sizeOfEnemyShip; tempY++){
                            if (mapInfo[x][tempY] == 0) {
                                probs[x][tempY] += A_SHIT_TON_OF_PRBABILITY;
                            }
                        }
                    }
                }
            }
            x++;
        }
    }

    
    private int[] findLuckyCell() {
        int[] luckyCell = new int[]{0, 0};
        int currentHighest = probs[0][0];
        
        for (int x = 0; x < BOARD_LENGTH; x++) {
            for (int y = 0; y < BOARD_LENGTH; y++) {
                if (probs[x][y] > currentHighest) {
                    luckyCell[0] = x;
                    luckyCell[1] = y;
                    currentHighest = probs[x][y];
                }
            }
        }
        
        
        //PRINTING SHIT
//        if (currentRound == 100){
//            for (int y = 9; y >= 0; y--) {
//                for (int x = 0; x < 10; x++) {
//                    System.out.print(probs[x][y] + " ");
//                }
//                System.out.println();
//            }
//            System.out.println("-----------------");
//            for (int y = 9; y >= 0; y--) {
//                for (int x = 0; x < 10; x++) {
//                    System.out.print(mapInfo[x][y] + " ");
//                }
//                System.out.println();
//            }
//            System.out.println("-----------------");
//        }
        //END OF PRINTING
        
        return luckyCell;
    }
    
    
    
    public void adjustMapInfo(boolean lastShot, ArrayList<Integer> updatedEnemyShips){
        
        if (lastShot == false) { //shot was a miss
            mapInfo[lastFiredX][lastFiredY] = 2;
        }
        else{ //shot was a hit
            
            if (enemyShips.size() == updatedEnemyShips.size()) { //shot just wounded a ship
                mapInfo[lastFiredX][lastFiredY] = 1;
            }
            
            else{ //shot actually sunk a ship
                int sunkShipSize = calculateSunkShipSize(updatedEnemyShips);
                enemyShips = updatedEnemyShips;
                mapInfo[lastFiredX][lastFiredY] = 1;
                findSunkShipWreck(sunkShipSize);
            }
            
        }
    }

    
    
    private int calculateSunkShipSize(ArrayList<Integer> updatedEnemyShips) {
        int sunkShipSize = -1; //if sth goes wrong, it returns negative one
        boolean containsEverything = true;
        
        for (int i = 0; i < enemyShips.size(); i++) {
            if (updatedEnemyShips.contains(enemyShips.get(i)) == false) {
                sunkShipSize = enemyShips.get(i);
                containsEverything = false;
            }
        }
        
        if (containsEverything) {
            sunkShipSize = 3;
        }
        
        return sunkShipSize;
    }

    
    
    private void findSunkShipWreck(int sunkShipSize) {
        boolean found;
        
        //first we check horizontally
        for (int x = 0; x <= BOARD_LENGTH - sunkShipSize; x++) {
            found = true;
            for (int tempX = x; tempX < x + sunkShipSize; tempX++) {
                if (mapInfo[tempX][lastFiredY] != 1) {
                    found = false;
                }
            }
            if (found) {
                for (int tempX = x; tempX < x + sunkShipSize; tempX++){
                    mapInfo[tempX][lastFiredY] = 3;
                }
                return;
            }
        }
        
        //then, if not found, we check vertically
        for (int y = 0; y <= BOARD_LENGTH - sunkShipSize; y++) {
            found = true;
            for (int tempY = y; tempY < y + sunkShipSize; tempY++) {
                if (mapInfo[lastFiredX][tempY] != 1) {
                    found = false;
                }
            }
            if (found) {
                for (int tempY = y; tempY < y + sunkShipSize; tempY++) {
                    mapInfo[lastFiredX][tempY] = 3;
                }
                return;
            }
        }
    }
    
}

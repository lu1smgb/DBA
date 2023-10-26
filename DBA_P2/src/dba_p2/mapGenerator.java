/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dba_p2;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author usuario
 */
public class mapGenerator {
    private int mapWidth;
    private int mapHeight;
    private int[][] mapValues;
    
    public mapGenerator(int width, int height){
        mapWidth = width;
        mapHeight = height;
        
        Random rand = new Random();
        double newValue;
        mapValues = new int[height][width];
        
        for (int row = 0; row < mapHeight; ++row){
            for (int column = 0; column < mapWidth; ++column){
                mapValues[row][column] = -1;
            }
        }
        
        mapValues[0][0] = 0;
        mapValues[mapHeight - 1][mapWidth - 1] = 0;
        
        for (int row = 0; row < mapHeight; ++row){
            for (int column = 0; column < mapWidth; ++column){
                newValue = rand.nextDouble();
                if (row != 0 && column != 0){
                    if (row != mapHeight - 1 && column != mapWidth - 1){
                        if (row > 0){               //Para no pasarme por arriba
                            if (column < mapWidth){ //Para no pasarme por la derecha
                                if (column > 0){    //Para no pasarme por la izquierda
                                    if (newValue <= 0.5){           
                                        mapValues[row][column] = 0;
                                    }
                                }
                                else{               
                                    if (newValue <= 0.5){
                                        mapValues[row][column] = 0;
                                    }
                                }
                            }
                            else{
                                if (newValue <= 0.5){
                                    mapValues[row][column] = 0;
                                }
                            }
                        }
                        else{
                            if (column < mapWidth){
                                if (column > 0){
                                   if (newValue <= 0.5){                                        
                                        mapValues[row][column] = 0;
                                    } 
                                }
                                else{
                                    if (newValue <= 0.5){
                                        mapValues[row][column] = 0;
                                    }
                                }
                            }
                            
                        }
                    }
                }
            }
        }
    }
    
    public int[][] getMapValues(){
        return mapValues;
    }
    
    public static void main(String[] args){
        mapGenerator map = new mapGenerator(10, 10);
        
        for (int i = 0; i < 10; ++i){
            for (int j = 0; j < 10; ++j){
                System.out.print(map.getMapValues()[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
}

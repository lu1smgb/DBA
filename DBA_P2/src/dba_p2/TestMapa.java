package dba_p2;

import java.util.ArrayList;

public class TestMapa {
    
    public static void main(String[] args) {
        
        // Array bidimensional 7x5
        int num_rows = 7;
        int num_cols = 5;
        // ***
        // ArrayList<ArrayList<Integer>> datos = new ArrayList<ArrayList<Integer>>();
        int datos[][] = new int[num_rows][num_cols];
        // ***
        for (int i=0; i < num_rows; i++) {
            // ArrayList<Integer> row = new ArrayList<Integer>();
            for (int j=0; j < num_cols; j++) {
                if (i != 0 && j != 0) {
                    datos[i][j] = -1;
                    // row.add(-1);
                }
                else {
                    datos[i][j] = 0;
                    // row.add(0);
                }
            }
            // datos.add(row);
        }

        // Instanciacion del mapa con nuevas dimensiones
        try {
            Mapa m = new dba_p2.Mapa("Mi mapa", 10, 12, datos);
            System.out.print(m);
        }
        catch (Exception e) {
            throw e;
        }
    }

}

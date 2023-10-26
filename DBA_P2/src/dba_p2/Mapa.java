package dba_p2;

import java.util.ArrayList;
import java.lang.String;

public class Mapa {
    
    private static final String _defaultName = "Unnamed map";
    private String name;
    private int num_rows;
    private int num_cols;
    private ArrayList<ArrayList<Integer>> map;

    public Mapa() {

        this(0, 0);

    }

    public Mapa(int num_rows, int num_cols) {

        this(_defaultName, num_rows, num_cols);
        
    }

    // TODO: Metodo privado generar mapa vacio

    public Mapa(String name, int num_rows, int num_cols) {

        this.name = name;
        this.num_rows = num_rows;
        this.num_cols = num_cols;
        this.map = new ArrayList<ArrayList<Integer>>();

        // Generamos el mapa completamente vacio
        for (int i=0; i < num_rows; i++) {
            map.add(new ArrayList<Integer>());
            ArrayList<Integer> fila = map.get(i);
            for (int j=0; j < num_cols; j++) {
                fila.add(j, 0);
            }
        }

    }

    public Mapa(ArrayList<ArrayList<Integer>> map) {

        this.name = _defaultName;
        this.num_rows = map.size();
        // TODO: Maximo de elementos en todas las filas ??
        this.num_cols = map.get(0).size();
        this.map = map;
        
    }

    public Mapa(int num_rows, int num_cols, ArrayList<ArrayList<Integer>> map) {

        this.name = _defaultName;
        this.num_rows = num_rows;
        this.num_cols = num_cols;
        this.map = map;
        
    }
    
    // ******************************************************************************************

    public void print() {

        System.out.println(name);
        
        for (int i=0; i < num_rows; i++) {
            for (int j=0; j < num_cols; j++) {
                System.out.print(map.get(i).get(j) + (j < num_cols - 1 ? " " : "\n"));
            }
        }

    }

    public String getName() {

        return name;

    }

    public void setName(String name) {

        this.name = name;

    }

    public int getNumberOfRows() {

        return num_rows;

    }

    public void setNumberOfRows(int num_rows) {

        this.num_rows = num_rows;

    }

    public int getNumberOfCols() {

        return num_cols;

    }

    public void setNumberOfCols(int num_cols) {

        this.num_cols = num_cols;

    }

    private boolean numberInRange(int n, int lower_range, int upper_range) {

        return n >= lower_range && n < upper_range;

    }

    public boolean changeTile(int element, int row, int col) {

        if (!numberInRange(row, 0, num_rows)) {
            System.err.println("[x] Error: invalid row index (" + row + ") while number of rows is " + num_rows);
        }
        else if (!numberInRange(col, 0, num_cols)) {
            System.err.println("[x] Error: invalid col index (" + col + ") while number of cols is " + num_cols);
        }
        else if (!numberInRange(element, -1, 1)) {
            System.err.println("[x] Error: invalid element" + element + ")");
        }
        else {
            map.get(row).set(col, element);
            return false;
        }
        return false;
    }

}

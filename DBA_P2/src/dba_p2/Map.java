package dba_p2;

import java.util.ArrayList;

import javax.management.InvalidAttributeValueException;

import java.lang.String;

// TODO: Declarar una enum para los tipos de elementos???

/**
 * Clase para almacenar informacion de un mapa
 */
public class Map {
    
    private String name = "Unnamed map";
    private int num_rows;
    private int num_columns;
    private ArrayList<ArrayList<Integer>> map;

    /**
     * <h2>Constructor por defecto</h2>
     * Construye un mapa vacio, sin dimensiones especificadas
     * @see #Map(int, int)
     */
    public Map() {

        this(0, 0);

    }

    /**
     * <h2>Constructor</h2>
     * Construye un mapa vacio, especificando el numero de filas y de columnas
     * 
     * @param num_rows Numero de filas del mapa
     * @param num_columns Numero de columnas del mapa
     */
    public Map(int num_rows, int num_columns) {

        this.num_rows = (num_rows >= 0 ? num_rows : 0);
        this.num_columns = (num_columns >= 0 ? num_columns : 0);
        this.map = new ArrayList<ArrayList<Integer>>();

        // Generamos el mapa completamente vacio
        for (int i=0; i < num_rows; i++) {
            ArrayList<Integer> row = new ArrayList<Integer>();
            for (int j=0; j < num_columns; j++) {
                row.add(j, 0);
            }
            map.add(row);
        }
        
    }

    /**
     * <h2>Constructor</h2>
     * Construye un mapa vacio, especificando el nombre
     * 
     * @param name Nombre del mapa
     * @see #Map(String, int, int)
     */
    public Map(String name) {

        this(name, 0, 0);

    }

    /**
     * <h2>Constructor</h2>
     * Construye un mapa vacio, especificando el numero de filas y de columnas
     * <p>
     * Tambien se le especificara un nombre para el mapa
     * 
     * @param name Nombre del mapa
     * @param num_rows Numero de filas del mapa
     * @param num_columns Numero de columnas del mapa
     * @see #Map(int, int)
     */
    public Map(String name, int num_rows, int num_columns) {

        this(num_rows, num_columns);
        this.name = name;

    }

    /**
     * <h2>Constructor</h2>
     * Crea un mapa a partir de un array bidimensional de enteros
     * <p>
     * Cada elemento dentro del array representa una casilla del mapa
     * <p>
     * Las dimensiones del mapa se ajustaran al numero de filas que tenga y 
     * al maximo de columnas de todas las filas.
     * <p>
     * Tambien se reemplazaran los elementos invalidos segun {@link #replaceUnwantedElements()}
     * 
     * @param map Array bidimensional de enteros del cual se generara el mapa
     */
    public Map(ArrayList<ArrayList<Integer>> map) {

        this.num_rows = map.size();
        int max_cols = 0;
        for (int i=0; i < this.num_rows; i++) {
            ArrayList<Integer> row = map.get(i);
            int row_size = row.size();
            if (row_size > max_cols) {
                max_cols = row_size;
            }
        }
        this.num_columns = max_cols;
        this.map = map;
        replaceUnwantedElements();
        
    }

    /**
     * <h2>Constructor</h2>
     * Crea un mapa a partir de sus dimensiones y un array bidimensional de enteros
     * <p>
     * Cada elemento dentro del array representa una casilla del mapa
     * <p>
     * El mapa se ajustara segun {@link #replaceUnwantedElements()} y {@link #adjustMapToDimensions()}
     * <p>
     * Se verificara que no se haya introducido un entero invalido
     * en el array, en caso contrario, se sustituira por el entero que represente
     * una casilla vacia.
     * 
     * @param num_rows Numero de filas del mapa
     * @param num_columns Numero de columnas del mapa
     * @param map Array bidimensional de enteros del cual se generara el mapa
     * @see #replaceUnwantedElements()
     * @see #adjustMapToDimensions()
     */
    public Map(int num_rows, int num_columns, ArrayList<ArrayList<Integer>> map) {

        this.num_rows = (num_rows >= 0 ? num_rows : 0);
        this.num_columns = (num_columns >= 0 ? num_columns : 0);
        this.map = map;

        replaceUnwantedElements();
        adjustMapToDimensions();
        
    }

    /**
     * <h2>Constructor</h2>
     * Crea un mapa a partir de un nombre, sus dimensiones 
     * y un array bidimensional de enteros
     * <p>
     * Cada elemento dentro del array representa una casilla del mapa
     * <p>
     * Las dimensiones del mapa se ajustaran a las dimensiones 
     * especificadas ({@link #num_rows}, {@link #num_columns})
     * <p>
     * Se verificara que no se haya introducido un entero invalido
     * en el array, en caso contrario, se sustituira por el entero que represente
     * una casilla vacia.
     * 
     * @param name Nombre del mapa
     * @param num_rows Numero de filas del mapa
     * @param num_columns Numero de columnas del mapa
     * @param map Array bidimensional de enteros del cual se generara el mapa
     * @see #Map(int, int, ArrayList)
     */
    public Map(String name, int num_rows, int num_columns, ArrayList<ArrayList<Integer>> map) {

        this(num_rows, num_columns, map);
        this.name = name;

    }
    
    // ******************************************************************************************

    /**
     * <h2>Getter {@link #name}</h2>
     * @return El nombre del mapa
     */
    public String getName() {

        return name;

    }

    /**
     * <h2>Setter {@link #name}</h2>
     * 
     * @param name Nuevo nombre del mapa
     */
    public void setName(String name) {

        this.name = name;

    }

    /**
     * <h2>Getter {@link #num_rows}</h2>
     * 
     * @return Numero de filas del mapa
     */
    public int getNumberOfRows() {

        return num_rows;

    }

    /**
     * <h2>Setter {@link #num_rows}</h2>
     * 
     * {@link #num_rows} tendra que ser mayor o igual que 0, en caso contrario
     * este metodo no realizara ninguna operacion
     * 
     * @param num_rows Nuevo numero de filas del mapa
     */
    public void setNumberOfRows(int num_rows) {
        
        this.num_rows = (num_rows >= 0 ? num_rows : 0);

    }

    /**
     * <h2>Getter {@link #num_columns}</h2>
     * 
     * @return Numero de columnas del mapa
     */
    public int getNumberOfCols() {

        return num_columns;

    }

    /**
     * <h2>Setter {@link #num_columns}</h2>
     * 
     * {@link #num_columns} tendra que ser mayor o igual que 0, en caso contrario
     * este metodo no realizara ninguna operacion
     * 
     * @param num_columns Nuevo numero de columnas del mapa
     */
    public void setNumberOfColumns(int num_columns) {

        this.num_columns = (num_columns >= 0 ? num_columns : 0);

    }

    /**
     * Obtiene el elemento en la fila y columna especificadas
     * 
     * @param row Numero de fila del elemento
     * @param column Numero de columna del elemento
     * @throws IndexOutOfBoundsException
     * @return Si los parametros son correctos devolvera el elemento
     *         deseado
     */
    public int getElement(int row, int column) 
    throws IndexOutOfBoundsException {

        try {
            return map.get(row).get(column);
        }
        catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("[x] Tried to access element (" + row + ", " + column +
                                                ") in map with (" + num_rows + ", " + num_columns + ") dimensions");
        }
        
    }

    /**
     * <h2>Metodo setElement</h2>
     * Cambia un elemento por otro especifico en el mapa
     * <p>
     * 
     * @param element Nuevo elemento
     * @param row Numero de fila
     * @param column Numero de columna
     * @throws IndexOutOfBoundsException
     * @throws InvalidAttributeValueException
     */
    public void setElement(int element, int row, int column) 
    throws IndexOutOfBoundsException, InvalidAttributeValueException {

        if (element != 0 && element != -1) {
            throw new InvalidAttributeValueException("[x] Invalid element (" + element + ")");
        }

        try {
            map.get(row).set(column, element);
        }
        catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("[x] Tried to replace element (" + row + ", " + column +
                                                ") in map with (" + num_rows + ", " + num_columns + ") dimensions");
        }

    }

    /**
     * <h2>Metodo adjustMapDimensions</h2>
     * Metodo usado para ajustar el mapa segun las dimensiones 
     * especificadas ({@link #num_rows}, {@link #num_columns})
     * <p>
     * Agrega las filas/columnas restantes o elimina las filas/columnas sobrantes
     * <p>
     * Si el mapa se hace mas grande, los elementos nuevos se
     * rellenaran con ceros
     * <p>
     * Este metodo deberia de usarse cada vez que las dimensiones del mapa
     * aumenten para evitar accesos ilegales a memoria. 
     * Si las dimensiones del mapa disminuyen, entonces es opcional
     */
    public void adjustMapToDimensions() {

        while (map.size() > num_rows) {
            map.remove(map.size()-1);
        }
        while (map.size() < num_rows) {
            map.add(new ArrayList<Integer>());
        }

        int max_cols = 0;
        for (int i=0; i < map.size(); i++) {

            ArrayList<Integer> row = map.get(i);

            while (row.size() > num_columns) {
                row.remove(row.size()-1);
            }
            while (row.size() < num_columns) {
                row.add(0);
            }

            int row_size = row.size();
            if (row_size > max_cols) {
                max_cols = row_size;
            }

        }
    }

    /**
     * <h2>Metodo replaceUnwantedElements</h2>
     * Reemplaza los elementos no deseados en el mapa con ceros, es decir,
     * los elementos que no sean -1 ni 0
     */
    public void replaceUnwantedElements() {

        for (int i=0; i < this.num_rows; i++) {
            for (int j=0; j < num_columns; j++) {
                int element = this.getElement(i, j);
                if (element != -1 && element != 0) {
                    map.get(i).set(j, 0);
                }
            }
        }

    }

    /** 
     * <h2>Metodo toString</h2>
     * Convierte los datos del mapa a un string que puede ser usado para imprimir los datos
     * por pantalla
     * <p>
     * El string tendra la siguiente estructura:
     * 
     * <ul>
     *  <li>Nombre</li>
     *  <li>Numero de filas y columnas</li>
     *  <li>Representacion del mapa</li>
     * </ul>
     * 
     * El string generado tendra un caracter de nueva linea al final
     * 
     * @return String con los datos del mapa
    */
    @Override
    public String toString() {

        String str = "";
        str += "Name: " + name + "\n";
        str += "Dimensions: (" + num_rows + ", " + num_columns + ")\n";
        
        for (int i=0; i < num_rows; i++) {
            for (int j=0; j < num_columns; j++) {
                // Si leemos el ultimo elemento de la fila, hacemos un linebreak (\n)
                str += map.get(i).get(j) + (j == num_columns - 1 ? "\n" : "\t");
            }
        }

        return str;

    }

}

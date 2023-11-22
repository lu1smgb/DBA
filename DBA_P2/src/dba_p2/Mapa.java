package dba_p2;

import java.util.ArrayList;

import javax.management.InvalidAttributeValueException;

import java.lang.String;

/**
 * Clase para almacenar informacion de un mapa
 */
public class Mapa {
    
    private String name = "Unnamed map";
    private int num_rows;
    private int num_columns;
    private ArrayList<ArrayList<Celda>> map;

    /**
     * <h2>Constructor por defecto</h2>
     * Construye un mapa vacio, sin dimensiones especificadas
     * @see #Map(int, int)
     */
    public Mapa() {

        this(0, 0);

    }

    /**
     * <h2>Constructor</h2>
     * Construye un mapa vacio, especificando el numero de filas y de columnas
     * 
     * @param num_rows Numero de filas del mapa
     * @param num_columns Numero de columnas del mapa
     */
    public Mapa(int num_rows, int num_columns) {

        this.num_rows = (num_rows >= 0 ? num_rows : 0);
        this.num_columns = (num_columns >= 0 ? num_columns : 0);
        this.map = new ArrayList<ArrayList<Celda>>();

        // Generamos el mapa completamente vacio
        for (int i=0; i < num_rows; i++) {
            ArrayList<Celda> row = new ArrayList<Celda>();
            for (int j=0; j < num_columns; j++) {
                row.add(j, Celda.VOID);
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
    public Mapa(String name) {

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
    public Mapa(String name, int num_rows, int num_columns) {

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
     * @param map ArrayList bidimensional de enteros del cual se generara el mapa
     * @see #Mapa(int[][])
     */
    public Mapa(ArrayList<ArrayList<Celda>> map) {

        this.num_rows = map.size();
        int max_cols = 0;
        for (int i=0; i < this.num_rows; i++) {
            ArrayList<Celda> row = map.get(i);
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
     * Crea un mapa a partir de un nombre y un arraylist de celdas
     * <p>
     * 
     * @param name Nombre del mapa
     * @param map Array bidimensional de celdas del cual se generara el mapa
     * @see #Mapa(ArrayList)
     */
    public Mapa(String name, ArrayList<ArrayList<Celda>> map) {

        this(map);
        this.name = name;

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
     * @param map ArrayList bidimensional de enteros del cual se generara el mapa
     * @see #replaceUnwantedElements()
     * @see #adjustMapToDimensions()
     */
    public Mapa(int num_rows, int num_columns, ArrayList<ArrayList<Celda>> map) {

        this.num_rows = (num_rows >= 0 ? num_rows : 0);
        this.num_columns = (num_columns >= 0 ? num_columns : 0);
        this.map = map;

        adjustMapToDimensions();
        replaceUnwantedElements();
        
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
     * @param map ArrayList bidimensional de enteros del cual se generara el mapa
     * @see #Map(int, int, ArrayList)
     */
    public Mapa(String name, int num_rows, int num_columns, ArrayList<ArrayList<Celda>> map) {

        this(num_rows, num_columns, map);
        this.name = name;

    }

    /**
     * <h2>Metodo privado toArrayList</h2>
     * 
     * Convierte un array 2D simple de enteros a un ArrayList 2D de Celda
     * <p>
     * Usado por los constructores que usan como parametro 
     * arrays 2D primitivos (no ArrayList)
     * 
     * @param arr Array 2D simple a convertir
     * @return Array convertido a ArrayList bidimensional
     */
    private ArrayList<ArrayList<Celda>> toArrayList(int[][] arr) {
        ArrayList<ArrayList<Celda>> final_array = new ArrayList<ArrayList<Celda>>();
        for (int i=0; i < arr.length; i++) {
            int[] old_row = arr[i];
            ArrayList<Celda> new_row = new ArrayList<Celda>();
            for (int j=0; j < old_row.length; j++) {
                int old_element = old_row[j];
                Celda new_element = null;
                for (Celda c : Celda.values()) {
                    if (new_element != null) {
                        break;
                    }
                    if (c.value() == old_element) {
                        new_element = c;
                    }
                }
                if (new_element == null) {
                    new_element = Celda.VOID;
                }
                new_row.add(new_element);
            } 
            final_array.add(new_row);
        }
        return final_array;
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
     * @see #Mapa(ArrayList)
     */
    public Mapa(int[][] map) {
        
        this.num_rows = map.length;
        int max_cols = 0;
        for (int i=0; i < this.num_rows; i++) {
            int[] row = map[i];
            int row_size = row.length;
            if (row_size > max_cols) {
                max_cols = row_size;
            }
        }
        this.num_columns = max_cols;
        this.map = toArrayList(map);
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
     * @see #Mapa(int, int, ArrayList)
     * @see #replaceUnwantedElements()
     * @see #adjustMapToDimensions()
     */
    public Mapa(int num_rows, int num_columns, int[][] map) {

        this.num_rows = (num_rows >= 0 ? num_rows : 0);
        this.num_columns = (num_columns >= 0 ? num_columns : 0);
        this.map = toArrayList(map);

        adjustMapToDimensions();
        replaceUnwantedElements();

    }

    /**
     * <h2>Constructor</h2>
     * Crea un mapa a partir de un nombre y un array bidimensional de enteros
     * <p>
     * Cada elemento dentro del array representa una casilla del mapa
     * <p>
     * Se verificara que no se haya introducido un entero invalido
     * en el array, en caso contrario, se sustituira por el entero que represente
     * una casilla vacia.
     * 
     * @param name Nombre del mapa
     * @param map Array bidimensional de enteros del cual se generara el mapa
     * @see #Mapa(int[][])
     */
    public Mapa(String name, int[][] map) {

        this(map);
        this.name = name;

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
     * @see #Mapa(String, int, int, ArrayList)
     */
    public Mapa(String name, int num_rows, int num_columns, int[][] map) {

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
     * <h2>Getter {@link #num_columns}</h2>
     * 
     * @return Numero de columnas del mapa
     */
    public int getNumberOfCols() {

        return num_columns;

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
    public Celda getElement(int row, int column) 
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
     * Obtiene el elemento en las coordenadas
     * 
     * @param coordinates Coordenadas del elemento a obtener
     * @throws IndexOutOfBoundsException
     * @return Si los parametros son correctos devolvera el elemento
     *         deseado
     */
    public Celda getElement(Coordinates coordinates) 
    throws IndexOutOfBoundsException {

        int row = coordinates.y;
        int column = coordinates.x;
        return this.getElement(row, column);
        
    }

    /**
     * Determina si hay un tipo determinado de celda en las coordenadas especificadas
     * 
     * @param celda Tipo de celda
     * @param coordinates Coordenadas
     * @throws IndexOutOfBoundsException
     * @return Si en las coordenadas especificadas se encuentra el tipo de celda especificada
     * @see Celda
     */
    public boolean hayCeldaEnCoordenadas(Celda celda, Coordinates coordinates)
    throws IndexOutOfBoundsException {

        return this.getElement(coordinates) == celda;

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
    public void setElement(Celda element, int row, int column) 
    throws IndexOutOfBoundsException, InvalidAttributeValueException {

        if (element != Celda.VOID && element != Celda.WALL) {
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
            map.add(new ArrayList<Celda>());
        }

        int max_cols = 0;
        for (int i=0; i < map.size(); i++) {

            ArrayList<Celda> row = map.get(i);

            while (row.size() > num_columns) {
                row.remove(row.size()-1);
            }
            while (row.size() < num_columns) {
                row.add(Celda.VOID);
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

        for (int i=0; i < num_rows; i++) {
            for (int j=0; j < num_columns; j++) {
                Celda element = this.getElement(i, j);
                if (element != Celda.VOID && element != Celda.WALL) {
                    map.get(i).set(j, Celda.VOID);
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
                int element = map.get(i).get(j).value();
                // Si leemos el ultimo elemento de la fila, hacemos un linebreak (\n)
                str += element + (j == num_columns - 1 ? "\n" : "\t");
            }
        }

        return str;

    }

}

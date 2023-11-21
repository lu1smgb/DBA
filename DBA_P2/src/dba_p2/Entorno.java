package dba_p2;

import javax.management.InvalidAttributeValueException;

public class Entorno {

    private Mapa mapa;
    private Coordinates posicionAgente;
    private Coordinates posicionObjetivo;
    
    /**
     * <h2>Constructor</h2>
     * 
     * Crea un entorno a partir de un mapa, la posicion del agente y la posicion del objetivo
     * <p>
     * Si alguna de las posiciones proporcionadas no es valida, se intentara buscar una nueva
     * <p>
     * Para la posicion del agente se empezara desde la esquina superior izquierda (0,0) y se ira iterando de izquierda a derecha, de arriba a abajo
     * Para la posicion del objetivo se empezara desde la esquina inferior derecha (columnas-1, filas-1)
     * 
     * @param mapa El mapa
     * @param posicionAgente Posicion inicial del agente en el entorno
     * @param posicionObjetivo Posicion del objetivo en el entorno
     * @throws InvalidAttributeValueException En caso de que una de las posiciones no sea
     * valida y no se pueda encontrar una nueva posicion valida (se da en casos donde el
     * mapa esta completamente lleno de celdas que no pueden ser transitadas por el agente)
     */
    Entorno(Mapa mapa, Coordinates posicionAgente, Coordinates posicionObjetivo) 
    throws InvalidAttributeValueException {
        
        this.mapa = mapa;

        boolean posicionAgenteValido = this.mapa.hayCeldaEnCoordenadas(Celda.VOID, posicionAgente);
        boolean posicionObjetivoValida = this.mapa.hayCeldaEnCoordenadas(Celda.VOID, posicionObjetivo);

        // Si la posicion proporcionada no es valida, intentaremos encontrar una nueva
        // La busqueda se hara de columna a columna, de izquierda a derecha
        if (!posicionAgenteValido) {

            Coordinates nuevaPosicionAgente = new Coordinates(0, 0);
            posicionAgenteValido = this.mapa.hayCeldaEnCoordenadas(Celda.VOID, nuevaPosicionAgente);

            while (!posicionAgenteValido) {
                if (nuevaPosicionAgente.y < getNumFilas() - 1) {
                    nuevaPosicionAgente.y++;
                }
                else if (nuevaPosicionAgente.y >= getNumFilas() - 1) {
                    if (nuevaPosicionAgente.x < getNumColumnas() - 1) {
                        nuevaPosicionAgente.x++;
                    }
                    else if (nuevaPosicionAgente.x >= getNumColumnas() - 1) {
                        throw new InvalidAttributeValueException(
                            "La posicion del agente es invalida y no se ha podido encontrar una posicion valida");
                    }
                }
                posicionAgenteValido = this.mapa.hayCeldaEnCoordenadas(Celda.VOID, nuevaPosicionAgente);
            }

            this.posicionAgente = nuevaPosicionAgente;

        }
        else {
            this.posicionAgente = posicionAgente;
        }

        // Lo mismo que antes, pero para la posicion del objetivo
        // La busqueda se hara de fila a fila, de abajo a arriba
        if (!posicionObjetivoValida) {

            Coordinates nuevaPosicionObjetivo = new Coordinates(getNumColumnas() - 1, getNumFilas() - 1);
            posicionObjetivoValida = this.mapa.hayCeldaEnCoordenadas(Celda.VOID, nuevaPosicionObjetivo);

            while (!posicionObjetivoValida) {
                if (nuevaPosicionObjetivo.x > 0) {
                    nuevaPosicionObjetivo.x--;
                }
                else if (nuevaPosicionObjetivo.x <= 0) {
                    if (nuevaPosicionObjetivo.y > 0) {
                        nuevaPosicionObjetivo.y--;
                    }
                    else if (nuevaPosicionObjetivo.y <= 0) {
                        throw new InvalidAttributeValueException(
                            "La posicion del objetivo es invalida y no se ha podido encontrar una posicion valida");
                    }
                }
                posicionObjetivoValida = this.mapa.hayCeldaEnCoordenadas(Celda.VOID, nuevaPosicionObjetivo);
            }

            this.posicionAgente = nuevaPosicionObjetivo;

        }
        else {
            this.posicionObjetivo = posicionObjetivo;
        }
        
    }

    /**
     * <h2>Constructor</h2>
     * 
     * Construye un entorno a partir de un mapa
     * <p>
     * Las posiciones vendran dadas por defecto:
     * <ul>
     *      <li>Agente: (0,0)</li>
     *      <li>Objetivo (columnas-1, filas-1)</li>
     * </ul>
     * En caso de que no se puedan inicializar las posiciones en el mapa,
     * se ajustaran como sea necesario
     * 
     * @param mapa
     * @throws InvalidAttributeValueException
     * @see {@link #Entorno(Mapa, Coordinates, Coordinates)}
     */
    Entorno(Mapa mapa) 
    throws InvalidAttributeValueException {

        this(mapa, new Coordinates(0, 0), new Coordinates(mapa.getNumberOfCols() - 1, mapa.getNumberOfRows() - 1));

    }

    /**
     * <h2>Getter mapa.name</h2>
     * @return Nombre del mapa
     */
    public String getNombre() {
        return this.mapa.getName();
    }

    /**
     * <h2>Setter mapa.name</h2>
     * Modifica el nombre del mapa
     * @param nombre Nuevo nombre del mapa
     */
    public void setNombre(String nombre) {
        this.mapa.setName(nombre);
    }

    /**
     * <h2>Getter mapa.num_rows</h2>
     * @return Numero de filas del mapa
     */
    public int getNumFilas() {
        return this.mapa.getNumberOfRows();
    }

    /**
     * <h2>Getter mapa.num_columns</h2>
     * @return Numero de columnas del mapa
     */
    public int getNumColumnas() {
        return this.mapa.getNumberOfCols();
    }

    /**
     * <h2>Getter posicionAgente</h2>
     * @return Posicion actual del agente
     */
    public Coordinates getPosicionAgente() {
        return this.posicionAgente;
    }

    /**
     * <h2>Getter posicionObjetivo</h2>
     * @return Posicion del objetivo
     */
    public Coordinates getPosicionObjetivo() {
        return this.posicionObjetivo;
    }

    /**
     * <h2>Metodo coordenadasValidas</h2>
     * @return Si las coordenadas se encuentran dentro de los limites del mapa
     */
    public boolean coordenadasValidas(Coordinates c) {
        return c.x >= 0 && c.x < this.mapa.getNumberOfCols() && c.y >= 0 && c.y < this.mapa.getNumberOfRows();
    }

    /**
     * <h2>Metodo moverAgente</h2>
     * 
     * Mueve el agente hacia unas coordenadas, siempre que este pueda hacerlo
     * 
     * @param nuevaPosicion Nueva posicion del agente
     * @return Si el agente ha podido moverse
     */
    public boolean moverAgente(Coordinates nuevaPosicion) {
        System.out.println("--- INICIA MOVIMIENTO ---");
        if (nuevaPosicion == null || this.getElement(nuevaPosicion) == Celda.WALL) {
            System.out.println("Agente NO PUEDE moverse:\t " + this.posicionAgente + " -> " + nuevaPosicion);
            System.out.println("--- TERMINA MOVIMIENTO ---");
            return false;
        }
        System.out.println("Agente se mueve:\t " + this.posicionAgente + " -> " + nuevaPosicion);
        this.posicionAgente = nuevaPosicion;
        System.out.println("--- TERMINA MOVIMIENTO ---");
        return true;
    }

    /**
     * <h2>Metodo objetivoCumplido</h2>
     * @return Si el agente ha alcanzado al objetivo
     */
    public boolean objetivoCumplido() {
        return this.posicionAgente.equals(this.posicionObjetivo);
    }

    /**
     * <h2>Metodo getElement</h2>
     * @param x Coordenada x
     * @param y Coordenada y
     * @return Tipo de celda en las coordenadas especificadas
     * @throws IndexOutOfBoundsException Si la coordenada se encuentra fuera de los limites del mapa
     */
    public Celda getElement(int x, int y) throws IndexOutOfBoundsException {
        return this.mapa.getElement(y, x);
    }

    /**
     * <h2>Metodo getElement</h2>
     * @param coordenadas Coordenadas
     * @return Tipo de celda en las coordenadas especificadas
     * @throws IndexOutOfBoundsException Si la coordenada se encuentra fuera de los limites del mapa
     */
    public Celda getElement(Coordinates coordenadas) throws IndexOutOfBoundsException {
        return this.mapa.getElement(coordenadas);
    }

    /**
     * <h2>Metodo toString</h2>
     * 
     * Muestra por salida estandar los datos del entorno:
     * <ul>
     *      <li>Nombre del mapa</li>
     *      <li>Dimensiones del mapa</li>
     *      <li>Posicion del agente</li>
     *      <li>Posicion del objetivo</li>
     *      <li>Representacion mas legible del mapa</li>
     * </ul>
     * @return String con los datos del entorno
     */
    @Override
    public String toString() {
        
        String str = "";
        str += "Mapa: " + this.mapa.getName() + "\n";

        int numFilas = this.mapa.getNumberOfRows();
        int numColumnas = this.mapa.getNumberOfCols();
        str += "Dimensiones (columnas,filas): (" + numColumnas + ", " + numFilas + ")\n";

        str += "Posicion del agente: " + this.posicionAgente + "\n";
        str += "Posicion del objetivo: " + this.posicionObjetivo + "\n";

        Coordinates itPosicion = new Coordinates(0, 0);
        String outputElement;
        for (int i=0; i < numFilas; i++) {
            for (int j=0; j < numColumnas; j++) {
                itPosicion.x = j; itPosicion.y = i;
                if (itPosicion.equals(this.posicionAgente)) {
                    outputElement = "A";
                }
                else if (itPosicion.equals(this.posicionObjetivo)) {
                    outputElement = "$";
                }
                else {
                    if (this.mapa.hayCeldaEnCoordenadas(Celda.VOID, itPosicion)) {
                        outputElement = "-";
                    }
                    else if (this.mapa.hayCeldaEnCoordenadas(Celda.WALL, itPosicion)) {
                        outputElement = "#";
                    }
                    else {
                        outputElement = "?";
                    }
                    ////outputElement = String.valueOf(this.mapa.getElement(itPosicion).value());
                }
                // Si leemos el ultimo elemento de la fila, hacemos un linebreak (\n)
                str += outputElement + (j == numColumnas - 1 ? "\n" : "\t");
            }
        }
        return str;

    }

}
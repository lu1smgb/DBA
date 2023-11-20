package dba_p2;

import javax.management.InvalidAttributeValueException;

public class Entorno {

    private Mapa mapa;
    private Coordinates posicionAgente;
    private Coordinates posicionObjetivo;

    Entorno() {

    }

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

    Entorno(Mapa mapa) 
    throws InvalidAttributeValueException {

        this(mapa, new Coordinates(0, 0), new Coordinates(mapa.getNumberOfCols() - 1, mapa.getNumberOfRows() - 1));

    }

    public String getNombre() {
        return this.mapa.getName();
    }

    public void setNombre(String nombre) {
        this.mapa.setName(nombre);
    }

    public int getNumFilas() {
        return this.mapa.getNumberOfRows();
    }

    public int getNumColumnas() {
        return this.mapa.getNumberOfCols();
    }

    public Coordinates getPosicionAgente() {
        return this.posicionAgente;
    }

    public Coordinates getPosicionObjetivo() {
        return this.posicionObjetivo;
    }

    public boolean coordenadasValidas(Coordinates c) {
        return c.x >= 0 && c.x < this.mapa.getNumberOfCols() && c.y >= 0 && c.y < this.mapa.getNumberOfRows();
    }

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

    public boolean objetivoCumplido() {
        return this.posicionAgente.equals(this.posicionObjetivo);
    }

    public Celda getElement(int x, int y) throws IndexOutOfBoundsException {
        return this.mapa.getElement(y, x);
    }

    public Celda getElement(Coordinates coordenadas) throws IndexOutOfBoundsException {
        return this.mapa.getElement(coordenadas);
    }

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
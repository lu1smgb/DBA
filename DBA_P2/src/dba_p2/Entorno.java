package dba_p2;

public class Entorno {

    private Mapa mapa;
    private Coordinates posicionAgente;
    private Coordinates posicionObjetivo;

    Entorno() {

    }

    Entorno(Mapa mapa) {

        this.mapa = mapa;
        this.posicionAgente = new Coordinates(0, 0);
        this.posicionObjetivo = new Coordinates(this.mapa.getNumberOfCols() - 1, this.mapa.getNumberOfRows() - 1);

    }

    Entorno(Mapa mapa, Coordinates posicionAgente, Coordinates posicionObjetivo) {
        
        this.mapa = mapa;
        this.posicionAgente = posicionAgente;
        this.posicionObjetivo = posicionObjetivo;
        
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

    public boolean setPosicionAgente(Coordinates nuevaPosicion) {
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
        return this.posicionAgente == this.posicionObjetivo;
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
                    int valorCelda = this.mapa.getElement(itPosicion).value();
                    if (valorCelda == Celda.VOID.value()) {
                        outputElement = "-";
                    }
                    else if (valorCelda == Celda.WALL.value()) {
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
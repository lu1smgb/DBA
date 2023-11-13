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

    public int getNumFilas() {
        return this.mapa.getNumberOfRows();
    }

    public int getNumColumnas() {
        return this.mapa.getNumberOfCols();
    }

    public Coordinates getPosicionAgente() {
        return this.posicionAgente;
    }

    public Coordinates percibirDireccion(Movimiento direccion) {
        Coordinates coordenadas = this.posicionAgente;
        switch (direccion) {
            case ARRIBA:
                coordenadas.y -= 1;
                break;
            case ABAJO:
                coordenadas.y += 1;
                break;
            case IZQUIERDA:
                coordenadas.x -= 1;
                break;
            case DERECHA:
                coordenadas.x += 1;
                break;
            default:
                break;
        }
        return coordenadas;
    }
    
    public Coordinates getPosicionObjetivo() {
        return this.posicionObjetivo;
    }

    public boolean moverAgente(Movimiento movimiento) {
        boolean puedeMoverse = false;
        try {
            Coordinates nuevasCoordenadas = this.getCoordenadas(movimiento);
            Celda siguienteCelda = this.mapa.getElement(nuevasCoordenadas);
            puedeMoverse = siguienteCelda != Celda.WALL;
            if (puedeMoverse) {
                this.posicionAgente = nuevasCoordenadas;
            }
            else {
                System.err.println("Movimiento invalido: choca con muro " + movimiento);
            }
        }
        catch (IndexOutOfBoundsException e) {
            System.err.println("Movimiento invalido: fuera de limites");
        }
        return puedeMoverse;
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
                    outputElement = String.valueOf(this.mapa.getElement(itPosicion).value());
                }
                // Si leemos el ultimo elemento de la fila, hacemos un linebreak (\n)
                str += outputElement + (j == numColumnas - 1 ? "\n" : "\t");
            }
        }
        return str;

    }

}
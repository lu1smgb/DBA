package dba_p2;

public class Entorno {

    private Mapa mapa;
    private Coordinates posicionAgente;
    private Coordinates posicionObjetivo;

    Entorno() {

    }

    Entorno(Mapa mapa) {

        this.mapa = mapa;
        this.posicionAgente = new Coordinates(this.mapa.getNumberOfCols() - 1, this.mapa.getNumberOfRows() - 1);
        this.posicionObjetivo = new Coordinates(0, 0);

    }

    /**
     * 
     */
    public boolean moverAgente(Movimiento movimiento) {
        Coordinates nuevasCoordenadas = this.posicionAgente;
        boolean exito = false;
        switch (movimiento) {
            case ARRIBA:
                nuevasCoordenadas.y -= 1;
                break;
            case ABAJO:
                nuevasCoordenadas.y += 1;
                break;
            case IZQUIERDA:
                nuevasCoordenadas.x -= 1;
                break;
            case DERECHA:
                nuevasCoordenadas.x += 1;
                break;
            default:
                break;
        }
        try {
            Celda siguienteCelda = this.mapa.getElement(nuevasCoordenadas);
            exito = siguienteCelda != Celda.WALL;
        }
        catch (IndexOutOfBoundsException e) {
            System.err.println("Movimiento invalido: fuera de limites");
        }
        return exito;
    }

    public boolean objetivoCumplido() {
        return this.posicionAgente == this.posicionObjetivo;
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

    public static void main(String args[]) {
        // Array bidimensional 7x5
        int num_rows = 7;
        int num_cols = 5;
        // ***
        // ArrayList<ArrayList<Celda>> datos = new ArrayList<ArrayList<Celda>>();
        int datos[][] = new int[num_rows][num_cols];
        // ***
        for (int i = 0; i < num_rows; i++) {
            // ArrayList<Celda> row = new ArrayList<Celda>();
            for (int j = 0; j < num_cols; j++) {
                if (i != 0 && j != 0) {
                    datos[i][j] = -1;
                    // row.add(-1);
                } else {
                    datos[i][j] = 0;
                    // row.add(0);
                }
            }
            // datos.add(row);
        }

        // Instanciacion del mapa con nuevas dimensiones
        try {
            Mapa m = new dba_p2.Mapa("Mi mapa", 10, 12, datos);
            Entorno e = new Entorno(m);
            System.out.println(e);
        } catch (Exception e) {
            throw e;
        }
    }

}
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
            Celda siguienteCelda = this.mapa.getElement(nuevasCoordenadas.x, nuevasCoordenadas.y);
            exito = siguienteCelda != Celda.WALL;
        }
        catch (IndexOutOfBoundsException e) {
            System.err.println("Movimiento invalido: fuera de limites");
        }
        return exito;
    }

}
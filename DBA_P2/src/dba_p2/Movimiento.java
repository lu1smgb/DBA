package dba_p2;

public enum Movimiento {
    
    ARRIBA(0),
    ABAJO(1),
    IZQUIERDA(2),
    DERECHA(3);

    private int value;

    Movimiento(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

}

package dba_p2;

public enum Celda {

    WALL(-1), VOID(0);

    private int value;

    Celda(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    // public static void main(String[] args) {
    //     System.out.println("WALL: " + Celda.WALL.value);
    //     System.out.println("VOID: " + Celda.VOID.value);
    // }
};

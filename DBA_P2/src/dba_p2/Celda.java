package dba_p2;

public enum Celda {

    WALL(-1), VOID(0);

    private int id;

    Celda(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }

    public static void main(String[] args) {
        System.out.println("WALL: " + Celda.WALL.id);
        System.out.println("VOID: " + Celda.VOID.id);
    }
};

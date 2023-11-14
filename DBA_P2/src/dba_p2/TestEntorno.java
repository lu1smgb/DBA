package dba_p2;

public class TestEntorno {
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
                if (i != 0 && j == num_cols - 1) {
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
            e.moverAgente(Movimiento.ABAJO);
            e.moverAgente(Movimiento.DERECHA);
            e.moverAgente(Movimiento.DERECHA);
            e.moverAgente(Movimiento.DERECHA);
            e.moverAgente(Movimiento.DERECHA);
            System.out.println(e);
        } catch (Exception e) {
            throw e;
        }
    }
}

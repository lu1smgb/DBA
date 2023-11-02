package P2DBA;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPANames;
import java.util.Random;
import P2DBA.TableroSwing;
import jade.core.AID;

public class MyJadeAgent extends Agent {
    private int x; // Posición X actual del agente en la matriz
    private int y; // Posición Y actual del agente en la matriz

    // Referencia a la interfaz gráfica Swing
    private TableroSwing tablero;

    protected void setup() {
        // Inicializa la posición del agente (ajusta según tus necesidades)
        x = 0;
        y = 0;

        // Inicializa la referencia a la interfaz gráfica
        tablero = new TableroSwing(new int[][]{
            {0, 0, 0, -1, 0, -1, 0},
            {0, 0, -1, 0, 0, -1, 0},
            {0, 0, 0, 0, 0, -1, 0},
            {0, 0, 0, 0, 0, -1, 0},
            {0, 0, 0, -1, 0, -1, 0},
            {0, 0, -1, 0, 0, -1, 0},
            {0, 0, 0, 0, 0, -1, 0},
            {0, 0, 0, 0, 0, -1, 0}
        });

        // Comportamiento para manejar las actualizaciones de posición
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                // Simula el movimiento del agente
                moverAgenteAleatoriamente();

                // Actualiza la posición del agente en la interfaz gráfica
                tablero.setAgentePosition(x, y);

                // Envia un mensaje a la interfaz gráfica con la nueva posición (opcional)
                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                message.setContent("Nueva posición del agente: " + x + ", " + y);
                message.addReceiver(new AID("InterfazGrafica", AID.ISLOCALNAME));
                send(message);

                // Pausa para simular un intervalo de tiempo entre movimientos
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Simula el movimiento del agente de forma aleatoria
    private void moverAgenteAleatoriamente() {
        Random random = new Random();
        int[] posiblesMovimientosX = {1, -1, 0, 0};
        int[] posiblesMovimientosY = {0, 0, 1, -1};
        int indiceMovimiento = random.nextInt(4);

        int nuevoX = x + posiblesMovimientosX[indiceMovimiento];
        int nuevoY = y + posiblesMovimientosY[indiceMovimiento];

        // Asegúrate de que el agente no se salga de los límites de la matriz (ajusta según el tamaño de tu matriz)
        if (nuevoX >= 0 && nuevoX < tablero.getMatriz()[0].length && nuevoY >= 0 && nuevoY < tablero.getMatriz().length) {
            x = nuevoX;
            y = nuevoY;
        }
         System.out.println(x);
         System.out.println(y);
    }
}

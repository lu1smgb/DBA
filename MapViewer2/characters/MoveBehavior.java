package characters;

import jade.core.behaviours.Behaviour;

public class MoveBehavior extends Behaviour {
    @Override
    public void action() {
        // Lógica para el comportamiento de movimiento
        System.out.println("Ejecutando el comportamiento de movimiento.");
    }

    @Override
    public boolean done() {
        // Determina si el comportamiento ha terminado
        return true; // En este caso, el comportamiento se ejecuta solo una vez
    }
}

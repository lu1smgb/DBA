package dba_p2;

import java.util.Random;

public class ComportamientoAleatorio extends ComportamientoAgente {

    ComportamientoAleatorio(NuevoAgente agente) {
        super(agente);
    }

    @Override
    protected Movimiento decidirMovimiento() {

        Random rng = new Random();
        int resultadoRng = rng.nextInt(Movimiento.values().length);
        Movimiento resultado = Movimiento.values()[resultadoRng];
        System.out.println("Agente decide aleatoriamente: " + resultado);
        return resultado;

    }

}

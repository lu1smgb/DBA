package dba_p2;

import jade.core.behaviours.CyclicBehaviour;

public abstract class ComportamientoAgente extends CyclicBehaviour {

    protected NuevoAgente agente;

    ComportamientoAgente(NuevoAgente agente) {
        this.myAgent = this.agente = agente;
    }

    protected abstract Movimiento decidirMovimiento();

    private void espera(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void action() {

        Entorno entorno = this.agente.getEntorno();
        System.out.println(entorno);

        if (entorno.objetivoCumplido()) {
            System.out.println("!!!!! OBJETIVO CUMPLIDO !!!!!");
            espera(3000);
            this.agente.doDelete();
        }
        else {
            Movimiento decision = this.decidirMovimiento();
            this.agente.moverse(decision);
            espera(500);
        }

    }

}

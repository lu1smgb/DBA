package dba_p2;

import jade.core.behaviours.CyclicBehaviour;

public abstract class ComportamientoAgente extends CyclicBehaviour {
    
    private NuevoAgente agente;

    ComportamientoAgente(NuevoAgente agente) {
        this.agente = (NuevoAgente) agente;
    }

    protected abstract Movimiento decidirMovimiento();

    public void action() {

        Entorno entorno = this.agente.getEntorno();

        if (!entorno.objetivoCumplido()) {
            Movimiento decision = this.decidirMovimiento();
            this.agente.moverse(decision);
            System.out.println(entorno);
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void onTick() {
        if (this.agente.getEntorno().objetivoCumplido()) {
            agente.doDelete();
        }
    }

}

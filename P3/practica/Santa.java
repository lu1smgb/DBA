package P3.practica;

import java.security.SecureRandom;

import jade.core.Agent;

public class Santa extends Agent {

    private String codigoSecreto;

    public String getCodigoSecreto() {
        return codigoSecreto;
    }

    @Override
    protected void setup() {

        final String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        final int TAM_CODIGO = 16;
        StringBuilder builder = new StringBuilder(TAM_CODIGO);
        SecureRandom random = new SecureRandom();

        for (int i=0; i < TAM_CODIGO; i++) {
            int idxCaracter = random.nextInt(caracteres.length());
            char caracter = caracteres.charAt(idxCaracter);
            builder.append(caracter);
        }
        
        codigoSecreto = builder.toString();

        this.addBehaviour(new EsperaPropuesta(this));
    }

    @Override
    protected void takeDown() {
        System.out.println("Termina Santa");
        System.exit(0);
    }
    
}

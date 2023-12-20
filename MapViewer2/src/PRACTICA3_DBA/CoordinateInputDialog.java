package PRACTICA3_DBA;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Ventana que se encarga de leer las coordenadas de origen y destino inicializar el entorno
// e iniciar el agente
public class CoordinateInputDialog extends JFrame {
    private JTextField originField;
    private int[][] mapMatrix;

    public CoordinateInputDialog(int[][] mapMatrix) {
        this.mapMatrix = mapMatrix;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Coordenadas");
        setSize(300, 150);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel originLabel = new JLabel("Coordenada de origen (fila columna):");
        originField = new JTextField();


        JButton submitButton = new JButton("Aceptar");
        submitButton.addActionListener(e -> validateAndClose());

        panel.add(originLabel);
        panel.add(originField);
        panel.add(submitButton);

        getContentPane().add(panel);

        pack();
    }

    private void validateAndClose() {
        try {
            String[] originParts = originField.getText().split(" ");

            int originRow = Integer.parseInt(originParts[0]);
            int originCol = Integer.parseInt(originParts[1]);

            if (isValidCoordinate(originRow, originCol)) {
                System.out.println("Coordenadas válidas: Origen (" + originRow + ", " + originCol + ")");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Coordenadas no válidas. Asegúrate de que estén dentro del mapa.");
            }
            jade.core.Runtime rt = jade.core.Runtime.instance();

            Profile p = new ProfileImpl();
            p.setParameter(Profile.MAIN_HOST, "localhost");
            p.setParameter(Profile.CONTAINER_NAME, "P3_Container");

            ContainerController cc = rt.createAgentContainer(p);
            Entorno e = new Entorno(mapMatrix, originRow, originCol);
            Object[] arrayargs = {e};
            try {
                String agentName = "MiAgente";
                String agentrName = "Rudolph";
                String agentsName = "Santa";
                AgentController ac = cc.createNewAgent(agentName, MyAgent.class.getName(), arrayargs);
                AgentController rd = cc.createNewAgent(agentrName, RudolphAgent.class.getName(), null);
                AgentController st = cc.createNewAgent(agentsName, Santa.class.getName(), null);
                
                ac.start();
                rd.start();
                st.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(this, "Formato de coordenadas incorrecto. Debe ser 'fila columna'.");
        }

    }

    private boolean isValidCoordinate(int row, int col) {
        return row >= 0 && row < mapMatrix.length && col >= 0 && col < mapMatrix[0].length;
    }

}

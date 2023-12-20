package PRACTICA3_DBA;

import javax.swing.*;
import java.awt.*;

// Panel de previsualización del mapa
public class MapPreviewPanel extends JPanel {
    private int[][] mapMatrix;
    private JButton selectMapButton;
    private boolean sim = false;
    private Entorno ent;
    private int circleRadius = 20;

    public MapPreviewPanel() {
        setLayout(new BorderLayout());
        selectMapButton = new JButton("Seleccionar Mapa");
        selectMapButton.addActionListener(e -> {
            openCoordinateInputDialog(mapMatrix);
            SwingUtilities.getWindowAncestor(MapPreviewPanel.this).dispose();
        });
        add(selectMapButton, BorderLayout.SOUTH);
        showSelectMapButton(false);
    }
    
    public MapPreviewPanel(Entorno en) {
        setLayout(new BorderLayout());
        ent = en;
        selectMapButton = new JButton("Seleccionar Mapa");
        selectMapButton.addActionListener(e -> {
            openCoordinateInputDialog(mapMatrix);
            SwingUtilities.getWindowAncestor(MapPreviewPanel.this).dispose();
        });
        add(selectMapButton, BorderLayout.SOUTH);
        showSelectMapButton(false);
    }
    
    public void setsim(){
        sim = true;
    }

    private void openCoordinateInputDialog(int[][] mapMatrix) {
        SwingUtilities.invokeLater(() -> {
            CoordinateInputDialog inputDialog = new CoordinateInputDialog(mapMatrix);
            inputDialog.setLocationRelativeTo(null);
            inputDialog.setVisible(true);
        });
    }

    public void setMapMatrix(int[][] mapMatrix) {
        this.mapMatrix = mapMatrix;
    }

    public void showSelectMapButton(boolean show) {
        selectMapButton.setVisible(show);
    }

    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        if(!sim){
        if (mapMatrix != null) {
            int cellSize = Math.min(getWidth() / (mapMatrix[0].length + 2), getHeight() / (mapMatrix.length + 2));

            for (int i = 0; i < mapMatrix.length; i++) {
                for (int j = 0; j < mapMatrix[0].length; j++) {
                    int x = (j + 1) * cellSize;
                    int y = (i + 1) * cellSize;

                    g.setColor(mapMatrix[i][j] == 0 ? Color.GREEN : Color.RED);
                    g.fillRect(x, y, cellSize, cellSize);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, cellSize, cellSize);
                }
            }
        }
        }
        else{
        int cellWidth = getWidth() / mapMatrix.length;
        int cellHeight = getHeight() / mapMatrix[0].length;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < mapMatrix.length; i++) {
            for (int j = 0; j < mapMatrix[0].length; j++) {
                if(i == ent.destino.x && j == ent.destino.y){
                    g.setColor(Color.cyan);
                }
                else if (mapMatrix[i][j] == 0) {
                    g.setColor(Color.GREEN);
                } else if (mapMatrix[i][j] == -1) {
                    g.setColor(Color.RED);
                }

                g.fillRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
            }
        }
        
        g.setColor(Color.BLUE);
        for (Nodo punto : ent.visitados) {
            g.fillRect(punto.y * cellWidth + cellWidth / 7, punto.x * cellHeight + cellHeight / 7, cellWidth / 7, cellHeight / 7);
        }
        
        g.setColor(Color.YELLOW);
        g.fillOval(ent.agente_actual.y * cellWidth + cellWidth / 2 - circleRadius / 2, ent.agente_actual.x * cellHeight + cellHeight / 2 - circleRadius / 2, circleRadius, circleRadius);
        }
    }
}

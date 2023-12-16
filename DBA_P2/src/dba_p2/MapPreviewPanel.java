package dba_p2;

import javax.swing.*;
import java.awt.*;

// Panel de previsualización del mapa
public class MapPreviewPanel extends JPanel {
    private int[][] mapMatrix;
    private JButton selectMapButton;

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
}

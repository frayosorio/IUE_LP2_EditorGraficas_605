package servicios;

import java.awt.Graphics;
import java.awt.Color;

import javax.swing.JPanel;

public class DibujoServicio {


    // ********** Metodos Estaticos **********

    public static void limpiarPanel(JPanel pnl) {
        Graphics g = pnl.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, pnl.getWidth(), pnl.getHeight());
    }

}

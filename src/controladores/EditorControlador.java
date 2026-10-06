package controladores;

import modelos.Estado;
import modelos.TipoTrazo;
import servicios.DibujoServicio;
import vistas.EditorVista;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class EditorControlador {

    private final EditorVista vista;

    private Estado estado;
    private int x, y;

    public EditorControlador(EditorVista vista) {
        this.vista = vista;
        estado = Estado.NADA;

        this.vista.setClickPanelGrafica(new MouseAdapter() {
            public void mouseClicked(MouseEvent evento) {
                clicRaton(evento);
            }
        });

        this.vista.setMovimientoRatonPanelGrafica(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent evento) {
                movimientoRaton(evento);
            }
        });
    }

    private void clicRaton(MouseEvent evento) {
        if (estado == Estado.NADA) {
            x = evento.getX();
            y = evento.getY();
            estado = Estado.TRAZANDO;
            System.out.println("primer punto x=" + x + " y=" + y);
        } else {
            System.out.println("segundo punto x=" + evento.getX() + " y=" + evento.getY());

            var g = vista.getGraficadorPanel();
            g.setColor(Color.WHITE);
            switch (vista.getTipoTrazoSeleccionado()) {
                case TipoTrazo.LINEA:
                    g.drawLine(x, y, evento.getX(), evento.getY());
                    break;
                case TipoTrazo.RECTANGULO:
                    g.drawRect(x, y, Math.abs(evento.getX() - x), Math.abs(evento.getY() - y));
                    break;
            }
            estado = Estado.NADA;
        }
    }

    private void movimientoRaton(MouseEvent evento) {
        if (estado == Estado.TRAZANDO) {
            DibujoServicio.limpiarPanel(vista.getPanelGrafica());
            var g = vista.getGraficadorPanel();
            g.setColor(Color.WHITE);
            switch (vista.getTipoTrazoSeleccionado()) {
                case TipoTrazo.LINEA:
                    g.drawLine(x, y, evento.getX(), evento.getY());
                    break;
                case TipoTrazo.RECTANGULO:
                    g.drawRect(x, y, Math.abs(evento.getX() - x), Math.abs(evento.getY() - y));
                    break;
            }
        }
    }

}

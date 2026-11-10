package controladores;

import modelos.*;
import servicios.ArchivoServicio;
import servicios.DibujoServicio;
import vistas.EditorVista;

import java.awt.*;
import java.awt.event.*;

public class EditorControlador {

    private final EditorVista vista;

    private Estado estado;
    private int x, y;

    String nombreArchivo = "";

    private DibujoServicio dibujo = new DibujoServicio();

    public EditorControlador(EditorVista vista) {
        this.vista = vista;
        estado = Estado.NADA;

        this.vista.setIniciarLienzo(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                iniciarLienzo();
            }
        });
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

        this.vista.setGuardarDibujo(evento -> guardarDibujo());
        this.vista.setCargarDibujo(evento -> cargarDibujo());
        this.vista.setSeleccionarTrazo(evento -> seleccionarTrazo());
        this.vista.setDibujar(evento -> dibujar());
        this.vista.setEliminarTrazo(evento -> eliminarTrazo());
    }

    private void iniciarLienzo() {
        DibujoServicio.limpiarLienzo(vista.getGraficadorPanel(),
                vista.getAnchoPanel(),
                vista.getAltoPanel());
    }

    private void redibujar() {
        DibujoServicio.limpiarLienzo(vista.getGraficadorPanel(),
                vista.getAnchoPanel(),
                vista.getAltoPanel());
        dibujo.dibujar(vista.getGraficadorPanel());
    }

    private void clicRaton(MouseEvent evento) {
        if (estado == Estado.NADA) {
            x = evento.getX();
            y = evento.getY();
            estado = Estado.TRAZANDO;
            System.out.println("primer punto x=" + x + " y=" + y);
        } else if (estado == Estado.TRAZANDO) {
            System.out.println("segundo punto x=" + evento.getX() + " y=" + evento.getY());

            Trazo trazo = switch (vista.getTipoTrazoSeleccionado()) {
                case TipoTrazo.LINEA -> new Linea(x, y, evento.getX(), evento.getY());
                case TipoTrazo.RECTANGULO -> new Rectangulo(x, y, evento.getX(), evento.getY());
                case TipoTrazo.OVALO -> new Ovalo(x, y, evento.getX(), evento.getY());
            };
            dibujo.agregar(new Nodo(trazo, vista.getColorTrazo()));
            redibujar();
            estado = Estado.NADA;
        } else if (estado == Estado.SELECCIONANDO) {
            if (dibujo.seleccionar(evento.getX(), evento.getY())) {
                redibujar();
            }
        }
    }

    private void movimientoRaton(MouseEvent evento) {
        vista.asignarCursor(Cursor.getDefaultCursor());
        if (estado == Estado.TRAZANDO) {
            Trazo trazo = switch (vista.getTipoTrazoSeleccionado()) {
                case TipoTrazo.LINEA -> new Linea(x, y, evento.getX(), evento.getY());
                case TipoTrazo.RECTANGULO -> new Rectangulo(x, y, evento.getX(), evento.getY());
                case TipoTrazo.OVALO -> new Ovalo(x, y, evento.getX(), evento.getY());
            };
            redibujar();
            trazo.dibujar(vista.getGraficadorPanel(), vista.getColorTrazo(), false);
        } else if (estado == Estado.SELECCIONANDO) {
            vista.asignarCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

    }

    private void guardarDibujo() {
        if (nombreArchivo.equals("")) {
            nombreArchivo = ArchivoServicio.elegirArchivo(true);
        }
        if (!nombreArchivo.equals("")) {
            dibujo.guardarJson(nombreArchivo);

            vista.mostrarMensaje("El archivo con el dibujo fue guardado");
        }
    }

    private void cargarDibujo() {
        nombreArchivo = ArchivoServicio.elegirArchivo(false);
        if (!nombreArchivo.isEmpty()) {
            dibujo.cargarDesdeJson(nombreArchivo);
            redibujar();
        }
    }

    private void seleccionarTrazo() {
        estado = Estado.SELECCIONANDO;
    }

    private void dibujar() {
        estado = Estado.NADA;
        redibujar();
    }

    private void eliminarTrazo() {
        if (dibujo.getNodoSeleccionado() != null) {
            dibujo.eliminar();
            redibujar();
        } else {
            vista.mostrarMensaje("No hay trazo seleccionado");
        }
    }

}

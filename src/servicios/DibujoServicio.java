package servicios;

import com.fasterxml.jackson.core.type.TypeReference;
import modelos.*;

import java.awt.Graphics;
import java.awt.Color;
import java.util.List;

public class DibujoServicio {

    private Nodo cabeza;
    private Nodo nodoSeleccionado;

    public DibujoServicio() {
        cabeza = null;
    }

    public Nodo getNodoSeleccionado() {
        return nodoSeleccionado;
    }

    public void agregar(Nodo nodo) {
        if (cabeza == null) {
            // lista ligada vacia
            cabeza = nodo;
        } else {
            // recorrer la lista hasta el ultimo
            var actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nodo;
        }
    }

    public void dibujar(Graphics g) {
        // recorrer la lista completa
        var actual = cabeza;
        while (actual != null) {
            actual.getTrazo().dibujar(g, actual.getColor(), actual == nodoSeleccionado);
            actual = actual.siguiente;
        }
    }

    public boolean seleccionar(int x, int y) {
        nodoSeleccionado = null;
        var actual = cabeza;
        while (actual != null) {
            if (actual.getTrazo().cercano(x, y)) {
                nodoSeleccionado = actual;
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public void eliminar() {
        if (cabeza != null && nodoSeleccionado != null) {
            if (nodoSeleccionado == cabeza) {
                cabeza = cabeza.siguiente;
            } else {
                Nodo anterior = null;
                var actual = cabeza;
                while (actual != nodoSeleccionado) {
                    anterior = actual;
                    actual = actual.siguiente;
                }
                anterior.siguiente = actual.siguiente;
                nodoSeleccionado = null;
            }
        }
    }

    private int getLongitud() {
        int totalNodos = 0;

        // recorrer la lista completa
        var actual = cabeza;
        while (actual != null) {
            totalNodos++;
            actual = actual.siguiente;
        }

        return totalNodos;
    }

    public boolean guardarJson(String nombreArchivo) {
        TrazoDto[] trazos = new TrazoDto[getLongitud()];

        // recorrer la lista completa
        var actual = cabeza;
        int fila = 0;
        while (actual != null) {
            trazos[fila] = actual.toDto();
            fila++;
            actual = actual.siguiente;
        }

        return ArchivoServicio.guardarJson(nombreArchivo, trazos);
    }

    public void cargarDesdeJson(String nombreArchivo) {
        var trazosDto = ArchivoServicio.leerJson(nombreArchivo, new TypeReference<List<TrazoDto>>() {
        });
        cabeza = null;
        for (var trazoDto : trazosDto) {
            Trazo trazo = null;
            switch (TipoTrazo.valueOf(trazoDto.getTipo())) {
                case LINEA:
                    trazo = new Linea(trazoDto.getX1(), trazoDto.getY1(), trazoDto.getX2(), trazoDto.getY2());
                    break;
                case OVALO:
                    trazo = new Ovalo(trazoDto.getX1(), trazoDto.getY1(), trazoDto.getX2(), trazoDto.getY2());
                    break;
                case RECTANGULO:
                    trazo = new Rectangulo(trazoDto.getX1(), trazoDto.getY1(), trazoDto.getX2(), trazoDto.getY2());
                    break;
            }
            if (trazo != null) {
                agregar(new Nodo(trazo, new Color(trazoDto.getRed(), trazoDto.getGreen(), trazoDto.getBlue())));
            }
        }
    }

    // ********** Metodos Estaticos **********

    public static void limpiarLienzo(Graphics g, int ancho, int alto) {
        g.setColor(Color.black);
        g.fillRect(0, 0, ancho, alto);
    }

}

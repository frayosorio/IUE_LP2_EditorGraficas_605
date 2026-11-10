package modelos;

import java.awt.*;

public abstract class Trazo {
    private int x1, y1, x2, y2;

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public Trazo(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public int getAncho() {
        return Math.abs(x2 - x1) + 1;
    }

    public int getAlto() {
        return Math.abs(y2 - y1) + 1;
    }

    public int getXMinimo() {
        return Math.min(x1, x2);
    }

    public int getYMinimo() {
        return Math.min(y1, y2);
    }

    public abstract void dibujar(Graphics g, Color color, boolean seleccionando);

    public abstract TipoTrazo getTipo();

    public abstract boolean cercano(int x, int y);

    // metodos estaticos
    public static int TOLERANCIA = 5;

    public static boolean esCercanoALinea(int x, int y,
                                          int x1, int y1, int x2, int y2) {
        double distancia = distanciaALinea(x, y, x1, y1, x2, y2);
        return distancia <= TOLERANCIA;
    }

    private static double distanciaALinea(int x, int y,
                                          int x1, int y1, int x2, int y2) {
        //***** Calcular la posición relativa del punto (x, y)
        // sobre el segmento de recta que va desde (x1, y1) hasta (x2, y2),
        // tomando como referencia la longitud total del segmento.
        // La posición relativa es un factor de interpolación que indica
        // qué proporción del desplazamiento entre los extremos debemos recorrer
        // para encontrar el punto más cercano. *****

        // vector de la recta: (px, py)
        double px = x2 - x1;
        double py = y2 - y1;

        // vector desde el inicio de la recta:  (x-x1, y-y1)
        // se calcula posición relativa
        double posicionRelativa = ((x - x1) * px + (y - y1) * py) / (Math.pow(px, 2) + Math.pow(py, 2));

        // Valores de posicionRelativa:
        // 0 : La proyección coincide con (x1, y1)
        // 0.25: Está al 25% del segmento
        // 0.5: está en la mitad
        // 1: La proyección coincide con (x2, y2)
        // < 0 : La proyección queda antes del inicio
        // > 0 : La proyección queda después del final

        posicionRelativa = posicionRelativa > 1 ? 1 : posicionRelativa < 0 ? 0 : posicionRelativa;

        //***** Calcular xCerca y yCerca como las coordenadas del punto del segmento de recta
        // que está más cerca del punto (x, y) *****

        double xCerca = x1 + posicionRelativa * px;
        double yCerca = y1 + posicionRelativa * py;

        return Math.sqrt(Math.pow(x - xCerca, 2) + Math.pow(y - yCerca, 2));
    }
}

import controladores.EditorControlador;
import vistas.EditorVista;

public class App {
    public static void main(String[] args) throws Exception {
        var vista = new EditorVista();
        new EditorControlador(vista);
        vista.setVisible(true);
    }
}

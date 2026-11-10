package vistas;

import modelos.TipoTrazo;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;

public class EditorVista extends JFrame {

    private JButton btnCargar, btnGuardar, btnEliminar, btnSeleccionar, btnDibujar;
    private JComboBox cmbTipo, cmbColorR, cmbColorG, cmbColorB;
    private JLabel lblColor;
    private JToolBar tbEditor;
    private JPanel pnlGrafica;

    private Color color;

    public EditorVista() {

        tbEditor = new JToolBar();
        btnCargar = new JButton();
        btnGuardar = new JButton();
        btnSeleccionar = new JButton();
        btnEliminar = new JButton();
        btnDibujar = new JButton();
        cmbTipo = new JComboBox();
        cmbColorR = new JComboBox();
        cmbColorG = new JComboBox();
        cmbColorB = new JComboBox();
        lblColor = new JLabel("     ");

        pnlGrafica = new JPanel();

        setSize(700, 500);
        setTitle("Editor de gráficas");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        btnCargar.setIcon(new ImageIcon(getClass().getResource("/iconos/AbrirArchivos.png")));
        btnCargar.setToolTipText("Agregar");
        tbEditor.add(btnCargar);

        btnGuardar.setIcon(new ImageIcon(getClass().getResource("/iconos/Guardar.png")));
        btnGuardar.setToolTipText("Guardar");
        tbEditor.add(btnGuardar);

        cmbTipo.setModel(
                new DefaultComboBoxModel(TipoTrazo.values()));
        tbEditor.add(cmbTipo);

        for (int i = 0; i < 256; i++) {
            cmbColorR.addItem(i);
            cmbColorG.addItem(i);
            cmbColorB.addItem(i);
        }
        cmbColorR.setSelectedIndex(255);
        cmbColorG.setSelectedIndex(255);
        cmbColorB.setSelectedIndex(255);
        lblColor.setOpaque(true);
        lblColor.setBorder(new LineBorder(Color.black, 2));

        cmbColorR.addActionListener(evento -> setColorTrazo());
        cmbColorG.addActionListener(evento -> setColorTrazo());
        cmbColorB.addActionListener(evento -> setColorTrazo());

        tbEditor.add(cmbColorR);
        tbEditor.add(cmbColorG);
        tbEditor.add(cmbColorB);
        tbEditor.add(lblColor);

        btnSeleccionar.setIcon(new ImageIcon(getClass().getResource("/iconos/Seleccionar.png")));
        btnSeleccionar.setToolTipText("Agregar");
        tbEditor.add(btnSeleccionar);

        btnEliminar.setIcon(new ImageIcon(getClass().getResource("/iconos/Eliminar.png")));
        btnEliminar.setToolTipText("Eliminar");
        tbEditor.add(btnEliminar);

        btnDibujar.setIcon(new ImageIcon(getClass().getResource("/iconos/Dibujar.png")));
        btnDibujar.setToolTipText("Dibujar");
        tbEditor.add(btnDibujar);

        pnlGrafica.setPreferredSize(new Dimension(300, 200));

        getContentPane().add(tbEditor, BorderLayout.NORTH);
        getContentPane().add(pnlGrafica, BorderLayout.CENTER);

        setColorTrazo();

    }

    // getters

    public TipoTrazo getTipoTrazoSeleccionado() {
        return (TipoTrazo) cmbTipo.getSelectedItem();
    }

    public Graphics getGraficadorPanel() {
        return pnlGrafica.getGraphics();
    }

    public int getAnchoPanel() {
        return pnlGrafica.getWidth();
    }

    public int getAltoPanel() {
        return pnlGrafica.getHeight();
    }


    public Color getColorTrazo() {
        return color;
    }
    //setters

    public void setIniciarLienzo(WindowAdapter eventoVentana) {
        addWindowListener(eventoVentana);
    }

    public void setClickPanelGrafica(MouseAdapter eventoRaton) {
        pnlGrafica.addMouseListener(eventoRaton);
        System.out.println("se asigna evento click");
    }

    public void setMovimientoRatonPanelGrafica(MouseMotionAdapter eventoRaton) {
        pnlGrafica.addMouseMotionListener(eventoRaton);
        System.out.println("se asigna evento movimiento raton");
    }

    public void setGuardarDibujo(ActionListener evento) {
        btnGuardar.addActionListener(evento);
    }

    public void setCargarDibujo(ActionListener evento) {
        btnCargar.addActionListener(evento);
    }

    public void setSeleccionarTrazo(ActionListener evento) {
        btnSeleccionar.addActionListener(evento);
    }

    public void setEliminarTrazo(ActionListener evento) {
        btnEliminar.addActionListener(evento);
    }

    public void setDibujar(ActionListener evento) {
        btnDibujar.addActionListener(evento);
    }

    private void setColorTrazo() {
        color = new Color(cmbColorR.getSelectedIndex(), cmbColorG.getSelectedIndex(),
                cmbColorB.getSelectedIndex());
        lblColor.setBackground(color);
    }

    // metodos publicos

    public boolean confirmar(String mensaje) {
        return JOptionPane.showConfirmDialog(null, mensaje, "",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    public void asignarCursor(Cursor cursor){
        pnlGrafica.setCursor(cursor);
    }
}

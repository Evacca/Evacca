package co.edu.utp.misiontic2022.c2.cdiaz.view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import co.edu.utp.misiontic2022.c2.cdiaz.controller.ReportesController;
import co.edu.utp.misiontic2022.c2.cdiaz.model.vo.ComprasDeLiderVo;
import co.edu.utp.misiontic2022.c2.cdiaz.model.vo.DeudasPorProyectoVo;
import co.edu.utp.misiontic2022.c2.cdiaz.model.vo.ProyectoBancoVo;


public class ReportesGUI extends JFrame {

    public JTable tabla;
    private ReportesController controller;

    public ReportesGUI() {
      
        controller = new ReportesController();

        initUI();
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void initUI() {

        setTitle("MisionTIC 2022 CICLO 2 - RETO 5");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        var tabpane = new JTabbedPane();
        getContentPane().add(tabpane, BorderLayout.BEFORE_FIRST_LINE);

        var panelconsulta1 = new JPanel();
        panelconsulta1.setLayout(new BorderLayout());
        tituloConsulta1(panelconsulta1);
        tabpane.addTab("Consulta 1", panelconsulta1);

        var botonEjecutar1 = new JButton("Generar tabla");
        botonEjecutar1.addActionListener(e -> iniciarConsulta1(panelconsulta1));          
        panelconsulta1.add(botonEjecutar1);

        var panelconsulta2 = new JPanel();
        panelconsulta2.setLayout(new BorderLayout());
        tituloConsulta2(panelconsulta2);
        tabpane.addTab("Consulta 2", panelconsulta2);        

        var botonEjecutar2 = new JButton("Generar tabla");
        botonEjecutar2.addActionListener(e -> iniciarConsulta2(panelconsulta2));
        panelconsulta2.add(botonEjecutar2);  

        var panelconsulta3 = new JPanel();
        panelconsulta3.setLayout(new BorderLayout());
        tituloConsulta3(panelconsulta3);
        tabpane.addTab("Consulta 3", panelconsulta3);

        var botonEjecutar3 = new JButton("Generar tabla");
        botonEjecutar3.addActionListener(e -> iniciarConsulta3(panelconsulta3));
        panelconsulta3.add(botonEjecutar3);

        var panelTabla = new JPanel();
        panelTabla.setLayout(new BorderLayout());        
        panelTabla.add(new JScrollPane(new JTable()), BorderLayout.PAGE_START);
        getContentPane().add(panelTabla, BorderLayout.CENTER);        
   
    }

    private void tituloConsulta1(JPanel panel1) {
        var etiqueta = new JLabel("Información de los proyectos financiados por un banco");
        etiqueta.setFont(new java.awt.Font("Segoe UI", 2, 24));
        etiqueta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panel1.add(etiqueta, BorderLayout.PAGE_START);
    }

    private void iniciarConsulta1(JPanel panel1) {
        try {
              
            var verTabla = new VerTablaProyectos();
            var lista = controller.listarProyectosPorBanco("conavi");
            verTabla.setData(lista);
            tabla.setModel(verTabla);            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public class VerTablaProyectos extends AbstractTableModel {
        private List<ProyectoBancoVo> proyectos;

        public VerTablaProyectos(){
            proyectos = new ArrayList<>();
        }

        public void setData(List<ProyectoBancoVo> data) {
            proyectos = data;
            fireTableDataChanged();
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 2:
                    return Integer.class;
                default:
                    return String.class;
            }
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "ID";
                case 1:
                    return "CONSTRUCTORA";
                case 2: 
                    return "CIUDAD";
                case 3:
                    return "CLASIFICACION";
                case 4:
                    return "ESTRATO";
                case 5:
                    return "LIDER";
            }
            return super.getColumnName(column);
        }

        @Override
        public int getRowCount() {
            return proyectos.size();
        }

        @Override
        public int getColumnCount() {
            return 6;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            var proyecto = proyectos.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return proyecto.getId_Proyecto();
                case 1:
                    return proyecto.getConstructora();
                case 2:
                    return proyecto.getCiudad();
                case 3:
                    return proyecto.getClasificacion();
                case 4:
                    return proyecto.getEstrato();
                case 5:
                    return proyecto.getLider();
            }
            return null;
        }
    }

    private void tituloConsulta2(JPanel panel2) {

        var etiqueta = new JLabel("Listado del total adeudado de cada proyecto");
        etiqueta.setFont(new java.awt.Font("Segoe UI", 2, 24));
        etiqueta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panel2.add(etiqueta, BorderLayout.PAGE_START);         
    }

    private void iniciarConsulta2(JPanel panel2) {
        try {
              
            var verTabla = new VerDeudasProyectos();
            var lista = controller.listarDeudasProyecto(50000d);            
            verTabla.setData(lista);
            tabla.setModel(verTabla);            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private class VerDeudasProyectos extends AbstractTableModel {
        private List<DeudasPorProyectoVo> proyectos;

        public VerDeudasProyectos(){
            proyectos = new ArrayList<>();
        }

        public void setData(List<DeudasPorProyectoVo> data) {
            proyectos = data;
            fireTableDataChanged();
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 2:
                    return Integer.class;
                default:
                    return Double.class;
            }
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "ID";
                case 1:
                    return "VALOR";
            }
            return super.getColumnName(column);
        }

        @Override
        public int getRowCount() {
            return proyectos.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            var proyecto = proyectos.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return proyecto.getid_Proyecto();
                case 1:
                    return proyecto.getValor();
            }
            return null;
        }
    }

    private void tituloConsulta3(JPanel panel3) {

        var etiqueta = new JLabel("El top 10 de los líderes que más gastan en sus proyectos");
        etiqueta.setFont(new java.awt.Font("Segoe UI", 2, 24));
        etiqueta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panel3.add(etiqueta, BorderLayout.PAGE_START);      
    }
    
    private void iniciarConsulta3(JPanel panel3) {
        try {
              
            var verTabla = new VerComprasLideresProyectos();
            var lista = controller.listarComprasLider();
            verTabla.setData(lista);
            tabla.setModel(verTabla);            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private class VerComprasLideresProyectos extends AbstractTableModel {
        private List<ComprasDeLiderVo> proyectos;

        public VerComprasLideresProyectos(){
            proyectos = new ArrayList<>();
        }

        public void setData(List<ComprasDeLiderVo> data) {
            proyectos = data;
            fireTableDataChanged();
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 2:
                    return String.class;
                default:
                    return Double.class;
            }
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "LIDER";
                case 1:
                    return "VALOR";
            }
            return super.getColumnName(column);
        }

        @Override
        public int getRowCount() {
            return proyectos.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            var proyecto = proyectos.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return proyecto.getLider();
                case 1:
                    return proyecto.getValor();
            }
            return null;
        }

    }
}
    

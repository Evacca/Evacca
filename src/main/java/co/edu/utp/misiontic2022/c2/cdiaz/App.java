package co.edu.utp.misiontic2022.c2.cdiaz;

import java.sql.SQLException;

import co.edu.utp.misiontic2022.c2.cdiaz.view.ReportesGUI;
//import co.edu.utp.misiontic2022.c2.cdiaz.view.ReportesView;


public class App {
    public static void main(String[] args) throws SQLException {
        //var reportesView = new ReportesView();
        //var banco = "Conavi";
        //reportesView.proyectosFinanciadosPorBanco(banco);

        //var reportesView = new ReportesView();
        //var limiteInferior = 50_000d;
        //reportesView.totalAdeudadoPorProyectosSuperioresALimite(limiteInferior);

        //var reportesView = new ReportesView();
        //reportesView.lideresQueMasGastan();

        var frm = new ReportesGUI();
        frm.setVisible(true);
    }
}



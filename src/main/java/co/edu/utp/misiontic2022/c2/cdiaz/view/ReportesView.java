package co.edu.utp.misiontic2022.c2.cdiaz.view;

import java.sql.SQLException;

import co.edu.utp.misiontic2022.c2.cdiaz.controller.ReportesController;
import co.edu.utp.misiontic2022.c2.cdiaz.model.vo.ComprasDeLiderVo;
import co.edu.utp.misiontic2022.c2.cdiaz.model.vo.DeudasPorProyectoVo;
import co.edu.utp.misiontic2022.c2.cdiaz.model.vo.ProyectoBancoVo;


public class ReportesView {
    private ReportesController controller;

    public ReportesView() {
        controller = new ReportesController();
    }

    private String repitaCaracter(Character caracter, Integer veces) {
        var respuesta = "";
        for (int i = 0; i < veces; i++) {
            respuesta += caracter;
        }
        return respuesta;
    }

    public void proyectosFinanciadosPorBanco(String banco) {
        try {
            System.out.println(repitaCaracter('=', 36) + " LISTADO DE PROYECTOS POR BANCO " + repitaCaracter('=', 37)); 
            if (banco != null && !banco.isBlank()) { 
                System.out.println(String.format("%3s %-25s %-20s %-15s %-7s %-30s", "ID", "CONSTRUCTORA", "CIUDAD", "CLASIFICACION", "ESTRATO", "LIDER")); 
                System.out.println(repitaCaracter('-', 105)); 
              
                var lista = controller.listarProyectosPorBanco(banco);
                for (ProyectoBancoVo ProyectoBancoVo : lista) {
                    System.out.printf("%3s %-25s %-20s %-15s %7s %-30s %n", ProyectoBancoVo.getId_Proyecto(), ProyectoBancoVo.getConstructora(), ProyectoBancoVo.getCiudad(), ProyectoBancoVo.getClasificacion(), 
                    ProyectoBancoVo.getEstrato(), ProyectoBancoVo.getLider());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e);
            e.printStackTrace();
        }
    }

    public void totalAdeudadoPorProyectosSuperioresALimite(Double limiteInferior) {
        try {
            System.out.println(repitaCaracter('=', 1) + " TOTAL DEUDAS POR PROYECTO " + repitaCaracter('=', 1)); 
			if (limiteInferior != null) { 
			System.out.println(String.format("%3s %15s", "ID", "VALOR  ")); System.out.println(repitaCaracter('-', 29)); 
                
                var lista = controller.listarDeudasProyecto(limiteInferior);
                for (DeudasPorProyectoVo deudasPorProyectoVo : lista) {
                    System.out.printf("%3d %,15.1f %n", deudasPorProyectoVo.getid_Proyecto(), deudasPorProyectoVo.getValor());
					
                }
            }    
        } catch (SQLException e) {
            System.err.println("Error: " + e);
            e.printStackTrace();
        }
    }

    public void lideresQueMasGastan() {
        try {
            System.out.println(repitaCaracter('=', 6) + " 10 LIDERES MAS COMPRADORES " + repitaCaracter('=', 7)); 
            System.out.println(String.format("%-25s %15s", "LIDER", "VALOR  ")); 
            System.out.println(repitaCaracter('-', 41));
            
            var lista = controller.listarComprasLider();
            for (ComprasDeLiderVo comprasDeLiderVo : lista) {
                System.out.printf("%-25s %,15.1f %n", comprasDeLiderVo.getLider(), comprasDeLiderVo.getValor());
            }
        
        } catch (SQLException e) {
            System.err.println("Error: " + e);
            e.printStackTrace();
        }
    }
}
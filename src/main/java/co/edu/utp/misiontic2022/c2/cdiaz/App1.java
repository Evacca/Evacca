package co.edu.utp.misiontic2022.c2.cdiaz;

import java.sql.SQLException;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;

public class App1 {

    public static class JDBCUtilities {
    
        private static final String UBICACION_BD = "ProyectosConstruccion.db";
 
        public static Connection getConnection() throws SQLException {
            String url = "jdbc:sqlite:" + UBICACION_BD;
            return DriverManager.getConnection(url);
        }
    }

    public class ReportesController {

        private ProyectoBancoDao proyectoBancoDao;
        private DeudasPorProyectoDao deudasPorProyectoDao;
        private ComprasDeLiderDao comprasDeLiderDao;

        public ReportesController() {
            proyectoBancoDao = new ProyectoBancoDao();
            deudasPorProyectoDao = new DeudasPorProyectoDao();
            comprasDeLiderDao = new ComprasDeLiderDao();
        }

        public List<ProyectoBancoVo> listarProyectosPorBanco(String banco) throws SQLException {
            return proyectoBancoDao.listarProyectos(banco);
        }

        public List<DeudasPorProyectoVo> listarDeudasProyecto(Double limiteInferior) throws SQLException {
            return deudasPorProyectoDao.deudasPorProyecto(limiteInferior);
        }

        public List<ComprasDeLiderVo> listarComprasLider() throws SQLException {
            return comprasDeLiderDao.listaCompraPorLideres();
        }

    }

    public class ComprasDeLiderDao {
        public List<ComprasDeLiderVo> listaCompraPorLideres() throws SQLException {

            List<ComprasDeLiderVo> respuesta = new ArrayList<>();
            var conn = JDBCUtilities.getConnection();

            PreparedStatement stmt = null;
            ResultSet rset = null;

            try {
                var query = " SELECT L.NOMBRE ||' '|| L.PRIMER_APELLIDO ||' '|| L.SEGUNDO_APELLIDO LIDER, SUM(C.CANTIDAD * MC.PRECIO_UNIDAD) VALOR"
                            + " FROM LIDER L"
                            + " JOIN PROYECTO P ON (L.ID_LIDER = P.ID_LIDER)"
                            + " JOIN COMPRA C ON (P.ID_PROYECTO = C.ID_PROYECTO)"
                            + " JOIN MATERIALCONSTRUCCION MC ON (C.ID_MATERIALCONSTRUCCION = MC.ID_MATERIALCONSTRUCCION)"
                            + " GROUP BY LIDER"
                            + " ORDER BY VALOR DESC"
                            + " LIMIT 10";

                stmt = conn.prepareStatement(query);
                rset = stmt.executeQuery();

                while (rset.next()) {
                    var vo = new ComprasDeLiderVo();
                    vo.setLider(rset.getString("LIDER"));
                    vo.setValor(rset.getInt("VALOR"));

                    respuesta.add(vo);
                }

            } catch (SQLException e) {
                System.err.println("Error: " + e);
                e.printStackTrace();
            }
            return respuesta;
        }
    }

    public class DeudasPorProyectoDao {
        public List<DeudasPorProyectoVo> deudasPorProyecto(Double limiteInferior) throws SQLException {

            List<DeudasPorProyectoVo> respuesta = new ArrayList<>();
            var conn = JDBCUtilities.getConnection();

            PreparedStatement stmt = null;
            ResultSet rset = null;
        
            try {
                var query = " SELECT C.ID_PROYECTO ID, SUM(MC.PRECIO_UNIDAD * C.CANTIDAD) VALOR"
                            + " FROM COMPRA C"
                            + " JOIN MATERIALCONSTRUCCION MC ON (C.ID_MATERIALCONSTRUCCION = MC.ID_MATERIALCONSTRUCCION)"
                            + " JOIN PROYECTO P ON (C.ID_PROYECTO = p.ID_PROYECTO)"
                            + " WHERE (C.PAGADO = 'No')"
                            + " GROUP BY C.ID_PROYECTO"
                            + " HAVING SUM(MC.PRECIO_UNIDAD * C.CANTIDAD) > (?)"
                            + " ORDER BY VALOR DESC";

                stmt = conn.prepareStatement(query);
                stmt.setDouble(1,limiteInferior);
                rset = stmt.executeQuery();

                while (rset.next()) {
                    var vo = new DeudasPorProyectoVo();
                    vo.setIdProyecto(rset.getInt("ID"));
                    vo.setValor(rset.getInt("VALOR"));

                    respuesta.add(vo);                
                } 
                
            } finally {
                if (rset != null) {
                    rset.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }
            return respuesta;
        }
    }

    public class ProyectoBancoDao {
        public List<ProyectoBancoVo> listarProyectos(String banco) throws SQLException {

            List<ProyectoBancoVo> respuesta = new ArrayList<>();
            var conn = JDBCUtilities.getConnection();
            
            PreparedStatement stmt = null;
            ResultSet rset = null;
            
            try {
                var query = " SELECT P.ID_PROYECTO ID, P.CONSTRUCTORA,P.CIUDAD, P.CLASIFICACION, T.ESTRATO, L.NOMBRE ||' '|| L.PRIMER_APELLIDO ||' '|| L.SEGUNDO_APELLIDO LIDER"
                            + " FROM PROYECTO P"
                            + " JOIN TIPO T ON (P.ID_TIPO = T.ID_TIPO)" 
                            + " JOIN LIDER L ON (P.ID_LIDER = L.ID_LIDER)"
                            + " WHERE P.BANCO_VINCULADO = (?)"
                            + " ORDER BY P.FECHA_INICIO DESC, CIUDAD ASC, CONSTRUCTORA";
                            
                stmt = conn.prepareStatement(query);
                stmt.setString(1, banco);
                rset = stmt.executeQuery();

                while (rset.next()) {
                    var vo = new ProyectoBancoVo();
                    vo.setId_Proyecto(rset.getInt("ID"));
                    vo.setConstructora(rset.getString("CONSTRUCTORA"));
                    vo.setCiudad(rset.getString("CIUDAD"));
                    vo.setClasificacion(rset.getString("CLASIFICACION"));
                    vo.setEstrato(rset.getInt("ESTRATO"));
                    vo.setLider(rset.getString("LIDER"));

                    respuesta.add(vo);
                }
                
            } finally {
                if (rset != null) {
                    rset.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }
            return respuesta;
        }
    }

    public class ComprasDeLiderVo {
        private String lider;
        private Integer valor;
        
        public String getLider() {
            return lider;
        }
        
        public void setLider(String lider) {
            this.lider = lider;
        }
        
        public Integer getValor() {
            return valor;
        }
        
        public void setValor(Integer valor) {
            this.valor = valor;
        }
    
        @Override
        public String toString() {
            return "ComprasDeLiderVo [lider=" + lider + ", valor=" + valor + "]";
        }
    }

    public class DeudasPorProyectoVo {

        private Integer id_Proyecto;
        private Integer valor;
        
        public Integer getid_Proyecto() {
            return  id_Proyecto;
        }
        
        public void setIdProyecto(Integer id_Proyecto) {
            this.id_Proyecto = id_Proyecto;
        }
        
        public Integer getValor() {
            return valor;
        }
        
        public void setValor(Integer valor) {
            this.valor = valor;
        }
    
        @Override
        public String toString() {
            return "DeudasPorProyectoVo [id_Proyecto=" + id_Proyecto + ", valor=" + valor + "]";
        }
           
    }

    public class ProyectoBancoVo {
        private Integer id_Proyecto;
        private String constructora;
        private String ciudad;
        private String clasificacion;
        private Integer estrato;
        private String lider;
        
        public Integer getId_Proyecto() {
            return id_Proyecto;
        }
        
        public void setId_Proyecto(Integer id_Proyecto) {
            this.id_Proyecto = id_Proyecto;
        }
        
        public String getConstructora() {
            return constructora;
        }
        
        public void setConstructora(String constructora) {
            this.constructora = constructora;
        }
        
        public String getCiudad() {
            return ciudad;
        }
        
        public void setCiudad(String ciudad) {
            this.ciudad = ciudad;
        }
        
        public String getClasificacion() {
            return clasificacion;
        }
        
        public void setClasificacion(String clasificacion) {
            this.clasificacion = clasificacion;
        }
        
        public Integer getEstrato() {
            return estrato;
        }
        
        public void setEstrato(Integer estrato) {
            this.estrato = estrato;
        }
        
        public String getLider() {
            return lider;
        }
        
        public void setLider(String lider) {
            this.lider = lider;
        }
    
        @Override
        public String toString() {
            return "ProyectoBancoVo [ciudad=" + ciudad + ", clasificacion=" + clasificacion + ", constructora="
                    + constructora + ", estrato=" + estrato + ", id_Proyecto=" + id_Proyecto + ", lider=" + lider + "]";
        }
    }

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
                        System.out.printf("%3s %-25s %-20s %-15s %-7s %-30s %n", ProyectoBancoVo.getId_Proyecto(), ProyectoBancoVo.getConstructora(), ProyectoBancoVo.getCiudad(), ProyectoBancoVo.getClasificacion(), 
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
                    System.out.println(String.format("%3s %15s", "ID", "VALOR ")); 
                    System.out.println(repitaCaracter('-', 29)); 
                    
                    var lista = controller.listarDeudasProyecto(limiteInferior);
                    for (DeudasPorProyectoVo deudasPorProyectoVo : lista) {
                        System.out.println(String.format("%3s %15s", deudasPorProyectoVo.getid_Proyecto(), deudasPorProyectoVo.getValor()));
                    }
                }    
            } catch (SQLException e) {
                System.err.println("Error: " + e);
                e.printStackTrace();
            }
        }
    
        public void lideresQueMasGastan() throws SQLException { 
            System.out.println(repitaCaracter('=', 6) + " 10 LIDERES MAS COMPRADORES " + repitaCaracter('=', 7)); 
            System.out.println(String.format("%-25s %15s", "LIDER", "VALOR ")); 
            System.out.println(repitaCaracter('-', 41));
                
            var lista = controller.listarComprasLider();
            for (ComprasDeLiderVo comprasDeLiderVo : lista) {
                System.out.println(String.format("%-25s %15s", comprasDeLiderVo.getLider(), comprasDeLiderVo.getValor()));
            }
        }
    }
    

}


    


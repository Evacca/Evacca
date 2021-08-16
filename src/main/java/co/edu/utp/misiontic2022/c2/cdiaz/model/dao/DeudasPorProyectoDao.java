package co.edu.utp.misiontic2022.c2.cdiaz.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import co.edu.utp.misiontic2022.c2.cdiaz.model.vo.DeudasPorProyectoVo;
import co.edu.utp.misiontic2022.c2.cdiaz.util.JDBCUtilities;

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

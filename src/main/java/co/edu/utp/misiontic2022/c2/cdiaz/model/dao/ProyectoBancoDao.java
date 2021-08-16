package co.edu.utp.misiontic2022.c2.cdiaz.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import co.edu.utp.misiontic2022.c2.cdiaz.model.vo.ProyectoBancoVo;
import co.edu.utp.misiontic2022.c2.cdiaz.util.JDBCUtilities;

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
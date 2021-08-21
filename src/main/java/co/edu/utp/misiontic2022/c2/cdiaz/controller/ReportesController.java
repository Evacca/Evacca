package co.edu.utp.misiontic2022.c2.cdiaz.controller;

import java.sql.SQLException;
import java.util.List;

import co.edu.utp.misiontic2022.c2.cdiaz.model.dao.ComprasDeLiderDao;
import co.edu.utp.misiontic2022.c2.cdiaz.model.dao.DeudasPorProyectoDao;
import co.edu.utp.misiontic2022.c2.cdiaz.model.dao.ProyectoBancoDao;
import co.edu.utp.misiontic2022.c2.cdiaz.model.vo.ComprasDeLiderVo;
import co.edu.utp.misiontic2022.c2.cdiaz.model.vo.DeudasPorProyectoVo;
import co.edu.utp.misiontic2022.c2.cdiaz.model.vo.ProyectoBancoVo;


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

    public Object listarProyectosPorBanco() {
        return null;
    }

}



package co.edu.utp.misiontic2022.c2.cdiaz.controller;

import java.sql.SQLException;
import java.util.List;

import co.edu.utp.misiontic2022.c2.cdiaz.model.dao.ComprasDeLiderDao;
import co.edu.utp.misiontic2022.c2.cdiaz.model.dao.DeudasPorProyectoDao;
import co.edu.utp.misiontic2022.c2.cdiaz.model.dao.ProyectoBancoDao;
import co.edu.utp.misiontic2022.c2.cdiaz.model.dao.ProyectosDao;
import co.edu.utp.misiontic2022.c2.cdiaz.model.vo.ComprasDeLiderVo;
import co.edu.utp.misiontic2022.c2.cdiaz.model.vo.DeudasPorProyectoVo;
import co.edu.utp.misiontic2022.c2.cdiaz.model.vo.ProyectoBancoVo;
import co.edu.utp.misiontic2022.c2.cdiaz.model.vo.ProyectosVo;

public class ReportesController {

    private ProyectoBancoDao proyectoBancoDao;
    private DeudasPorProyectoDao deudasPorProyectoDao;
    private ComprasDeLiderDao comprasDeLiderDao;
    private ProyectosDao proyectosDao;

    public ReportesController() {
        proyectoBancoDao = new ProyectoBancoDao();
        deudasPorProyectoDao = new DeudasPorProyectoDao();
        comprasDeLiderDao = new ComprasDeLiderDao();
        proyectosDao = new ProyectosDao();
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

    public List<ProyectosVo> listarProyectosExcluyendoClasificaciones(String clasificacion1, String clasificacion2) throws SQLException {
        return proyectosDao.listarProyectos(clasificacion1, clasificacion2);
    }

}



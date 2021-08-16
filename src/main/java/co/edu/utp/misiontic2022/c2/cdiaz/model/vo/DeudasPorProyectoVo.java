package co.edu.utp.misiontic2022.c2.cdiaz.model.vo;

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

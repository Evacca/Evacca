package co.edu.utp.misiontic2022.c2.cdiaz.model.vo;

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

    

   
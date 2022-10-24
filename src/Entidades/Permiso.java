
package Entidades;


public class Permiso
{
    private int id;
    private String descripcion;
    private String orde;
    private String estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOrde() {
        return orde;
    }

    public void setOrde(String orde) {
        this.orde = orde;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}

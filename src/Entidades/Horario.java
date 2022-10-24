package Entidades;


public class Horario {
    private int id;
    private int HoraInicio;
    private int FechaIncio;
    private int idpersonal;
    private int horaIngreso;
    private String estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHoraInicio() {
        return HoraInicio;
    }

    public void setHoraInicio(int HoraInicio) {
        this.HoraInicio = HoraInicio;
    }

    public int getFechaIncio() {
        return FechaIncio;
    }

    public void setFechaIncio(int FechaIncio) {
        this.FechaIncio = FechaIncio;
    }

    public int getIdpersonal() {
        return idpersonal;
    }

    public void setIdpersonal(int idpersonal) {
        this.idpersonal = idpersonal;
    }

    public int getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(int horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}

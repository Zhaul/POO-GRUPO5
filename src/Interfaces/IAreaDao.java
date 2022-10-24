package Interfaces;

import Entidades.Area;
import java.util.List;

public interface IAreaDao {

    public boolean registrar(Area area);

    public List<Area> obtener(String filtro);

    public boolean actualizar(Area area);

    public boolean eliminar(Area area);
}

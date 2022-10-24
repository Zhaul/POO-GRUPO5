package Datos;

import Entidades.Area;
import Interfaces.IAreaDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class AreaDao implements IAreaDao {

    ConexionBD conexionBD = new ConexionBD();

    @Override
    public List<Area> obtener(String filtro) {

        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Areas ORDER BY id";
        List<Area> listaArea = new ArrayList<Area>();

        try {

            con = conexionBD.getConexion();
            stm = con.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                Area a = new Area();
                a.setId(rs.getInt("id"));
                a.setNombre(rs.getString("nombre"));
                a.setFunciones(rs.getString("funciones"));
                a.setEstado(rs.getString("estado"));
                listaArea.add(a);
            }
            stm.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: Clase AreaDaoImple, método obtener");
            e.printStackTrace();
        }

        return listaArea;
    }

    @Override
    public boolean registrar(Area area) {

        boolean retorno = false;
        PreparedStatement pst = null;
        Connection con = null;

        String sql = "INSERT INTO Area (nombre, funciones, estado) values (?,?,?)";

        try {

            con = conexionBD.getConexion();
            pst = con.prepareStatement(sql);
            pst.setString(1, area.getNombre());
            pst.setString(2, area.getFunciones());
            pst.setString(3, area.getEstado());
            pst.execute();
            retorno = true;
            pst.close();
            con.close();
            
        } catch (SQLException e) {
            System.out.println("Error: Clase AreaDaoImple, método registrar");
            e.printStackTrace();
        }
        return retorno;
    }

    @Override
    public boolean actualizar(Area area) {
        boolean retorno = false;
        PreparedStatement pst = null;
        Connection con = null;

        String sql = "UPDATE Area set nombre = ?, funciones = ?, estado = ? where id = ?";

        try {

            con = conexionBD.getConexion();
            pst = con.prepareStatement(sql);
            pst.setString(1, area.getNombre());
            pst.setString(2, area.getFunciones());
            pst.setString(3, area.getEstado());
            pst.setInt(4, area.getId());
            pst.execute();
            retorno = true;
            pst.close();
            con.close();
            
        } catch (SQLException e) {
            System.out.println("Error: Clase AreaDaoImple, método actualizar");
            e.printStackTrace();
        }
        return retorno;
    }

    @Override
    public boolean eliminar(Area area) {
        
        boolean retorno = false;
        PreparedStatement pst = null;
        Connection con = null;

        String sql = "DELETE FROM Area where id = ?";

        try {

            con = conexionBD.getConexion();
            pst.setInt(1, area.getId());
            pst.execute();
            retorno = true;
            pst.close();
            con.close();
            
        } catch (SQLException e) {
            System.out.println("Error: Clase AreaDaoImple, método eliminar");
            e.printStackTrace();
        }
        return retorno;
    }

}

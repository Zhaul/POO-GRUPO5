
package Datos;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {
    
    String DRIVER = "com.mysql.cj.jdbc.Driver";
    String USER = "proyectopoo";
    String PASSWORD = "123";
    String URL = "jdbc:mysql://localhost:3306/proyectofinal";
    static Connection con;

    public ConexionBD() {
        con = null;
        try {
            Class.forName(DRIVER);
            con = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public Connection getConexion(){
        return con;
    }
    
    public void logoutConexion(){
        con = null;
        if (con == null) {
            System.out.println("Conexión Terminada.");
        }
    }
    
}

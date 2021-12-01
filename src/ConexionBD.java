import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author black
 */
public class ConexionBD {
  Connection conexion;
    Statement transaccion;
    ResultSet cursor;

    public ConexionBD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/tap_u4_practica1_mysql?zeroDateTimeBehavior=convertToNull", "root", "");
            transaccion = conexion.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean insertar(Producto producto) {
        String SQL_Insert = "INSERT INTO PRODUCTOS VALUES(NULL, '%DES%', '%PRE%','%EXI%')";

        SQL_Insert = SQL_Insert.replaceAll("%DES%", producto.descripcion);
        SQL_Insert = SQL_Insert.replaceAll("%PRE%", producto.precio+"");
        SQL_Insert = SQL_Insert.replaceAll("%EXI%", producto.existencia+"");

        try {
            transaccion.execute(SQL_Insert);
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

    public ArrayList<String[]> consultarTodos() {
        ArrayList<String[]> resultado = new ArrayList<String[]>();
        try {
            cursor = transaccion.executeQuery("SELECT * FROM PRODUCTOS");
            if (cursor.next()) {
                do {
                    String[] renglon = {cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4)};
                    resultado.add(renglon);
                } while (cursor.next());
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    public Producto consultarID(String ID) {
        Producto productoResultado = new Producto();

        try {
            cursor = transaccion.executeQuery("SELECT * FROM PRODUCTOS WHERE IDPRODUCTO=" + ID);
            if (cursor.next()) {
                productoResultado.descripcion = cursor.getString(2);
                productoResultado.precio = cursor.getFloat(3);
                productoResultado.existencia = cursor.getInt(4);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productoResultado;
    }

    public boolean eliminar(String ID) {
        try {
            transaccion.execute("DELETE FROM PRODUCTOS WHERE IDPRODUCTO=" + ID);
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

    public boolean update(Producto producto) {
        String SQL_Update = "UPDATE PRODUCTOS SET DESCRIPCION = '%DES%', PRECIO = '%PRE%', EXISTENCIA = '%EXI%' WHERE IDPRODUCTO="+producto.idProducto;

        SQL_Update = SQL_Update.replaceAll("%DES%", producto.descripcion);
        SQL_Update = SQL_Update.replaceAll("%PRE%", producto.precio+"");
        SQL_Update = SQL_Update.replaceAll("%EXI%", producto.existencia+"");

        try {
            transaccion.execute(SQL_Update);
        } catch (SQLException ex) {
            System.out.print(ex);
            return false;
        }
        return true;
    }   
}

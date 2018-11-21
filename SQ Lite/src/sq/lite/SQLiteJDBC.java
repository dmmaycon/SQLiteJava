/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sq.lite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Maycon de Moraes
 */
public class SQLiteJDBC {
    
    public static Connection connect(String path)
    {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:"+path);
            System.out.println("Conexão realizada com sucesso!");
            return conn;
        } catch (Exception e){
            System.err.println("Erro ao se conectar no banco!");
            System.err.println("Classe: " + e.getClass().getName() + " Erro: " + e.getMessage());
        }
        return null;
    }
    
    
    /**
     * Recebe qualquer instução sql e executa no banco
     * @param sql
     * @param conn
     * @return 
     */
    public static boolean inserir(String sql, Connection conn)
    {
        Statement stmt = null;
        try {
            conn.setAutoCommit(false);
            
            stmt = conn.createStatement();
            if (sql != "") {
                stmt.executeUpdate(sql);
            }
            stmt.close();
            conn.commit();
            conn.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
    /**
     * Recebe qualquer instução sql e executa no banco
     * e retorna os dados do selec. 
     * @param sql
     * @param conn
     * @return 
     */
    public static ArrayList<String[]> listar(String sql, Connection conn)
    {
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<String[]> result = new ArrayList();
        try {
            conn.setAutoCommit(false);
            
            stmt = conn.createStatement();
            if (sql != "") {
                rs = stmt.executeQuery(sql);
                
                while(rs.next()) {
                    // parametro fixo
                    result.add(new String[]{Integer.toString(rs.getInt("id")), rs.getString("nome")});
                }                
            }
            rs.close();
            stmt.close();
            conn.close();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    
    
}

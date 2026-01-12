/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pbo.koneksi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author riska_ti
 */
public class Koneksi {
    private static Connection koneksi;

    public static Connection getKoneksi() {
        if (koneksi == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/pbo_2310010362"; 
                String user = "root"; 
                String password = ""; 

                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                koneksi = DriverManager.getConnection(url, user, password);

                System.out.println("Koneksi Database Berhasil!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Koneksi Database Gagal! " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Koneksi Gagal: " + e.getMessage());
            }
        }
        return koneksi;
    }
    
    public static void main(String[] args){
        
    }
}

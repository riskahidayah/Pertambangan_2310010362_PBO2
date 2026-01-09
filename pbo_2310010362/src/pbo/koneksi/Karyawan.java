/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pbo.koneksi;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author riska_ti
 */
public class Karyawan {
    public void tampilData(JTable tabel, String sql) {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID Karyawan");
        model.addColumn("Nama Karyawan");
        model.addColumn("Jabatan");
        model.addColumn("Alamat");
        model.addColumn("No. Telp");

        try {
            Connection conn = Koneksi.getKoneksi();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("ID_Karyawan"),
                    rs.getString("Nama_Karyawan"),
                    rs.getString("Jabatan"),
                    rs.getString("Alamat_Karyawan"),
                    rs.getString("No_telp_Karyawan")
                });
            }

            tabel.setModel(model);

        } catch (Exception e) {
            System.out.println("Error tampil karyawan: " + e.getMessage());
        }
    }
    
}

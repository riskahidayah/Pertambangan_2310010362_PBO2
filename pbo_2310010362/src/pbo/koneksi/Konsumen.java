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
public class Konsumen {
     public void tampilData(JTable tabel, String sql) {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID Konsumen");
        model.addColumn("Nama Perusahaan");
        model.addColumn("Alamat Perusahaan");
        model.addColumn("No. Telp Perusahaan");

        try {
            Connection conn = Koneksi.getKoneksi();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("ID_Konsumen"),
                    rs.getString("Nama_Perusahaan"),
                    rs.getString("Alamat_Perusahaan"),
                    rs.getString("No_telp_Perusahaan")
                });
            }

            tabel.setModel(model);

        } catch (Exception e) {
            System.out.println("Error tampil konsumen: " + e.getMessage());
        }
    }
}

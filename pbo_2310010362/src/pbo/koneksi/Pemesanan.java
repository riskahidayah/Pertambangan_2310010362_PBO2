/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pbo.koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
/**
 *
 * @author riska_ti
 */
public class Pemesanan {
    private final Connection koneksidb = Koneksi.getKoneksi();
    private Statement st;
    private ResultSet rs;
    private String sql;
    public boolean validasi = false;
    
    public void simpanPemesanan(String id_pesan, String tgl, String id_konsumen, String id_karyawan, String notes) {
        try {
            sql = "SELECT * FROM pemesanan WHERE ID_pemesanan = ?";
            PreparedStatement cekdata = koneksidb.prepareStatement(sql);
            cekdata.setString(1, id_pesan);
            ResultSet data = cekdata.executeQuery();

            if (data.next()) {
                JOptionPane.showMessageDialog(null, "ID Pemesanan Sudah Ada!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                this.validasi = false;
            } else {
                sql = "INSERT INTO pemesanan (ID_pemesanan, tanggal_pemesanan, ID_Konsumen, ID_Karyawan, Notes) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement perintah = koneksidb.prepareStatement(sql);
                perintah.setString(1, id_pesan);
                perintah.setString(2, tgl);
                perintah.setString(3, id_konsumen);
                perintah.setString(4, id_karyawan);
                perintah.setString(5, notes);
                perintah.execute();
                
                JOptionPane.showMessageDialog(null, "Data Pemesanan Berhasil Disimpan!");
                this.validasi = true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Pemesanan Gagal: " + e.getMessage());
            this.validasi = false;
        }
    }

    public void ubahPemesanan(String id_pesan, String tgl, String id_konsumen, String id_karyawan, String notes) {
        try {
            sql = "UPDATE pemesanan SET tanggal_pemesanan=?, ID_Konsumen=?, ID_Karyawan=?, Notes=? WHERE ID_pemesanan=?";
            PreparedStatement perintah = koneksidb.prepareStatement(sql);
            perintah.setString(1, tgl);
            perintah.setString(2, id_konsumen);
            perintah.setString(3, id_karyawan);
            perintah.setString(4, notes);
            perintah.setString(5, id_pesan);
            perintah.execute();
            
            JOptionPane.showMessageDialog(null, "Data Pemesanan Berhasil Diubah!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Perubahan Data Pemesanan Gagal: " + e.getMessage());
        }
    }

    public void hapusPemesanan(String id_pesan) {
        try {
            sql = "DELETE FROM pemesanan WHERE ID_pemesanan=?";
            PreparedStatement perintah = koneksidb.prepareStatement(sql);
            perintah.setString(1, id_pesan);
            perintah.execute();
            
            JOptionPane.showMessageDialog(null, "Data Pemesanan Berhasil Dihapus!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Penghapusan Data Pemesanan Gagal: " + e.getMessage());
        }
    }

    public void tampilData(JTable tbl) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Pemesanan");
        model.addColumn("Tanggal");
        model.addColumn("Konsumen"); 
        model.addColumn("Karyawan"); 
        model.addColumn("Notes");

        String sqlTampil = "SELECT p.ID_pemesanan, p.tanggal_pemesanan, k.Nama_Perusahaan, r.Nama_Karyawan, p.Notes " +
                           "FROM pemesanan p " +
                           "JOIN konsumen k ON p.ID_Konsumen = k.ID_Konsumen " +
                           "JOIN karyawan r ON p.ID_Karyawan = r.ID_Karyawan " +
                           "ORDER BY p.ID_pemesanan ASC";

        try {
            st = koneksidb.createStatement();
            rs = st.executeQuery(sqlTampil);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("ID_pemesanan"),
                    rs.getString("tanggal_pemesanan"),
                    rs.getString("Nama_Perusahaan"),
                    rs.getString("Nama_Karyawan"),
                    rs.getString("Notes")
                });
            }
            tbl.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal Tampilkan Data Pemesanan: " + e.getMessage());
        }
    }
    
    public void muatDataKonsumen(JComboBox<String> cmb) {
        cmb.removeAllItems();
        cmb.addItem("-- Pilih Konsumen --");
        try {
            sql = "SELECT ID_Konsumen, Nama_Perusahaan FROM konsumen ORDER BY Nama_Perusahaan ASC";
            st = koneksidb.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                cmb.addItem(rs.getString("Nama_Perusahaan") + " | " + rs.getString("ID_Konsumen"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data Konsumen: " + e.getMessage());
        }
    }
    
    public void muatDataKaryawan(JComboBox<String> cmb) {
        cmb.removeAllItems();
        cmb.addItem("-- Pilih Karyawan --");
        try {
            sql = "SELECT ID_Karyawan, Nama_Karyawan FROM karyawan ORDER BY Nama_Karyawan ASC";
            st = koneksidb.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                cmb.addItem(rs.getString("Nama_Karyawan") + " | " + rs.getString("ID_Karyawan"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data Karyawan: " + e.getMessage());
        }
    }
    
    public String getIdFromCmb(JComboBox<String> cmb) {
    String selectedItem = (String) cmb.getSelectedItem();
    if (selectedItem == null || selectedItem.startsWith("--")) {
        return ""; // kembalikan kosong kalau belum memilih
    }
    if (selectedItem.contains(" | ")) {
        return selectedItem.substring(selectedItem.lastIndexOf(" | ") + 3).trim();
    }
    return ""; 
}

    public void tampilData(JTable table, String sql) {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID Pemesanan");
    model.addColumn("Tanggal");
    model.addColumn("Konsumen");
    model.addColumn("Karyawan");
    model.addColumn("Notes");

    try {
        st = koneksidb.createStatement();
        rs = st.executeQuery(sql);

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("id_pemesanan"),
                rs.getString("tanggal_pemesanan"),
                rs.getString("id_konsumen"),
                rs.getString("id_karyawan"),
                rs.getString("Notes"),
            });
        }

        table.setModel(model);
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(null, e);
    }
}
    
    public void tampilLaporan(String laporanFile, String SQL) {
    try {
        File file = new File(laporanFile);
        JasperDesign jasDes = JRXmlLoader.load(file);

        JRDesignQuery query = new JRDesignQuery();
        query.setText(SQL);
        jasDes.setQuery(query);

        JasperReport jr = JasperCompileManager.compileReport(jasDes);
        JasperPrint jp = JasperFillManager.fillReport(
                jr, null, Koneksi.getKoneksi()
        );

        JasperViewer.viewReport(jp, false);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}

}

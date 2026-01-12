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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author riska_ti
 */
public class Produk {
    private final Connection koneksidb = Koneksi.getKoneksi();
    private Statement st;
    private ResultSet rs;
    private String sql;
    
    public String idProduk, jenisProduk, ukuran;
    public double harga;
    
    public boolean validasi = false;
    
    public void simpanProduk(String id, String jenis, String ukr, double hrg) {
        try {
            sql = "SELECT * FROM jenis_produk WHERE ID_produk = ?";
            PreparedStatement cekdata = koneksidb.prepareStatement(sql);
            cekdata.setString(1, id);
            ResultSet data = cekdata.executeQuery();

            if (data.next()) {
                JOptionPane.showMessageDialog(null, "ID Produk Sudah Ada!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                this.validasi = false;
            } else {
                sql = "INSERT INTO jenis_produk (ID_produk, jenis_produk, ukuran, harga) VALUES (?, ?, ?, ?)";
                PreparedStatement perintah = koneksidb.prepareStatement(sql);
                perintah.setString(1, id);
                perintah.setString(2, jenis);
                perintah.setString(3, ukr);
                perintah.setDouble(4, hrg);
                perintah.execute();
                
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan!");
                this.validasi = true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal: " + e.getMessage());
            this.validasi = false;
        }
    }

    public void ubahProduk(String id, String jenis, String ukr, double hrg) {
        try {
            sql = "UPDATE jenis_produk SET jenis_produk=?, ukuran=?, harga=? WHERE ID_produk=?";
            PreparedStatement perintah = koneksidb.prepareStatement(sql);
            perintah.setString(1, jenis);
            perintah.setString(2, ukr);
            perintah.setDouble(3, hrg);
            perintah.setString(4, id);
            perintah.execute();
            
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Perubahan Data Gagal: " + e.getMessage());
        }
    }

    public void hapusProduk(String id) {
        try {
            sql = "DELETE FROM jenis_produk WHERE ID_produk=?";
            PreparedStatement perintah = koneksidb.prepareStatement(sql);
            perintah.setString(1, id);
            perintah.execute();
            
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Penghapusan Data Gagal: " + e.getMessage());
        }
    }

    public void tampilData(JTable tbl, String sql) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Produk");
        model.addColumn("Jenis Produk");
        model.addColumn("Ukuran");
        model.addColumn("Harga");

        try {
            st = koneksidb.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("ID_produk"),
                    rs.getString("jenis_produk"),
                    rs.getString("ukuran"),
                    rs.getDouble("harga")
                });
            }
            tbl.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal Tampilkan Data: " + e.getMessage());
        }
    }

    public void cariProduk(String id) {
        try {
            sql = "SELECT * FROM jenis_produk WHERE ID_produk = ?";
            PreparedStatement perintah = koneksidb.prepareStatement(sql);
            perintah.setString(1, id);
            rs = perintah.executeQuery();
            
            if (rs.next()) {
                this.idProduk = rs.getString("ID_produk");
                this.jenisProduk = rs.getString("jenis_produk");
                this.ukuran = rs.getString("ukuran");
                this.harga = rs.getDouble("harga");
                this.validasi = true;
            } else {
                this.validasi = false;
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Cari Data: " + e.getMessage());
            this.validasi = false;
        }
    }
}
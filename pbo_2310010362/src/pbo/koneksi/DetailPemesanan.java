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

/**
 *
 * @author riska_ti
 */
public class DetailPemesanan {
    private final Connection koneksidb = Koneksi.getKoneksi();
    private Statement st;
    private ResultSet rs;
    private String sql;
    public boolean validasi = false;

    
    public void simpanDetail(String id_detail, String id_pesanan, String id_produk, int harga, int jumlah, String status) {
        try {
            sql = "SELECT * FROM detail_pemesanan WHERE ID_detail_pemesanan = ?";
            PreparedStatement cekdata = koneksidb.prepareStatement(sql);
            cekdata.setString(1, id_detail);
            ResultSet data = cekdata.executeQuery();

            if (data.next()) {
                JOptionPane.showMessageDialog(null, "ID Detail Pemesanan Sudah Ada!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                this.validasi = false;
            } else {
                sql = "INSERT INTO detail_pemesanan (ID_detail_pemesanan, ID_pemesanan, ID_Produk, Harga_Produk, Jumlah_pemesanan, Status_Detail_Pemesanan) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement perintah = koneksidb.prepareStatement(sql);
                perintah.setString(1, id_detail);
                perintah.setString(2, id_pesanan);
                perintah.setString(3, id_produk);
                perintah.setInt(4, harga);
                perintah.setInt(5, jumlah);
                perintah.setString(6, status);
                perintah.execute();
                
                JOptionPane.showMessageDialog(null, "Detail Pemesanan Berhasil Ditambahkan!");
                this.validasi = true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Penyimpanan Detail Pemesanan Gagal: " + e.getMessage());
            this.validasi = false;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Harga dan Jumlah harus berupa angka!");
            this.validasi = false;
        }
    }

    public void hapusDetail(String id_detail) {
        try {
            sql = "DELETE FROM detail_pemesanan WHERE ID_detail_pemesanan=?";
            PreparedStatement perintah = koneksidb.prepareStatement(sql);
            perintah.setString(1, id_detail);
            perintah.execute();
            
            JOptionPane.showMessageDialog(null, "Detail Pemesanan Berhasil Dihapus!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Penghapusan Detail Pemesanan Gagal: " + e.getMessage());
        }
    }

   public void tampilData(JTable tabel, String sql) {
    DefaultTableModel model = new DefaultTableModel();

    model.addColumn("ID Detail");
    model.addColumn("ID Pemesanan");
    model.addColumn("ID Produk");
    model.addColumn("Harga Produk");
    model.addColumn("Jumlah");
    model.addColumn("Status");

    try {
        Connection conn = Koneksi.getKoneksi();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("ID_Detail_Pemesanan"),
                rs.getString("ID_Pemesanan"),
                rs.getString("ID_Produk"),
                rs.getDouble("Harga_Produk"),
                rs.getInt("Jumlah_Pemesanan"),
                rs.getString("Status_Detail_Pemesanan")
            });
        }

        tabel.setModel(model);

    } catch (Exception e) {
        System.out.println("Error tampil detail pemesanan: " + e.getMessage());
    }
}


    
    public void muatIdPemesanan(JComboBox<String> cmb) {
        cmb.removeAllItems();
        cmb.addItem("-- Pilih ID Pemesanan --");
        try {
            sql = "SELECT ID_pemesanan FROM pemesanan ORDER BY ID_pemesanan DESC";
            st = koneksidb.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                cmb.addItem(rs.getString("ID_pemesanan"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data ID Pemesanan: " + e.getMessage());
        }
    }
    
    public void muatDataProduk(JComboBox<String> cmb) {
        cmb.removeAllItems();
        cmb.addItem("-- Pilih Produk --");
        try {
            sql = "SELECT ID_produk, jenis_produk, harga FROM jenis_produk ORDER BY jenis_produk ASC";
            st = koneksidb.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                String item = rs.getString("jenis_produk") + " | " + rs.getString("ID_produk") + " | " + rs.getInt("harga");
                cmb.addItem(item);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data Produk: " + e.getMessage());
        }
    }
    
    public String getIdProdukFromCmb(JComboBox<String> cmb) {
        String selectedItem = (String) cmb.getSelectedItem();
        if (selectedItem != null && selectedItem.contains(" | ")) {
            String[] parts = selectedItem.split(" \\| ");
            if (parts.length >= 2) {
                return parts[1].trim();
            }
        }
        return null;
    }
    
    public int getHargaProdukFromCmb(JComboBox<String> cmb) {
        String selectedItem = (String) cmb.getSelectedItem();
        if (selectedItem != null && selectedItem.contains(" | ")) {
            String[] parts = selectedItem.split(" \\| ");
            if (parts.length >= 3) {
                try {
                    return Integer.parseInt(parts[2].trim());
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        }
        return 0;
    }
    
   
}

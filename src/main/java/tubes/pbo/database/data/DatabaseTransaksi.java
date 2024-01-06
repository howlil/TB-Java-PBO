package tubes.pbo.database.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DatabaseTransaksi {

    private Connection connection;

    public DatabaseTransaksi() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kopimas", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     public void tambahTransaksi(String idPesan, double totalHarga, int totalBeli, Map<String, Integer> item, String namaPelanggan, String informasiPembayaran) throws SQLException {
        // Start a transaction
        connection.setAutoCommit(false);

        try {
            // Tambahkan ke tabel transaksi
            String queryTransaksi = "INSERT INTO transaksi (id_pesan, totalHarga, totalBeli) VALUES (?, ?, ?)";
            try (PreparedStatement pst = connection.prepareStatement(queryTransaksi)) {
                pst.setString(1, idPesan);
                pst.setDouble(2, totalHarga);
                pst.setInt(3, totalBeli);
                pst.executeUpdate();
            }

            // Tambahkan ke tabel transaksi_produk dan update stok di tabel produk
            String queryTransaksiProduk = "INSERT INTO transaksi_produk (id_barang, id_pesan) VALUES (?, ?)";
            String queryUpdateStok = "UPDATE produk SET stok = stok - ? WHERE nama_barang = ?";
            for (Map.Entry<String, Integer> entry : item.entrySet()) {
                String namaBarang = entry.getKey();
                int jumlah = entry.getValue();

                // Update stok
                try (PreparedStatement pst = connection.prepareStatement(queryUpdateStok)) {
                    pst.setInt(1, jumlah);
                    pst.setString(2, namaBarang);
                    pst.executeUpdate();
                }

                // Dapatkan id_barang berdasarkan nama_barang
                String idBarang = getIdBarangFromNama(namaBarang);

                // Tambahkan ke transaksi_produk
                try (PreparedStatement pst = connection.prepareStatement(queryTransaksiProduk)) {
                    pst.setString(1, idBarang);
                    pst.setString(2, idPesan);
                    pst.executeUpdate();
                }
            }

            // Tambahkan ke tabel riwayat transaksi
            String queryRiwayat = "INSERT INTO riwayat_transaksi (id_pesan, namaPembeli, tanggal, informasi_pembayaran) VALUES (?, ?, NOW(), ?)";
            try (PreparedStatement pst = connection.prepareStatement(queryRiwayat)) {
                pst.setString(1, idPesan);
                pst.setString(2, namaPelanggan);
                pst.setString(3, informasiPembayaran);
                pst.executeUpdate();
            }

            // Commit transaction
            connection.commit();
        } catch (SQLException e) {
            // Jika terjadi error, rollback transaction
            connection.rollback();
            throw e;
        } finally {
            // Set auto-commit kembali ke true
            connection.setAutoCommit(true);
        }
    }
    private String getIdBarangFromNama(String namaBarang) throws SQLException {
        String query = "SELECT id_barang FROM produk WHERE nama_barang = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, namaBarang);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("id_barang");
            }
        }
        return null;
    }

}

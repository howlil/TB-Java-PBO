package tubes.pbo.database.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import tubes.pbo.database.Produk;

public class DatabaseTransaksi {

    private Connection connection;

    public DatabaseTransaksi() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kopimas", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBarangStok(int barangId, int stokBaru) throws SQLException {
        String query = "UPDATE produk SET stok = ? WHERE id_barang = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, stokBaru);
            pstmt.setInt(2, barangId);
            pstmt.executeUpdate();
        }
    }

    // Mendapatkan barang berdasarkan ID
    public Produk getBarangByID(int id) throws SQLException {
        String query = "SELECT * FROM produk WHERE id_barang = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Produk(rs.getInt("id_barang"), rs.getString("nama_barang"), rs.getDouble("harga"),
                            rs.getInt("stok"));
                }
            }
        }
        return null;
    }
    public void simpanRiwayatTransaksi(String idPesan, String namaPembeli, String noHp, String namaProduk, int totalBeli, double totalHarga, java.sql.Date tanggal) throws SQLException {
        String informasiPembayaran = String.format("Produk: %s, Jumlah: %d, Total Harga: %.2f", namaProduk, totalBeli, totalHarga);
        String query = "INSERT INTO riwayat_transaksi (id_pesan, namaPembeli, noHp, tanggal, informasi_pembayaran) VALUES (?, ?, ?, ?, ?);";
    
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, idPesan);
            pstmt.setString(2, namaPembeli);
            pstmt.setString(3, noHp);
            pstmt.setDate(4, tanggal);
            pstmt.setString(5, informasiPembayaran);
            pstmt.executeUpdate();
        }
    }
  

    public void tampilkanSemuaRiwayatTransaksi() throws SQLException {
        String query = "SELECT * FROM riwayat_transaksi;";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("ID Riwayat: " + rs.getString("id_riwayat") +
                                   ", ID Pesan: " + rs.getString("id_pesan") +
                                   ", Nama Pembeli: " + rs.getString("namaPembeli") +
                                   ", Tanggal: " + rs.getDate("tanggal") +
                                   ", Info Pembayaran: " + rs.getString("informasi_pembayaran"));
            }
        }
    }

    public ResultSet getTransaksiTerakhir() throws SQLException {
        String query = "SELECT t.id_pesan, t.totalHarga, t.totalBeli, rt.namaPembeli, rt.noHp, rt.tanggal, rt.informasi_pembayaran FROM transaksi t LEFT JOIN riwayat_transaksi rt ON t.id_pesan = rt.id_pesan ORDER BY t.id_pesan DESC LIMIT 1;";
        PreparedStatement pstmt = connection.prepareStatement(query);
        return pstmt.executeQuery();
    }
    
    
    public void simpanTransaksiProduk(int idBarang, String idPesan) throws SQLException {
        String query = "INSERT INTO transaksi_produk (id_barang, id_pesan) VALUES (?, ?);";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, idBarang);
            pstmt.setString(2, idPesan);
            pstmt.executeUpdate();
        }
    }

    public String simpanTransaksi(double totalHarga, int jumlahBeli) throws SQLException {
        String query = "INSERT INTO transaksi (totalHarga, totalBeli) VALUES (?, ?);";
        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setDouble(1, totalHarga);
            pstmt.setInt(2, jumlahBeli);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getString(1); // Mengembalikan id_pesan
                } else {
                    throw new SQLException("Gagal membuat transaksi, ID tidak diperoleh.");
                }
            }
        }
    }

    public void buatTransaksi(int barangId, int jumlahBeli, String namaPembeli) throws SQLException {
        connection.setAutoCommit(false);
        Savepoint savepoint = connection.setSavepoint();

        try {
            Produk produk = getBarangByID(barangId);
            if (produk == null) {
                throw new SQLException("Barang tidak ditemukan");
            }

            if (produk.getStok() < jumlahBeli) {
                throw new SQLException("Stok tidak cukup");
            }

            int stokBaru = produk.getStok() - jumlahBeli;
            updateBarangStok(barangId, stokBaru);

            double totalHarga = produk.getHarga() * jumlahBeli;


            connection.commit();
        } catch (SQLException e) {
            connection.rollback(savepoint);
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

}

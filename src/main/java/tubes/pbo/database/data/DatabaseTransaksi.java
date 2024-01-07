package tubes.pbo.database.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import tubes.pbo.database.Produk;

public class DatabaseTransaksi {

    private Connection connection;

    //konstruktor
    public DatabaseTransaksi() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kopimas", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //update stok barang
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

    //simpar riwayat transaksi
    public void simpanRiwayatTransaksi(String idPesan, String namaPembeli, String noHp, String namaProduk,
            int totalBeli, double totalHarga, java.sql.Date tanggal) throws SQLException {
        String informasiPembayaran = String.format("Produk: %s, Jumlah: %d, Total Harga: %.2f", namaProduk, totalBeli,
                totalHarga);
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

    //taampilkan semua riwayat
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

    //mendapatkan data terakhir dari transaksi
    public ResultSet getTransaksiTerakhir() throws SQLException {
        String query = "SELECT t.id_pesan, t.totalHarga, t.totalBeli, rt.namaPembeli, rt.noHp, rt.tanggal, rt.informasi_pembayaran FROM transaksi t LEFT JOIN riwayat_transaksi rt ON t.id_pesan = rt.id_pesan ORDER BY t.id_pesan DESC LIMIT 1;";
        PreparedStatement pstmt = connection.prepareStatement(query);
        return pstmt.executeQuery();
    }

    // menyimpan  transaksi_produk
    public void simpanTransaksiProduk(int idBarang, String idPesan) throws SQLException {
        String query = "INSERT INTO transaksi_produk (id_barang, id_pesan) VALUES (?, ?);";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, idBarang);
            pstmt.setString(2, idPesan);
            pstmt.executeUpdate();
        }
    }

    //menyimpan transaksi
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

    //menghitung keuntungan
    public double hitungKeuntungan() throws SQLException {
        String query = "SELECT tp.id_barang, tp.id_pesan, p.harga, COUNT(tp.id_barang) as jumlah_terjual FROM transaksi_produk tp JOIN produk p ON tp.id_barang = p.id_barang GROUP BY tp.id_barang;";
        double totalKeuntungan = 0.0;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int jumlahTerjual = rs.getInt("jumlah_terjual");
                double harga = rs.getDouble("harga");
                totalKeuntungan += jumlahTerjual * harga * 0.3; // 30% dari total harga
            }
        }
        return totalKeuntungan;
    }


    // mendapatan daftar barang yang terjual
    public Map<Integer, Integer> getDaftarBarangTerjual() throws SQLException {
        Map<Integer, Integer> barangTerjual = new HashMap<>();
        String query = "SELECT tp.id_barang, SUM(t.totalBeli) as jumlah_terjual FROM transaksi_produk tp JOIN transaksi t ON tp.id_pesan = t.id_pesan GROUP BY tp.id_barang;";
    
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int idBarang = rs.getInt("id_barang");
                int jumlahTerjual = rs.getInt("jumlah_terjual");
                barangTerjual.put(idBarang, jumlahTerjual);
            }
        }
        return barangTerjual;
    }
    

    //menghitung barang yang terjual
    public int hitungTotalBarangTerjual() throws SQLException {
        String query = "SELECT SUM(totalBeli) as total_terjual FROM transaksi;";
        int totalTerjual = 0;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                totalTerjual = rs.getInt("total_terjual");
            }
        }
        return totalTerjual;
    }


    //menyimpan keuntungan
    public void simpanKeuntungan(int idBarang, double jumlahKeuntungan) throws SQLException {
        String query = "INSERT INTO keuntungan (id_barang, jumlah_keuntungan, tanggal) VALUES (?, ?, ?);";
        java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, idBarang);
            pstmt.setDouble(2, jumlahKeuntungan);
            pstmt.setDate(3, sqlDate);
            pstmt.executeUpdate();
        }
    }


    //memmabuat transaksi
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

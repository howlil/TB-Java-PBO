package tubes.pbo.database.data;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import tubes.pbo.database.Produk;

public class Database {
    private Connection connection;

    public Database() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kopimas", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addProduk(Produk produk) throws SQLException {
        String query = "INSERT INTO produk (id_barang,nama_barang, harga, stok) VALUES (?,?, ?, ?);";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, produk.getId_barang());
            pstmt.setString(1, produk.getNama_barang());
            pstmt.setDouble(2, produk.getHarga());
            pstmt.setInt(3, produk.getStok());
            pstmt.executeUpdate();
        }
    }

    public void deleteBarang(int id_barang) throws SQLException {
        String query = "DELETE FROM barang WHERE id_barang = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id_barang);
            pstmt.executeUpdate();
        }
    }

    public void updateBarang(Produk produk) throws SQLException {
        String query = "UPDATE barang SET nama_barang = ?, harga = ?, stok = ? WHERE id_barang = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, produk.getNama_barang());
            pstmt.setDouble(2, produk.getHarga());
            pstmt.setInt(3, produk.getStok());
            pstmt.setInt(4, produk.getId_barang());
            pstmt.executeUpdate();
        }
    }
    


}

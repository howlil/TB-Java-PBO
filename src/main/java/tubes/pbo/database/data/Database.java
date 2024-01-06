package tubes.pbo.database.data;

import java.sql.*;
import java.util.ArrayList;
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
    public Produk getBarangByID(int id_barang) throws SQLException {
        String query = "SELECT * FROM barang WHERE id_barang = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id_barang);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Produk(rs.getInt("id_barang"), rs.getString("nama_barang"), rs.getDouble("harga"), rs.getInt("stok"));
                }
            }
        }
        return null;
    }

    public void addProduk(Produk produk) throws SQLException {
        String query = "INSERT INTO produk (id_barang,nama_barang, harga, stok) VALUES (?,?, ?, ?);";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, produk.getId_barang());
            pstmt.setString(2, produk.getNama_barang());
            pstmt.setDouble(3, produk.getHarga());
            pstmt.setInt(4, produk.getStok());
            pstmt.executeUpdate();
        }
    }

    public void deleteBarang(int id_barang) throws SQLException {
        String query = "DELETE FROM produk WHERE id_barang = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id_barang);
            pstmt.executeUpdate();
        }
    }

    public void updateBarang(Produk produk) throws SQLException {
        String query = "UPDATE produk SET nama_barang = ?, harga = ?, stok = ? WHERE id_barang = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, produk.getNama_barang());
            pstmt.setDouble(2, produk.getHarga());
            pstmt.setInt(3, produk.getStok());
            pstmt.setInt(4, produk.getId_barang());
            pstmt.executeUpdate();
        }
    }

        public List<Produk> getAllBarang() throws SQLException {
        List<Produk> produkList = new ArrayList<>();
        String query = "SELECT * FROM produk;";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                produkList.add(new Produk(rs.getInt("id_barang"), rs.getString("nama_barang"), rs.getDouble("harga"), rs.getInt("stok")));
            }
        }
        return produkList;
    }

}

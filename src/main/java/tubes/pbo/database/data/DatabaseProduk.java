package tubes.pbo.database.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import tubes.pbo.database.Produk;

public class DatabaseProduk {
    private Connection connection;

    //konstruktor
    public DatabaseProduk() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kopimas", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //mendapatkan barang dari ID
    public Produk getBarangByID(int id_barang) throws SQLException {
        String query = "SELECT * FROM produk WHERE id_barang = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id_barang);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Produk(rs.getInt("id_barang"), rs.getString("nama_barang"), rs.getDouble("harga"),
                            rs.getInt("stok"));
                }
            }
        }
        return null;
    }

    //mendapatkan barang dari nama
    public Produk getProdukByNama(String nama) throws SQLException {
        String query = "SELECT * FROM produk WHERE nama_barang = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nama);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id_barang = rs.getInt("id_barang");
                    double harga = rs.getDouble("harga");
                    int stok = rs.getInt("stok");
                    return new Produk(id_barang, nama, harga, stok);
                }
            }
        }
        return null;
    }

    // menambahkan produk
    public void addProduk(Produk produk) throws SQLException {
        // Cek apakah ada produk dengan nama yang sama
        String checkQuery = "SELECT id_barang, stok FROM produk WHERE nama_barang = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setString(1, produk.getNama_barang());
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    // Produk dengan nama yang sama ditemukan, lakukan update
                    int existingId = rs.getInt("id_barang");
                    int existingStok = rs.getInt("stok");
                    String updateQuery = "UPDATE produk SET  stok = ? WHERE id_barang = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                        updateStmt.setInt(1, existingStok + produk.getStok());
                        updateStmt.setInt(2, existingId);
                        updateStmt.executeUpdate();
                    }
                } else {
                    // Tidak ada produk dengan nama yang sama, lakukan insert
                    String insertQuery = "INSERT INTO produk (nama_barang, harga, stok) VALUES (?, ?, ?)";
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                        insertStmt.setString(1, produk.getNama_barang());
                        insertStmt.setDouble(2, produk.getHarga());
                        insertStmt.setInt(3, produk.getStok());
                        insertStmt.executeUpdate();
                    }
                }
            }
        }
    }

    //menaghpus produk
    public void deleteBarang(int id_barang) throws SQLException {
        String query = "DELETE FROM produk WHERE id_barang = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id_barang);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Tidak ada baris yang terpengaruh, mungkin produk dengan ID tersebut tidak ada.");
            }
        }
    }

   // mengaupdate produk
    public void updateBarang(Produk produk) throws SQLException {
        String query = "UPDATE produk SET harga = ?, stok = ? WHERE id_barang = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setDouble(1, produk.getHarga());
            pstmt.setInt(2, produk.getStok());
            pstmt.setInt(3, produk.getId_barang());
            pstmt.executeUpdate();
        }
    }

    //mendapatkan data semua produk

    public List<Produk> getAllBarang() throws SQLException {
        List<Produk> produkList = new LinkedList<>();
        String query = "SELECT * FROM produk;";
        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                produkList.add(new Produk(rs.getInt("id_barang"), rs.getString("nama_barang"), rs.getDouble("harga"),
                        rs.getInt("stok")));
            }
        }
        return produkList;
    }

}

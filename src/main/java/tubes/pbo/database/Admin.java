package tubes.pbo.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import tubes.pbo.database.data.Database;
import tubes.pbo.database.template.InventoriManajemen;;

public class Admin extends Pengguna implements InventoriManajemen {
    private static Scanner scanner = new Scanner(System.in);
    private static final Database dbHelper = new Database();

    public Admin(String username, String password) {
        super(username, password);
    };

    @Override
    public void addProduk() {
        int a = 1;
        System.out.println("Masukkan detail barang:");
        System.out.print("Nama Barang: ");
        String nama = scanner.nextLine();
        System.out.print("Harga Barang: ");
        double harga = scanner.nextDouble();
        System.out.print("Stok Barang: ");
        int stok = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Produk produk = new Produk(a, nama, harga, stok);
        try {
            dbHelper.addProduk(produk);
            System.out.println("Barang berhasil ditambahkan.");
            a++;
        } catch (SQLException e) {
            System.out.println("Gagal menambahkan barang: " + e.getMessage());
        }
    }

    @Override
    public void deleteProduk() {
        System.out.print("Masukkan ID barang yang akan dihapus: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
    
        try {
            // Mengecek apakah barang dengan ID tersebut ada
            Produk barang = dbHelper.getBarangByID(id);
            if (barang != null) {
                // Menampilkan nama barang
                System.out.println("Nama Barang: " + barang.getNama_barang());
                // Meminta konfirmasi
                System.out.print("Apakah Anda yakin ingin menghapus barang ini? (y/n): ");
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("y")) {
                    // Menghapus barang
                    dbHelper.deleteBarang(id);
                    System.out.println("Barang berhasil dihapus.");
                } else {
                    System.out.println("Penghapusan barang dibatalkan.");
                }
            } else {
                System.out.println("Barang dengan ID tersebut tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal menghapus barang: " + e.getMessage());
        }
    }
    

    @Override
    public void showProduk() {
          try {
            List<Produk> produkList = dbHelper.getAllBarang();
            if (produkList.isEmpty()) {
                System.out.println("Tidak ada barang.");
                return;
            }
            System.out.println("Daftar Barang:");
            for (Produk produk : produkList) {
                System.out.printf("ID: %d, Nama: %s, Harga: %.2f, Stok: %d\n", produk.getId_barang(), produk.getNama_barang(), produk.getHarga(), produk.getStok());
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data barang: " + e.getMessage());
        }

    }

    @Override
    public void updateProduk() {
        System.out.print("Masukkan ID barang yang akan diperbarui: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Nama Barang baru: ");
        String nama = scanner.nextLine();
        System.out.print("Harga Barang baru: ");
        double harga = scanner.nextDouble();
        System.out.print("Stok Barang baru: ");
        int stok = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Produk produk = new Produk(id, nama, harga, stok);
        try {
            dbHelper.updateBarang(produk);
            System.out.println("produk berhasil diperbarui.");
        } catch (SQLException e) {
            System.out.println("Gagal memperbarui produk: " + e.getMessage());
        }
    }
}

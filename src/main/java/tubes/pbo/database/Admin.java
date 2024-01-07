package tubes.pbo.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import tubes.pbo.database.data.*;
import tubes.pbo.database.template.InventoriManajemen;;

public class Admin extends Pengguna implements InventoriManajemen {
    private static Scanner scanner = new Scanner(System.in);
    private static final DatabaseProduk dbHelper = new DatabaseProduk();
    private int a = 1;

    //konstruktor admin
    public Admin(String username, String password) {
        super(username, password);
    };

    //menambahkan produk
    @Override

    public void addProduk() {
        System.out.println("Masukkan detail barang:");
        System.out.print("Nama Barang: ");
        String nama = scanner.nextLine();

        try {
             Produk existingProduk = dbHelper.getProdukByNama(nama);
            double harga = 0;
         if (existingProduk == null) {
            System.out.print("Harga Barang: ");
             harga = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
        }

        System.out.print("Stok Barang: ");
        int stok = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (existingProduk != null) {
            existingProduk.setStok(existingProduk.getStok() + stok);
            dbHelper.updateBarang(existingProduk);
        } else {

            Produk produk = new Produk(a, nama, harga, stok); 
            dbHelper.addProduk(produk);
            a++;
        }
             System.out.println("Operasi berhasil.");
        } catch (SQLException e) {
            System.out.println("Gagal melakukan operasi database: " + e.getMessage());
            e.printStackTrace(); // Untuk debugging lebih detail
        }
        }

    //menghapus produk
    @Override
    public void deleteProduk() {
        System.out.print("Masukkan ID produk yang akan dihapus: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            // Mengecek apakah produk dengan ID tersebut ada
            Produk produk = dbHelper.getBarangByID(id);
            if (produk != null) {
                // Menampilkan nama produk
                System.out.println("Nama Produk: " + produk.getNama_barang());
                // Meminta konfirmasi
                System.out.print("Apakah Anda yakin ingin menghapus produk ini? (y/n): ");
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("y")) {
                    // Menghapus produk
                    dbHelper.deleteBarang(id);
                    System.out.println("Produk berhasil dihapus.");
                } else {
                    System.out.println("Penghapusan produk dibatalkan.");
                }
            } else {
                System.out.println("Produk dengan ID tersebut tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal menghapus produk: " + e.getMessage());
            e.printStackTrace(); // Menambahkan untuk debugging
        }
    }

    // menampilakn semua produk
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
                System.out.printf("ID: %d, Nama: %s, Harga: %.2f, Stok: %d\n", 
                produk.getId_barang(), produk.getNama_barang(), produk.getHarga(), produk.getStok());
            
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data barang: " + e.getMessage());
        }

    }

    // update produk
    @Override
    public void updateProduk() {
        System.out.print("Masukkan ID barang yang akan diperbarui: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Nama Barang baru: ");
        String nama = scanner.nextLine();
        System.out.print("Harga Barang baru: ");
        double harga = scanner.nextDouble();
        System.out.print("Stok Barang baru: ");
        int stok = scanner.nextInt();
        scanner.nextLine(); 

        Produk produk = new Produk(id, nama, harga, stok);
        try {
            dbHelper.updateBarang(produk);
            System.out.println("produk berhasil diperbarui.");
        } catch (SQLException e) {
            System.out.println("Gagal memperbarui produk: " + e.getMessage());
        }
    }
}

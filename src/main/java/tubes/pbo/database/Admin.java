package tubes.pbo.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        } catch (SQLException e) {
            System.out.println("Gagal menambahkan barang: " + e.getMessage());
        }
        a++;
    }

    @Override
    public void deleteProduk(int id_barang)  {
       
    }

    @Override
    public void showProduk() {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateProduk() {
        // TODO Auto-generated method stub

    }
}

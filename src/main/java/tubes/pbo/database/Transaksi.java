package tubes.pbo.database;

import tubes.pbo.database.data.DatabaseProduk;
import tubes.pbo.database.data.DatabaseTransaksi;
import tubes.pbo.database.template.TransaksiManajemen;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Date;

public class Transaksi implements TransaksiManajemen {
    private static Scanner scanner = new Scanner(System.in);
    private static final DatabaseTransaksi dbTransaksi = new DatabaseTransaksi();
    private double totalHarga;
    private Integer totalBeli;
    private String namaPembeli;
    private String noHp;
    

    @Override
    public void cetakStruk() {
         SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String tanggal = formatter.format(new Date());

        System.out.println("======================================");
        System.out.println("              Struk Belanja           ");
        System.out.println("               KopiMas                ");
        System.out.println("======================================");
        System.out.println("Tanggal: " + tanggal);
        System.out.println("Nama Pembeli: " + namaPembeli);
        System.out.println("No HP: " + noHp);
        System.out.println("Total Beli: " + totalBeli + " item(s)");
        System.out.println("Total Harga: Rp" + String.format("%.2f", totalHarga));
        System.out.println("======================================");
        System.out.println("         Terima Kasih dan             ");
        System.out.println("     Selamat Menikmati Kopi Anda      ");
        System.out.println("======================================");
    }
    

    @Override
    public void buatTransaksi() {
        System.out.print("Nama Pembeli: ");
        this.namaPembeli = scanner.nextLine();
        System.out.print("No HP: ");
        this.noHp = scanner.nextLine();
        System.out.print("ID Barang: ");
        int barangID = scanner.nextInt();
        
        try {
            Produk produk = dbTransaksi.getBarangByID(barangID);
            if (produk == null) {
                System.out.println("Barang tidak ditemukan.");
                return;
            }
            
            System.out.print("Jumlah Beli: ");
            this.totalBeli = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (produk.getStok() <  this.totalBeli) {
                System.out.println("Stok tidak cukup.");
                return;
            }

            // Mengurangi stok
            int stokBaru = produk.getStok() -  this.totalBeli;
            dbTransaksi.updateBarangStok(barangID, stokBaru);

            // Menghitung total harga
            this.totalHarga = produk.getHarga() *  this.totalBeli;

            // Menyimpan transaksi
            String idPesan = dbTransaksi.simpanTransaksi(this.totalHarga,  this.totalBeli);
            dbTransaksi.simpanTransaksiProduk(barangID, idPesan);
            java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
            dbTransaksi.simpanRiwayatTransaksi(idPesan, namaPembeli, produk.getNama_barang(), this.totalBeli, totalHarga, sqlDate);
    
    
            System.out.println("Transaksi berhasil disimpan.");
        } catch (SQLException e) {
            System.out.println("Gagal melakukan transaksi: " + e.getMessage());
        }
    }

    @Override
    public void riwayatTransaksi() {
        try {
            dbTransaksi.tampilkanSemuaRiwayatTransaksi();
        } catch (SQLException e) {
            System.out.println("Gagal menampilkan riwayat transaksi: " + e.getMessage());
        }
    }

    @Override
    public void keuntungan() {

    }

}

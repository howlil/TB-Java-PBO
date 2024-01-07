package tubes.pbo.database;

import tubes.pbo.database.data.DatabaseProduk;
import tubes.pbo.database.data.DatabaseTransaksi;
import tubes.pbo.database.template.TransaksiManajemen;

import java.sql.ResultSet;
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
    try {
        ResultSet rs = dbTransaksi.getTransaksiTerakhir();
        if (rs.next()) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            System.out.println("======================================");
            System.out.println("              Struk Belanja           ");
            System.out.println("               KopiMas                ");
            System.out.println("======================================");
            System.out.println("Tanggal: " + formatter.format(rs.getTimestamp("tanggal")));
            System.out.println("Nama Pembeli: " + rs.getString("namaPembeli"));
            System.out.println("No HP: " + rs.getString("noHp"));
            System.out.println("Total Beli: " + rs.getInt("totalBeli") + " item(s)");
            System.out.println("Total Harga: Rp" + String.format("%.2f", rs.getDouble("totalHarga")));

            // Memformat informasi pembayaran
            String informasiPembayaran = rs.getString("informasi_pembayaran").replace(", ", "\n");
            System.out.println("Detail Pembayaran:\n" + informasiPembayaran);

            System.out.println("======================================");
            System.out.println("         Terima Kasih dan             ");
            System.out.println("     Selamat Menikmati Kopi Anda      ");
            System.out.println("======================================");
        } else {
            System.out.println("Tidak ada transaksi terakhir yang ditemukan.");
        }
    } catch (SQLException e) {
        System.out.println("Gagal mengambil data transaksi terakhir: " + e.getMessage());
    }
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
            if (produk.getStok() < this.totalBeli) {
                System.out.println("Stok tidak cukup.");
                return;
            }

            // Mengurangi stok
            int stokBaru = produk.getStok() - this.totalBeli;
            dbTransaksi.updateBarangStok(barangID, stokBaru);

            // Menghitung total harga
            this.totalHarga = produk.getHarga() * this.totalBeli;

            // Menyimpan transaksi
            String idPesan = dbTransaksi.simpanTransaksi(this.totalHarga, this.totalBeli);
        dbTransaksi.simpanTransaksiProduk(barangID, idPesan);
        java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
        dbTransaksi.simpanRiwayatTransaksi(idPesan, namaPembeli, this.noHp, produk.getNama_barang(), this.totalBeli, totalHarga, sqlDate);

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
    private void tampilkanTotalBarangTerjual() {
        try {
            int totalTerjual = dbTransaksi.hitungTotalBarangTerjual();
            System.out.println("Total barang terjual: " + totalTerjual);
        } catch (SQLException e) {
            System.out.println("Gagal menghitung total barang terjual: " + e.getMessage());
        }
    }
    public void hitungDanSimpanKeuntungan() {
        try {
            // Misalkan Anda memiliki metode untuk mendapatkan daftar barang terjual dengan jumlahnya
            Map<Integer, Integer> barangTerjual = dbTransaksi.getDaftarBarangTerjual();
            for (Map.Entry<Integer, Integer> entry : barangTerjual.entrySet()) {
                int idBarang = entry.getKey();
                int jumlahTerjual = entry.getValue();
                Produk produk = dbTransaksi.getBarangByID(idBarang);
                if (produk != null) {
                    double keuntungan = jumlahTerjual * produk.getHarga() * 0.3; // 30% dari total harga
                    dbTransaksi.simpanKeuntungan(idBarang, keuntungan);
                }
            }
            System.out.println("Keuntungan telah disimpan untuk semua barang terjual.");
        } catch (SQLException e) {
            System.out.println("Gagal menghitung dan menyimpan keuntungan: " + e.getMessage());
        }
    }
    @Override
    public void keuntungan() {
        try {
            double totalKeuntungan = dbTransaksi.hitungKeuntungan();
            System.out.printf("Total keuntungan: Rp%.2f\n", totalKeuntungan);
            tampilkanTotalBarangTerjual();
            hitungDanSimpanKeuntungan();
        } catch (SQLException e) {
            System.out.println("Gagal menghitung keuntungan: " + e.getMessage());
        }
    }

}

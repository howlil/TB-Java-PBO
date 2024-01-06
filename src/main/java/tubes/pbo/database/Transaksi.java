package tubes.pbo.database;

import tubes.pbo.database.data.DatabaseTransaksi;
import tubes.pbo.database.template.TransaksiManajemen;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class Transaksi implements TransaksiManajemen {
    private static Scanner scanner = new Scanner(System.in);

    private String idTransaksi;
    private String namaPelanggan;
    private Map<String, Integer> item;
    private double totalHarga;
    private DatabaseTransaksi dbTransaksi;

    public Transaksi() {
        this.dbTransaksi = new DatabaseTransaksi();
    }

    @Override
    public void catakStruk() {
    }
    public void selesaikanTransaksi(String informasiPembayaran) {
        try {
            int totalBeli = 0;
            for (Integer jumlah : item.values()) {
                totalBeli += jumlah;
            }
    
            this.idTransaksi = UUID.randomUUID().toString(); // Generate unique ID
            dbTransaksi.tambahTransaksi(idTransaksi, totalHarga, totalBeli, item, namaPelanggan, informasiPembayaran);
            System.out.println("Transaksi berhasil disimpan");
        } catch (SQLException e) {
            System.out.println("Gagal menyimpan transaksi: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public Transaksi buatTransaksi() {
        System.out.print("Nama Pelanggan: ");
        this.namaPelanggan = scanner.nextLine();

        while (true) {
            System.out.print("Nama Barang: ");
            String namaBarang = scanner.nextLine();
            System.out.print("Jumlah: ");
            int jumlah = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Tambahkan ke daftar item
            item.put(namaBarang, item.getOrDefault(namaBarang, 0) + jumlah);

            System.out.print("Tambah barang lagi? (y/n): ");
            String response = scanner.nextLine();
            if (!response.equalsIgnoreCase("y")) {
                break;
            }
        }
        return this; // Mengembalikan objek transaksi ini
    }

    @Override
    public void pembayaran() {
        // Hitung total harga. Anda bisa menyesuaikannya untuk mengambil harga dari database.
        this.totalHarga = hitungTotalHarga();
        System.out.println("Total yang harus dibayar: " + totalHarga);
        // Logika pembayaran lebih lanjut bisa ditambahkan di sini.
    }

    private double hitungTotalHarga() {
        double total = 0.0;
        for (Map.Entry<String, Integer> entry : item.entrySet()) {
            String namaBarang = entry.getKey();
            int jumlah = entry.getValue();

            // Asumsikan harga tiap barang adalah 10.000 (misalnya)
            // Anda bisa memodifikasi bagian ini untuk mengambil harga sebenarnya dari database
            double harga = 10000.0; 

            total += harga * jumlah;
        }
        return total;
    }

    @Override
    public void riwayatTransaksi() {
        // TODO Auto-generated method stub

    }

    @Override
    public void keuntungan() {
        // TODO Auto-generated method stub

    }
}

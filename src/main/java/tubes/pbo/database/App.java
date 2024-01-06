package tubes.pbo.database;

import java.util.Scanner;

import tubes.pbo.database.login.*;
import tubes.pbo.database.template.*;
import java.util.*;
import java.lang.invoke.VarHandle.AccessMode;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("masukan nama anda");
        String username = scanner.nextLine();

        System.out.println("masukan password anda");
        String password = scanner.nextLine();

        Admin admin = new Admin("admin", "admin");
        Pelanggan user = new Pelanggan(username, password);

        Transaksi transaki = new Transaksi();
        Sistem sistem = new Sistem(admin, user);

        // if (!sistem.login()) {
        // System.out.println("Login gagal");
        // scanner.close();
        // return;
        // }

        int option = 0;
        try {
            do {
                System.out.println("+++++++++KOPIMAS INTERNATION CORP++++++++++");
                System.out.println("=================Menu Admin==================");
                System.out.println("1. Kelola Stok Produk");
                System.out.println("2. Transaksi");
                System.out.println("3. Keuntungan dan Riwayat");
                System.out.println("4. Keluar");
                System.out.println("===================================");
                System.out.print("Pilih opsi: ");

                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        Clean.clearScreen();
                        int opsiSubmenu = 0;
                        do {
                            System.out.println("+++++++++KOPIMAS INTERNATION CORP++++++++++");
                            System.out.println("=========Kelola Stok Produk===========");
                            System.out.println("1. Tambah Barang");
                            System.out.println("2. Lihat Semua Barang");
                            System.out.println("3. Update Barang");
                            System.out.println("4. Hapus Barang");
                            System.out.println("5. Kembali ke Menu Utama");
                            System.out.println("===================================");
                            System.out.print("Pilih opsi: ");
                            opsiSubmenu = scanner.nextInt();
                            scanner.nextLine();

                            switch (opsiSubmenu) {
                                case 1:
                                    Clean.clearScreen();
                                    admin.addProduk();
                                    break;
                                case 2:
                                    Clean.clearScreen();
                                    admin.showProduk();
                                    break;
                                case 3:
                                    Clean.clearScreen();
                                    admin.updateProduk();
                                    break;
                                case 4:
                                    Clean.clearScreen();
                                    admin.deleteProduk();
                                    break;
                                case 5:
                                    Clean.clearScreen();
                                    System.out.println("Kembali ke Menu Utama.");
                                    break;
                                default:
                                    Clean.clearScreen();
                                    System.out.println("Opsi tidak dikenal.");
                                    break;
                            }

                        } while (opsiSubmenu != 5);
                        break;

                    case 2:
                        Clean.clearScreen();
                        int opsiTransaksi = 0;
                        do {
                            System.out.println("+++++++++KOPIMAS INTERNATION CORP++++++++++");
                            System.out.println("==============TRANSAKI===============:");
                            System.out.println("1. Lihat Semua Barang");
                            System.out.println("2. Buat Transaksi");
                            System.out.println("3. Cetak Struk Barang");
                            System.out.println("4. Kembali ke Menu Utama");
                            System.out.println("===================================");
                            System.out.print("Pilih opsi: ");
                            opsiTransaksi = scanner.nextInt();
                            scanner.nextLine();

                            switch (opsiTransaksi) {
                                case 1:
                                    Clean.clearScreen();
                                    admin.showProduk();
                                    break;

                                case 2:
                                    transaki.buatTransaksi();
                                    break;

                                case 3:
                                    Clean.clearScreen();
                                    break;

                                case 4:
                                    Clean.clearScreen();
                                    System.out.println("Kembali ke Menu Utama");
                                    break;

                                default:
                                    break;
                            }
                        } while (opsiTransaksi != 4);
                        break;

                    case 3:
                        Clean.clearScreen();

                        int opsiKeuntungan = 0;
                        do {
                            System.out.println("+++++++++KOPIMAS INTERNATION CORP++++++++++");
                            System.out.println("=============Keuntungan dan Riwayat===============:");
                            System.out.println("1. Riwayat Transaksi");
                            System.out.println("2. Keuntungan");
                            System.out.println("3. Cari Pelanggan");
                            System.out.println("4. Kembali ke Halaman Menu");
                            System.out.println("===================================");
                            System.out.print("Pilih opsi: ");
                            opsiKeuntungan = scanner.nextInt();
                            scanner.nextLine();

                            switch (opsiKeuntungan) {
                                case 1:

                                    break;

                                case 2:

                                    break;

                                case 3:
                                    break;
                                case 4:
                                    System.out.println("Kembali ke Menu Utama");
                                    break;

                                default:
                                    break;
                            }
                        } while (opsiKeuntungan != 4);
                        break;
                    case 4:
                        Clean.clearScreen();
                        System.out.println("Keluar.");
                        break;
                    default:
                        System.out.println("Opsi tidak dikenal.");
                }
            } while (option != 4);

        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace(); // Untuk debugging
        } finally {
            scanner.close();
        }

    }

}

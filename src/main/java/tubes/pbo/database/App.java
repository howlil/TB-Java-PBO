package tubes.pbo.database;

import java.util.Scanner;

import tubes.pbo.database.login.Sistem;
import tubes.pbo.database.template.Clean;

import java.lang.invoke.VarHandle.AccessMode;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;

public class App {
    public static void main( String[] args )
    {
        Admin admin = new Admin("admin", "admin");
        

        Sistem sistem = new Sistem(admin);
        Scanner scanner = new Scanner(System.in);
        Date date = new Date();
        SimpleDateFormat waktu = new SimpleDateFormat("HH:mm:ss z");
        SimpleDateFormat tanggal = new SimpleDateFormat("E, dd/MM/yyyy"); 
       
        // if (!sistem.login()) {
        //     System.out.println("Login gagal");
        //     scanner.close();
        //     return;
        // }

        int option = 0;
        try {
            do {
                System.out.println("\nMenu Admin");
                System.out.println("1. Kelola Stok Produk");
                System.out.println("2. Transaksi");
                System.out.println("3. Keuntungan");
                System.out.println("4. Keluar");
                System.out.print("Pilih opsi: ");

                option = scanner.nextInt();
                scanner.nextLine(); 

                switch (option) {
                    case 1:
                    Clean.clearScreen();
                    int opsiSubmenu=0;
                        do {
                            System.out.println("\nKelola Stok Produk:");
                            System.out.println("1. Tambah Barang");
                            System.out.println("2. Lihat Semua Barang");
                            System.out.println("3. Update Barang");
                            System.out.println("4. Hapus Barang");
                            System.out.println("5. Kembali ke Menu Utama");
                            System.out.print("Pilih opsi: ");
                            opsiSubmenu = scanner.nextInt();
                            scanner.nextLine(); 

                            switch (opsiSubmenu) {
                                case 1:
                                Clean.clearScreen();
                                admin.addProduk();
                               break;
                            case 2:
                                // Implementasi untuk Lihat Semua Barang
                                break;
                            case 3:
                                // Implementasi untuk Update Barang
                                break;
                            case 4:
                                // Implementasi untuk Hapus Barang
                                break;
                            case 5:
                                System.out.println("Kembali ke Menu Utama.");
                                break;
                            default:
                                System.out.println("Opsi tidak dikenal.");
                                break;
                            }
                       
                         
                        } while (opsiSubmenu != 5);
                        break;
                    case 2:
                       
                    ;
                        break;
                    case 3:
                        System.out.println("Keluar.");
                        break;
                    case 4:
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

package tubes.pbo.database.template;
import tubes.pbo.database.Transaksi;

public interface TransaksiManajemen {
    Transaksi buatTransaksi();
    void catakStruk();
    void pembayaran();
    void riwayatTransaksi();
    void keuntungan();


    
}

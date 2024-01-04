package tubes.pbo.database.template;
import tubes.pbo.database.Transaksi;

public interface TransaksiManajemen {
    Transaksi buatTransaksi();
    void showTransaksi(String transactionId);
    void pembayaran(Transaksi transaction);

    
}

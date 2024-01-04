package tubes.pbo.database;

public interface TransaksiManajemen {
    Transaksi buatTransaksi();
    void showTransaksi(String transactionId);
    void pembayaran(Transaksi transaction);

    
}

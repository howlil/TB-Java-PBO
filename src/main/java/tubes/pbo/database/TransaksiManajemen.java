package tubes.pbo.database;

public interface TransaksiManajemen {
    Transaksi createTransaction();
    void viewTransaction(String transactionId);
    void processPayment(Transaksi transaction);
}

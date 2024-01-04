package tubes.pbo.database;
import tubes.pbo.database.template.TransaksiManajemen;

import java.util.Map;

public class Transaksi implements TransaksiManajemen {
    private String id;
    private Map<Produk, Integer> item;
    private double totalHarga;
    private Integer totalBeli;

    @Override
    public void showTransaksi(String transactionId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Transaksi buatTransaksi() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void pembayaran(Transaksi transaction) {
        // TODO Auto-generated method stub
        
    }

    
}

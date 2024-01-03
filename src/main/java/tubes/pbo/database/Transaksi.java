package tubes.pbo.database;

import java.util.Map;

public class Transaksi {
    private String id;
    private Map<Produk, Integer> item;
    private double totalHarga;
    private Integer totalBeli;
}

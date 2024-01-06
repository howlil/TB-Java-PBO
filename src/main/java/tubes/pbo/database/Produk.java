package tubes.pbo.database;

public class Produk {
    
    private Integer id_barang;
    private String nama_barang;
    private double harga;
    private Integer stok;

    public Produk(int id ,String nama , double harga, int stok){
        this.nama_barang = nama;
        this.harga = harga;
        this.stok = stok;
        this.id_barang= id;
    }

    public double getHarga() {
        return harga;
    }
    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public String getNama_barang() {
        return nama_barang;
    }
    
    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public Integer getStok() {
        return stok;
    }
    
    public void setStok(Integer stok) {
        this.stok = stok;
    }
    public Integer getId_barang() {
        return id_barang;
    }
    public void setId_barang(Integer id_barang) {
        this.id_barang = id_barang;
    }


}

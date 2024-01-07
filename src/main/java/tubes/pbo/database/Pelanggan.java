package tubes.pbo.database;

public class Pelanggan extends Pengguna {
    private String nama;
    private String noHp;


    public Pelanggan(String nama, String noHp) {
        super(nama, noHp);
    }

    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public String getNoHp() {
        return noHp;
    }
    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }
}

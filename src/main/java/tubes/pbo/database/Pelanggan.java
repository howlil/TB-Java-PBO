package tubes.pbo.database;

public class Pelanggan extends Pengguna {
    Integer saldo = 0;
    private String nama;
    private String noHp;


    public Pelanggan(String nama, String noHp) {
        super(nama, noHp);
    }

    public Integer deposit(Integer totalDeposit) {
        this.saldo = saldo + totalDeposit;
        return this.saldo;
    }

    public Integer cekSaldo() {
        return this.saldo;
    }

    public Integer getSaldo() {
        return getSaldo();
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

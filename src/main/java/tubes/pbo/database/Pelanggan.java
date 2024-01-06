package tubes.pbo.database;

public class Pelanggan extends Pengguna {
    Integer saldo = 0;

    public Pelanggan(String username, String password) {
        super(username, password);
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

    public Integer diskon() {

        return null;
    }
}

package tubes.pbo.database;

import tubes.pbo.database.template.InventoriManajemen;;

public class Admin extends Pengguna implements InventoriManajemen {

    public Admin(String username , String password) {
        super(username, password);
    };

    @Override
    public void addProduk() {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteProduk() {
        // TODO Auto-generated method stub

    }

    @Override
    public void showProduk() {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateProduk() {
        // TODO Auto-generated method stub

    }
}

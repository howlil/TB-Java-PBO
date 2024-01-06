package tubes.pbo.database;

public abstract class Pengguna {
    public String username;
    public String password;

    public Pengguna(String uname, String pw) {
        this.username = uname;
        this.password = pw;
    }

  
}

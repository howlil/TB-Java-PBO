package tubes.pbo.database;

public abstract class Pengguna {
    private String nama;
    public String username;
    public String password;

    public Pengguna(String uname, String pw) {
        this.username = uname;
        this.password = pw;
    }

  
}

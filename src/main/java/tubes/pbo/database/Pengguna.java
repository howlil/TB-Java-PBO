package tubes.pbo.database;

abstract class Pengguna {
    private String nama;
    private String username;
    private String password;

   public Pengguna(String uname, String pw){
        this.username=uname;
        this.password=pw;
    }
}

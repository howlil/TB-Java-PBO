package tubes.pbo.database.login;

import java.util.Scanner;

import tubes.pbo.database.*;;

public class Sistem {
    private Admin admin;
    private Captha captha;
    

    public Sistem(Admin admin) {
        this.admin = admin;
        this.captha = new Captha();
    }

    // public boolean check(String username, String password){
        
      
    // }  

    public boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("username : ");
        String username = scanner.nextLine();

        System.out.print("Password :");
        String password = scanner.nextLine();

        if(admin.check(username,password)){
            System.out.println("Masukkan captcha berikut: " + captcha.getCaptcha());

        }

    }

}

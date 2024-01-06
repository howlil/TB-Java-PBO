package tubes.pbo.database.login;

import java.util.Scanner;

import tubes.pbo.database.*;;

public class Sistem {
    private Admin admin;
    private Captcha captcha;

    public Sistem(Admin admin) {
        this.admin = admin;
        this.captcha = new Captcha();
    }

    public boolean login() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("username : ");
            String username = scanner.nextLine();
    
            System.out.print("Password : ");
            String password = scanner.nextLine();
    
            if (admin.username.equals(username) && admin.password.equals(password)) {
                System.out.println("Masukkan captcha berikut: " + captcha.getCaptcha());
                String captchaInput = scanner.nextLine();
    
                if(captcha.verifikasi(captchaInput)){
                    System.out.println("Login berhasil! \n");
                    return true;
                }
            }
            return false;
        } finally {
            scanner.close(); // Memastikan scanner ditutup dalam kondisi apapun
        }
    }
    
}

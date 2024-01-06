package tubes.pbo.database.login;

import java.util.Random;

public class Captcha {
    private String captcha;

    public Captcha() {
        this.captcha = generatedCode();
    }

    private String generatedCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        String code = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(code.length());
            sb.append(code.charAt(index));
        }
        return sb.toString();
    }

    public boolean verifikasi(String input) {
        return captcha.equals(input)  ;
    }

    public String getCaptcha() {
        return captcha;
    }

}

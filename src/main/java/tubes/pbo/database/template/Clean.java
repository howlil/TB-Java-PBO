package tubes.pbo.database.template;

public class Clean {
    public static void clearScreen() {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                // Untuk Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Untuk Unix/Linux
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Jika ada kesalahan, tampilkan pesan error dan lanjutkan
            System.out.println("Tidak dapat membersihkan layar: " + e.getMessage());
        }
    }
}
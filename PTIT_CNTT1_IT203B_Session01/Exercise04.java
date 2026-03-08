import java.io.IOException;

public class Exercise04 {

    // Method C
    public static void saveToFile() throws IOException {
        // gia lap loi khi ghi file
        throw new IOException("Loi khi ghi du lieu vao file!");
    }

    // Method B
    public static void processUserData() throws IOException {
        saveToFile();
    }

    // Method A
    public static void main(String[] args) {

        try {
            processUserData();
        } catch (IOException e) {
            System.out.println("Da bat duoc loi: " + e.getMessage());
        }

        System.out.println("Chuong trinh tiep tuc chay sau khi xu ly loi.");
    }
}

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Exercise06 {

    // Custom Exception
    static class InvalidAgeException extends Exception {
        public InvalidAgeException(String msg) {
            super(msg);
        }
    }

    // Lop User
    static class User {
        private String name;
        private int age;

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) throws InvalidAgeException {
            if (age < 0) {
                throw new InvalidAgeException("Tuoi khong the am!");
            }
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

    // Method ghi log loi
    public static void logError(String msg) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = LocalDateTime.now().format(dtf);
        System.out.println("[ERROR] " + time + " - " + msg);
    }

    // Gia lap ghi file (Checked Exception)
    public static void saveToFile(User u) throws IOException {
        throw new IOException("Khong the ghi du lieu vao file!");
    }

    public static void main(String[] args) {

        User user = new User();
        user.setName("Vu");

        try {
            user.setAge(-5);
        } catch (InvalidAgeException e) {
            logError(e.getMessage());
        }

        // Defensive Programming tranh NullPointerException
        if (user.getName() != null) {
            System.out.println("Ten nguoi dung: " + user.getName());
        } else {
            System.out.println("Ten nguoi dung chua duoc thiet lap.");
        }

        try {
            saveToFile(user);
        } catch (IOException e) {
            logError(e.getMessage());
        }

        System.out.println("Chuong trinh ket thuc an toan.");
    }
}

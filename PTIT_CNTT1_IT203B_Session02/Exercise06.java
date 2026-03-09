package PTIT_CNTT1_IT203B_Session02;

// Functional Interface tùy chỉnh
@FunctionalInterface
interface UserProcessor {
    String process(User u);
}

// Lớp User
class User {
    private String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

// Lớp tiện ích
class UserUtils {
    public static String convertToUpperCase(User u) {
        return u.getUsername().toUpperCase();
    }
}

public class Exercise06 {

    public static void main(String[] args) {

        User user = new User("ptit_student");

        // Gán Method Reference (static method) thay cho Lambda
        UserProcessor processor = UserUtils::convertToUpperCase;

        // Gọi phương thức process và in kết quả
        String result = processor.process(user);
        System.out.println(result);
    }
}

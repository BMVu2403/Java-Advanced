package PTIT_CNTT1_IT203B_Session02;

@FunctionalInterface
interface Authenticatable {

    // 1. Phương thức trừu tượng
    String getPassword();

    // 2. Phương thức mặc định: kiểm tra mật khẩu không rỗng
    default boolean isAuthenticated() {
        return getPassword() != null && !getPassword().isEmpty();
    }

    // 3. Phương thức static: mô phỏng mã hóa mật khẩu
    static String encrypt(String rawPassword) {
        return rawPassword == null ? null : "ENC_" + rawPassword;
    }
}

class User implements Authenticatable {
    private String password;

    public User(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }
}

public class Exercise03 {

    public static void main(String[] args) {

        User user1 = new User(Authenticatable.encrypt("123456"));
        User user2 = new User("");

        System.out.println("User1 authenticated: " + user1.isAuthenticated());
        System.out.println("User2 authenticated: " + user2.isAuthenticated());
    }
}

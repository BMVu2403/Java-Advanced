package PTIT_CNTT1_IT203B_Session02;

import java.util.*;
import java.util.function.*;

class User {
    private String username;

    public User() {
        this.username = "default";
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

public class Exercise04 {

    public static void main(String[] args) {

        // Danh sách users tự định nghĩa
        List<User> users = Arrays.asList(
                new User("admin"),
                new User("user1"),
                new User("user2"));

        // 1. (user) -> user.getUsername()
        // Tham chiếu instance method của đối tượng bất kỳ
        Function<User, String> getUsername = User::getUsername;

        // 2. (s) -> System.out.println(s)
        // Tham chiếu instance method của một đối tượng cụ thể
        Consumer<String> print = System.out::println;

        // 3. () -> new User()
        // Tham chiếu Constructor
        Supplier<User> userSupplier = User::new;

        // Áp dụng Method Reference
        users.stream()
                .map(getUsername)
                .forEach(print);

        User newUser = userSupplier.get();
        System.out.println("New user created: " + newUser.getUsername());
    }
}

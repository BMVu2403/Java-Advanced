package PTIT_CNTT1_IT203B_Session03;

import java.util.List;

enum Status {
    ACTIVE,
    INACTIVE
}

record User(String username, String email, Status status) {
}

public class Exercise02 {
    public static void main(String[] args) {

        List<User> users = List.of(
                new User("alice", "alice@gmail.com", Status.ACTIVE),
                new User("bob", "bob@yahoo.com", Status.INACTIVE),
                new User("charlie", "charlie@gmail.com", Status.ACTIVE));

        // Lọc user dùng gmail và in username
        users.stream()
                .filter(user -> user.email().endsWith("@gmail.com"))
                .map(User::username)
                .forEach(System.out::println);
    }
}

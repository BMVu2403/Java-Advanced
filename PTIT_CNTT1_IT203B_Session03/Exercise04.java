package PTIT_CNTT1_IT203B_Session03;

import java.util.*;
import java.util.stream.Collectors;

enum Status {
    ACTIVE,
    INACTIVE
}

record User(String username, String email, Status status) {
}

public class Exercise04 {
    public static void main(String[] args) {

        // Danh sách user có trùng username
        List<User> users = List.of(
                new User("alice", "alice@gmail.com", Status.ACTIVE),
                new User("bob", "bob@yahoo.com", Status.INACTIVE),
                new User("alice", "alice2@gmail.com", Status.ACTIVE),
                new User("charlie", "charlie@gmail.com", Status.ACTIVE),
                new User("bob", "bob2@yahoo.com", Status.INACTIVE));

        // Collectors.toMap giúp loại bỏ trùng lặp dựa trên username 
        List<User> uniqueUsers = users.stream()
                .collect(Collectors.toMap(
                        User::username, // key: username
                        user -> user, // value: User
                        (existing, duplicate) -> existing // giữ user đầu tiên
                ))
                .values()
                .stream()
                .toList();

        // In kết quả
        uniqueUsers.forEach(System.out::println);
    }
}

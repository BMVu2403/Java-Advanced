package PTIT_CNTT1_IT203B_Session04;

public class Exercise03 {

    public String processEmail(String email) {

        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email không hợp lệ");
        }

        int atIndex = email.indexOf("@");

        if (atIndex == email.length() - 1) {
            throw new IllegalArgumentException("Email không có tên miền");
        }

        return email.toLowerCase();
    }
}

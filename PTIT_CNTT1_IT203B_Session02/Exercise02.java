package PTIT_CNTT1_IT203B_Session02;

@FunctionalInterface
interface PasswordValidator {
    boolean validate(String password);
}

public class Exercise02 {

    public static void main(String[] args) {

        // Lambda rút gọn tối đa (bỏ kiểu dữ liệu, ngoặc nhọn, return)
        PasswordValidator validator = password -> password.length() >= 8;

        System.out.println("12345678 -> " + validator.validate("12345678"));
        System.out.println("1234 -> " + validator.validate("1234"));
    }
}

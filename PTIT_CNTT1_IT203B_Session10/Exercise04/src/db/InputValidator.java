package PTIT_CNTT1_IT203B_Session10.Exercise04.src.db;

public class InputValidator {

    public static String sanitize(String input) {
        if (input == null) {
            return "";
        }

        return input
                .replace("'", "")
                .replace("--", "")
                .replace(";", "");
    }
}

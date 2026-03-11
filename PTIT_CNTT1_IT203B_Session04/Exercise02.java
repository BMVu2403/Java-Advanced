package PTIT_CNTT1_IT203B_Session04;

public class Exercise02 {

    public boolean checkRegistrationAge(int age) {

        if (age < 0) {
            throw new IllegalArgumentException("Tuổi không hợp lệ");
        }

        return age >= 18;
    }
}

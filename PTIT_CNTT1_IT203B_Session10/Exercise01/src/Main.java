package PTIT_CNTT1_IT203B_Session10.Exercise01.src;

import PTIT_CNTT1_IT203B_Session10.Exercise01.src.repository.PatientRepository;

public class Main {
    public static void main(String[] args) {
        PatientRepository repo = new PatientRepository();
        repo.testConnection();
    }
}

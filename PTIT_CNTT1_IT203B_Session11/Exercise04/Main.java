package PTIT_CNTT1_IT203B_Session11.Exercise04;

import PTIT_CNTT1_IT203B_Session11.Exercise04.src.repository.PatientRepository;

public class Main {

    public static void main(String[] args) {

        PatientRepository repo = new PatientRepository();

        // tim binh thuong
        repo.findPatientByName("Nguyen Van A");

        // tan cong SQL Injection
        repo.findPatientByName("' OR '1'='1' --");
    }
}

package PTIT_CNTT1_IT203B_Session10.Exercise02;

import PTIT_CNTT1_IT203B_Session10.Exercise02.src.repository.PharmacyRepository;

public class Main {
    public static void main(String[] args) {
        PharmacyRepository repo = new PharmacyRepository();
        repo.printPharmacyCatalogue();
    }
}

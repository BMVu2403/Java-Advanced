package PTIT_CNTT1_IT203B_Session08.Exercise06;

public class PrintReceipt implements NotificationService {

    @Override
    public void notifyCustomer(String message) {
        System.out.println("In hóa đơn: " + message);
    }
}

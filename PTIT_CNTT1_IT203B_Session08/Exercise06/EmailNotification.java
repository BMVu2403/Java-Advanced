package PTIT_CNTT1_IT203B_Session08.Exercise06;

public class EmailNotification implements NotificationService {

    @Override
    public void notifyCustomer(String message) {
        System.out.println("Gửi email: " + message);
    }
}

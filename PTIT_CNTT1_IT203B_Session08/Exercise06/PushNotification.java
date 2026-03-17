package PTIT_CNTT1_IT203B_Session08.Exercise06;

public class PushNotification implements NotificationService {

    @Override
    public void notifyCustomer(String message) {
        System.out.println("Gửi push notification: " + message);
    }
}

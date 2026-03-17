package PTIT_CNTT1_IT203B_Session08.Exercise06;

public class MobileAppFactory implements SalesChannelFactory {

    @Override
    public DiscountStrategy createDiscountStrategy() {
        return new FirstTimeDiscount();
    }

    @Override
    public PaymentMethod createPaymentMethod() {
        return new MomoPayment();
    }

    @Override
    public NotificationService createNotificationService() {
        return new PushNotification();
    }
}

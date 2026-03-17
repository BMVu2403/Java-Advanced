package PTIT_CNTT1_IT203B_Session08.Exercise06;

public class WebsiteFactory implements SalesChannelFactory {

    @Override
    public DiscountStrategy createDiscountStrategy() {
        return new WebsiteDiscount();
    }

    @Override
    public PaymentMethod createPaymentMethod() {
        return new CreditCardPayment();
    }

    @Override
    public NotificationService createNotificationService() {
        return new EmailNotification();
    }
}

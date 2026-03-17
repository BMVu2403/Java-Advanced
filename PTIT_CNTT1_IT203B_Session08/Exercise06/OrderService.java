package PTIT_CNTT1_IT203B_Session08.Exercise06;

public class OrderService {

    private DiscountStrategy discountStrategy;
    private PaymentMethod paymentMethod;
    private NotificationService notificationService;

    public OrderService(SalesChannelFactory factory) {
        this.discountStrategy = factory.createDiscountStrategy();
        this.paymentMethod = factory.createPaymentMethod();
        this.notificationService = factory.createNotificationService();
    }

    public void processOrder(Order order) {
        double total = order.getTotalAmount();
        double discount = discountStrategy.calculateDiscount(total);
        double finalAmount = total - discount;

        System.out.println("Áp dụng giảm giá: " + discount);
        paymentMethod.pay(finalAmount);
        notificationService.notifyCustomer("Đơn hàng thành công");
    }
}

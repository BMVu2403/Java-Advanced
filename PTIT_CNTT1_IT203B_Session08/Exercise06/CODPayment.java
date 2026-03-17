package PTIT_CNTT1_IT203B_Session08.Exercise06;

public class CODPayment implements PaymentMethod {

    @Override
    public void pay(double amount) {
        System.out.println("Thanh toán COD khi nhận hàng: " + amount);
    }
}

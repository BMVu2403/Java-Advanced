package PTIT_CNTT1_IT203B_Session08.Exercise06;

public class CreditCardPayment implements PaymentMethod {

    @Override
    public void pay(double amount) {
        System.out.println("Xử lý thanh toán thẻ tín dụng: " + amount);
    }
}

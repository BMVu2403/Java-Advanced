package PTIT_CNTT1_IT203B_Session08.Exercise06;

public class MomoPayment implements PaymentMethod {

    @Override
    public void pay(double amount) {
        System.out.println("Xử lý thanh toán MoMo: " + amount);
    }
}

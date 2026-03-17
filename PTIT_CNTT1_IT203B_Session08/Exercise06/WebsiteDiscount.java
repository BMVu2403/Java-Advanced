package PTIT_CNTT1_IT203B_Session08.Exercise06;

public class WebsiteDiscount implements DiscountStrategy {

    @Override
    public double calculateDiscount(double totalAmount) {
        return totalAmount * 0.10;
    }
}

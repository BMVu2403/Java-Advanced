package PTIT_CNTT1_IT203B_Session08.Exercise06;

public class Order {
    public String productName;
    public double price;
    public int quantity;

    public double getTotalAmount() {
        return price * quantity;
    }
}

package store.domain;

public class Receipt {
    private String name;
    private int quantity;
    private String promotionName;
    private int promotionQuantity;

    public Receipt(String name, String quantity, String promotionName, String promotionQuantity) {
        this.name = name;
        this.quantity = strToInt(quantity);
        this.promotionName = promotionName;
        this.promotionQuantity = strToInt(promotionQuantity);
    }

    private int strToInt(String str) {
        return Integer.parseInt(str);
    }

    public void setPromotionName(String productName) {
        this.promotionName = productName;
    }

    public void addPromotionQuantity(int promotionQuantity) {
        this.promotionQuantity += promotionQuantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public String getName() {
        return name;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public int getPromotionQuantity() {
        return this.promotionQuantity;
    }

    public int getQuantity() {
        return this.quantity;
    }
}

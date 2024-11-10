package store.domain;

public class Receipt {
    private String name;
    private int quantity;
    private String promotionName;
    private int promotionQuantity;

    public Receipt(String name, int quantity, String promotionName, int promotionQuantity) {
        this.name = name;
        this.quantity = quantity;
        this.promotionName = promotionName;
        this.promotionQuantity = promotionQuantity;
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

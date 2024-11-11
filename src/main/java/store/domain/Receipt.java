package store.domain;

public class Receipt {
    private String name;
    private int price;
    private int quantity;
    private String promotionName;
    private int promotionQuantity;
    private int promotionTargetQuantity;

    public Receipt(String name, int price, int quantity, String promotionName, int promotionQuantity) {
        this.name = name;
        this.price = price;
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

    public void reduceQuantity(int quantity) {
        this.quantity -= quantity;
    }

    public void reducePromotionQuantity(int promotionQuantity) {
        this.promotionQuantity -= promotionQuantity;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPromotionTargetQuantity(int sum) {
        this.promotionTargetQuantity = this.promotionQuantity / sum;
    }

    public int getTargetQuantity() {
        return this.promotionTargetQuantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

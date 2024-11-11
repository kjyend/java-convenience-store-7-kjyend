package store.domain;

import java.text.NumberFormat;

public class Product {
    private String name;
    private int price;
    private int quantity;
    private String promotion;
    private int promotionQuantity;

    public Product(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Product(String name, int price, int quantity, String promotion, int promotionQuantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
        this.promotionQuantity = promotionQuantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void addPromotionQuantity(int promotionQuantity) {
        this.promotionQuantity += promotionQuantity;
    }

    public String getName() {
        return this.name;
    }

    public String getPriceUtil() {
        return NumberFormat.getInstance().format(this.price);
    }

    public int getPrice() {
        return this.price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String getPromotion() {
        return this.promotion;
    }

    public int getPromotionQuantity() {
        return this.promotionQuantity;
    }

    public int reducesStock(int quantity) {
        quantity -= Math.min(this.promotionQuantity, quantity);
        this.promotionQuantity = Math.max(0, this.promotionQuantity - quantity);

        quantity -= Math.min(this.quantity, quantity);
        this.quantity = Math.max(0, this.quantity - quantity);

        return quantity;
    }

    public boolean validatePromotion() {
        return this.promotion != null;
    }

    public boolean sameName(String product) {
        return this.name.equals(product);
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

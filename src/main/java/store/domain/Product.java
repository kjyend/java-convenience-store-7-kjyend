package store.domain;

import java.text.NumberFormat;

public class Product {
    private String name;
    private int price;
    private int quantity;
    private String promotion;

    public Product(String name, String price, String quantity, String promotion) {
        this.name = name;
        this.price = strToInt(price);
        this.quantity = strToInt(quantity);
        this.promotion = promotion;
    }

    private int strToInt(String price) {
        return Integer.parseInt(price);
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

    public int getNumbers() {
        return this.quantity;
    }

    public String getPromotion() {
        return this.promotion;
    }

    public int reducesStock(int quantity) {
        if (this.quantity - quantity < 0) {
            int result = quantity - this.quantity;
            this.quantity = 0;
            return result;
        }
        return this.quantity - quantity;
    }

    public boolean validatePromotion() {
        return this.promotion != null;
    }

    public boolean sameName(String product) {
        return this.name.equals(product);
    }
}

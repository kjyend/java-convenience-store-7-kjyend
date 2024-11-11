package store.validator;

import java.util.List;
import store.domain.Product;

public class Validators {

    public void validateProductFormat(String product) {
        String regex = "^[가-힣0-9\\[\\],-]+$";
        if (!product.matches(regex)) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    public void validateProductInputFormat(String product) {
        String regex = "\\[(.+?)-(\\d+)]";
        if (!product.matches(regex)) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    public void validateProductExists(List<Product> buyProductList, List<Product> productList) {
        for (Product buyProduct : buyProductList) {
            if (!findAndValidateProduct(productList, buyProduct)) {
                throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
            }
        }
    }

    private boolean findAndValidateProduct(List<Product> productList, Product buyProduct) {
        for (Product product : productList) {
            if (product.getName().equals(buyProduct.getName())) {
                return true;
            }
        }
        return false;
    }

    public void validateProductQuantity(Product matchProduct, Product buyProduct) {
        int sum = buyProduct.getQuantity() + buyProduct.getPromotionQuantity();
        if (sum > buyProduct.getQuantity()) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    public void validateYesOrNo(String yesNoInput) {
        if (!(yesNoInput.equalsIgnoreCase("y") || yesNoInput.equalsIgnoreCase("n"))) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
    }
}

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

    public void validateProductExists(List<Product> listProducts, String product) {
        for (Product listProduct : listProducts) {
            if (!listProduct.sameName(product)) {
                throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
            }
        }
    }

    public void validateProductQuantity(List<Product> matchProduct, Product buyProduct) {
        int sum = 0;
        for (Product product : matchProduct) {
            sum += product.getNumbers();
        }
        if (sum > buyProduct.getNumbers()) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    public void validateYesOrNo(String yesNoInput) {
        if (!(yesNoInput.equalsIgnoreCase("y") || yesNoInput.equalsIgnoreCase("n"))) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
    }
}

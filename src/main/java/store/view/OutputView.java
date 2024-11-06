package store.view;

import java.util.List;
import store.domain.Product;

public class OutputView {

    public void startGame() {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.\n");
    }

    public void printProductList(List<Product> products) {
        for (Product product : products) {
            System.out.println(
                    "- " + product.getName() + " " + product.getPriceUtil() + "원 " + product.getNumbers() + "개 "
                            + product.getPromotion);
        }
    }
}

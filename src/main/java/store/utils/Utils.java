package store.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import store.domain.Product;
import store.domain.Promotion;

public class Utils {

    public List<String> readFile(String files) {
        try {
            Path path = Paths.get(files);
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 경로에 파일이 존재하지 않습니다.");
        }
    }

    public void buildProductList(List<String> fileProducts, List<Product> products) {
        for (int i = 1; i < fileProducts.size(); i++) {
            String[] fileProduct = fileProducts.get(i).split(",");
            initializeOrUpdateProducts(products, fileProduct);
        }
    }

    private void initializeOrUpdateProducts(List<Product> products, String[] fileProduct) {
        for (Product product : products) {
            if (!product.getName().equals(fileProduct[0])) {
                createNewProduct(products, fileProduct);
            }
            if (product.getName().equals(fileProduct[0])) {
                updateExistingProduct(fileProduct, product);
            }
        }
    }

    private void createNewProduct(List<Product> products, String[] fileProduct) {
        if (fileProduct[3] == null) {
            products.add(new Product(fileProduct[0], fileProduct[1], fileProduct[2],
                    fileProduct[3], null));
        }
        if (fileProduct[3] != null) {
            products.add(new Product(fileProduct[0], fileProduct[1], null,
                    fileProduct[3], fileProduct[2]));
        }
    }

    private void updateExistingProduct(String[] fileProduct, Product product) {
        if (fileProduct[3] == null) {
            product.addQuantity(Integer.parseInt(fileProduct[2]));
        }
        if (fileProduct[3] != null) {
            product.addPromotionQuantity(Integer.parseInt(fileProduct[2]));
        }
    }

    public void buildPromotionList(List<String> filePromotions, List<Promotion> promotions) {
        for (int i = 1; i < filePromotions.size(); i++) {
            String[] filePromotion = filePromotions.get(i).split(",");
            promotions.add(new Promotion(filePromotion[0], filePromotion[1], filePromotion[2], filePromotion[3],
                    filePromotion[4]));
        }
    }
}

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
            products.add(new Product(fileProduct[0], Integer.parseInt(fileProduct[1]), fileProduct[2],
                    Promotion.nameOf(fileProduct[3])));
        }
    }
}

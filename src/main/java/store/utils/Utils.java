package store.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.Receipt;
import store.validator.Validators;

public class Utils {

    private final Validators validators;

    public Utils(Validators validators) {
        this.validators = validators;
    }

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
            createNewProduct(products, fileProduct);
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
        if (fileProduct[3].equals("null")) {
            products.add(new Product(fileProduct[0], Integer.parseInt(fileProduct[1]), Integer.parseInt(fileProduct[2]),
                    fileProduct[3], 0));
        }
        if (!fileProduct[3].equals("null")) {
            products.add(new Product(fileProduct[0], Integer.parseInt(fileProduct[1]), 0,
                    fileProduct[3], Integer.parseInt(fileProduct[2])));
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

    public List<Receipt> productSetting(String product, List<Product> productList) {
        String[] parts = separationProductQuantity(product);
        List<Product> buyProductList = addProduct(parts);
        validators.validateProductExists(buyProductList, productList);
        return sellProduct(buyProductList, productList);
    }

    private String[] separationProductQuantity(String product) {
        return product.split(",");
    }

    private List<Product> addProduct(String[] parts) {
        List<Product> buyProductList = new ArrayList<>();
        for (String part : parts) {
            String[] session = part.substring(1, part.length() - 1).split("-");
            buyProductList.add(new Product(session[0], Integer.parseInt(session[1])));
        }
        return buyProductList;
    }

    private List<Receipt> sellProduct(List<Product> buyProductList, List<Product> productList) {
        List<Receipt> receiptList = new ArrayList<>();
        for (Product buyProduct : buyProductList) {
            Product matchProduct = findProduct(buyProduct, productList);
            validators.validateProductQuantity(matchProduct, buyProduct);
            receiptList.add(sellProductQuantity(matchProduct, buyProduct));
        }
        return receiptList;
    }

    private Product findProduct(Product buyProduct, List<Product> productList) {
        List<Product> checkProducts = productList.stream()
                .filter(product -> product.getName().equals(buyProduct.getName()))
                .toList();
        Product matchProduct = new Product(buyProduct.getName(), buyProduct.getPrice(), 0, "null", 0);
        for (Product checkProduct : checkProducts) {
            if (checkProduct.getPromotion().equals("null")) {
                matchProduct.addQuantity(checkProduct.getQuantity());
            }
            if (!checkProduct.getPromotion().equals("null")) {
                matchProduct.setPromotion(checkProduct.getPromotion());
                matchProduct.addPromotionQuantity(checkProduct.getPromotionQuantity());
            }
            matchProduct.setPrice(checkProduct.getPrice());
        }
        return matchProduct;
    }

    private Receipt sellProductQuantity(Product matchProduct, Product buyProduct) {
        Receipt receipt = new Receipt(buyProduct.getName(), buyProduct.getPrice(), 0, matchProduct.getPromotion(), 0);
        if (matchProduct.getPromotionQuantity() >= buyProduct.getQuantity()) {
            processExcessStockPurchase(buyProduct, matchProduct, receipt);
            receipt.setPrice(matchProduct.getPrice());
            return receipt;
        }
        processStockShortage(buyProduct, matchProduct, receipt);
        receipt.setPrice(matchProduct.getPrice());
        return receipt;
    }

    private void processExcessStockPurchase(Product buyProduct, Product matchProduct, Receipt receipt) {
        int numbers = getBuyProductZero(buyProduct, matchProduct);
        receipt.addPromotionQuantity(numbers);
    }

    private int getBuyProductZero(Product buyProduct, Product matchProduct) {
        int numbers = buyProduct.getQuantity();
        matchProduct.reducesStock(numbers);
        buyProduct.reducesStock(numbers);
        return numbers;
    }


    private void processStockShortage(Product buyProduct, Product matchProduct, Receipt receipt) {
        getPromotionProductReceipt(buyProduct, matchProduct, receipt);
        getProductReceipt(buyProduct, matchProduct, receipt);
    }

    private void getPromotionProductReceipt(Product buyProduct, Product matchProduct, Receipt receipt) {
        int numbers = matchProduct.getPromotionQuantity();
        matchProduct.reducesStock(numbers);
        receipt.addPromotionQuantity(numbers);
        buyProduct.reducesStock(numbers);
    }

    private void getProductReceipt(Product buyProduct, Product matchProduct, Receipt receipt) {
        int quantity = buyProduct.getQuantity();
        buyProduct.reducesStock(quantity);
        matchProduct.reducesStock(quantity);
        receipt.addQuantity(quantity);
    }

    public Promotion findPromotion(Receipt receipt, List<Promotion> promotionList) {
        for (Promotion promotion : promotionList) {
            if (promotion.getName().equals(receipt.getPromotionName())) {
                return promotion;
            }
        }
        return null;
    }

    public Product findProduct(Receipt receipt, List<Product> productList) {
        for (Product product : productList) {
            product.sameName(receipt.getName());
            return product;
        }
        return null;
    }

    public String getPriceUtil(int price) {
        return NumberFormat.getInstance().format(price);
    }
}

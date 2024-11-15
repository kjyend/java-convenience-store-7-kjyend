package store.controller;

import java.util.ArrayList;
import java.util.List;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.Receipt;
import store.utils.Utils;
import store.validator.Validators;
import store.view.InputView;
import store.view.OutputView;

public class Controller {

    private final String PRODUCT_PATH = "src/main/resources/products.md";
    private final String PROMOTION_PATH = "src/main/resources/promotions.md";

    private final Utils utils;
    private final Validators validators;
    private final InputView inputView;
    private final OutputView outputView;

    private List<Product> productList = new ArrayList<>();
    private List<Promotion> promotionList = new ArrayList<>();
    private List<Receipt> receiptList;
    private String repurchased = "Y";

    public Controller(Utils utils, Validators validators, InputView inputView, OutputView outputView) {
        this.utils = utils;
        this.validators = validators;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        startGameSetting();
        while (repurchased.equals("Y")) {
            printStart();
            receiptList = inputProduct();
            validateAndApplyPromotions(receiptList);
            productDown(receiptList, productList);
            printResult();
            repurchased = repurchaseIntention();
        }
    }

    private void productDown(List<Receipt> receiptList, List<Product> productList) {
        for (Receipt receipt : receiptList) {
            for (Product product : productList) {
                product(receipt, product);
            }
        }
    }

    private static void product(Receipt receipt, Product product) {
        if (receipt.getName().equals(product.getName()) && (receipt.getQuantity() != 0
                || receipt.getPromotionQuantity() != 0)) {
            if (product.getPromotionQuantity() != 0) {
                product.reducesStock(receipt.getPromotionQuantity());
            }
            if (product.getQuantity() != 0) {
                product.reducesStock(receipt.getQuantity());
            }
        }
    }

    public void startGameSetting() {
        readProductFile();
        readPromotionFile();
    }

    private void readProductFile() {
        List<String> fileProducts = utils.readFile(PRODUCT_PATH);
        utils.buildProductList(fileProducts, productList);
        utils.buildCheckProductList(productList);
    }

    private void readPromotionFile() {
        List<String> filePromotions = utils.readFile(PROMOTION_PATH);
        utils.buildPromotionList(filePromotions, promotionList);
    }

    private void printStart() {
        outputView.startGame();
        outputView.printProductList(productList);
    }

    public List<Receipt> inputProduct() {
        while (true) {
            try {
                String buyProducts = inputView.inputProductDetails();
                inputValidator(buyProducts);
                return utils.productSetting(buyProducts, productList, promotionList);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void inputValidator(String buyProducts) {
        validators.validateProductFormat(buyProducts);
        validatorInputList(buyProducts);
    }

    private void validatorInputList(String buyProducts) {
        String[] products = buyProducts.split(",");
        for (String product : products) {
            validators.validateProductInputFormat(product);
        }
    }

    public void validateAndApplyPromotions(List<Receipt> receiptList) {
        for (Receipt receipt : receiptList) {
            if (receipt.getPromotionName() != null) {
                Promotion promotion = utils.findPromotion(receipt, promotionList);
                Product product = utils.findProduct(receipt, productList);
                promotionNotNull(receipt, promotion, product);
            }
        }
    }

    private void promotionNotNull(Receipt receipt, Promotion promotion, Product product) {
        if (promotion != null) {
            processPromotionCondition(product, promotion, receipt);
            processPromotionNotCondition(product, promotion, receipt);
            promotionQuantityTargetPlus(receipt, promotion);
        }
    }

    private void processPromotionCondition(Product product, Promotion promotion, Receipt receipt) {
        int sum = promotion.getBuy() + promotion.getFree();
        if (promotion.promotionDate() && receipt.getPromotionQuantity() % sum == sum - 1
                && product.getPromotionQuantity() - receipt.getPromotionQuantity() > 0) {
            String booleanCheck = receivePromotionConfirmation(receipt);
            promotionYesCheck(product, receipt, booleanCheck);
        }
    }

    private String receivePromotionConfirmation(Receipt receipt) {
        while (true) {
            try {
                String booleanCheck = inputView.inputPromotionAddFreeProduct(receipt.getName());
                validators.validateYesOrNo(booleanCheck);
                return booleanCheck;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void promotionYesCheck(Product product, Receipt receipt, String booleanCheck) {
        if (booleanCheck.equals("Y")) {
            product.reducesStock(1);
            receipt.addPromotionQuantity(1);
        }
    }

    private void processPromotionNotCondition(Product product, Promotion promotion, Receipt receipt) {
        int sum = promotion.getBuy() + promotion.getFree();
        int excessQuantity = receipt.getQuantity() + receipt.getPromotionQuantity() % sum;
        if (promotion.promotionDate() && excessQuantity > sum) {
            String booleanCheck = processNonPromotionalPurchase(receipt, excessQuantity);
            promotionNoCheck(product, receipt, booleanCheck, excessQuantity);
        }
    }

    private String processNonPromotionalPurchase(Receipt receipt, int excessQuantity) {
        while (true) {
            try {
                String booleanCheck = inputView.inputConfirmPurchaseWithoutPromotion(receipt.getName(), excessQuantity);
                validators.validateYesOrNo(booleanCheck);
                return booleanCheck;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void promotionNoCheck(Product product, Receipt receipt, String booleanCheck, int excessQuantity) {
        if (booleanCheck.equals("N")) {
            int quantity = receipt.getQuantity();
            product.addQuantity(quantity);
            receipt.reduceQuantity(quantity);
            int excessPromotionQuantity = excessQuantity - quantity;
            product.addPromotionQuantity(excessPromotionQuantity);
            receipt.reducePromotionQuantity(excessPromotionQuantity);
        }
    }

    private void promotionQuantityTargetPlus(Receipt receipt, Promotion promotion) {
        int sum = promotion.getFree() + promotion.getBuy();
        receipt.setPromotionTargetQuantity(sum);
    }

    public String membershipCheck() {
        while (true) {
            try {
                String booleanCheck = inputView.inputConfirmMembershipDiscount();
                validators.validateYesOrNo(booleanCheck);
                return booleanCheck;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void printResult() {
        String membership = membershipCheck();
        outputView.printReceiptList(receiptList);
        outputView.printPromotionDetail(receiptList);
        outputView.printReceiptListDetail(receiptList, membership);
    }

    public String repurchaseIntention() {
        while (true) {
            try {
                String booleanCheck = inputView.inputAskForAdditionalPurchase();
                validators.validateYesOrNo(booleanCheck);
                return booleanCheck;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}

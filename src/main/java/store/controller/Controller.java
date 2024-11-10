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

    public Controller(Utils utils, Validators validators, InputView inputView, OutputView outputView) {
        this.utils = utils;
        this.validators = validators;
        this.inputView = inputView;
        this.outputView = outputView;
    }
}

package store;

import store.controller.Controller;
import store.utils.Utils;
import store.validator.Validators;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        Utils utils = new Utils(new Validators());
        Controller controller = new Controller(utils, new Validators(), new InputView(), new OutputView(utils));
        controller.start();
    }
}

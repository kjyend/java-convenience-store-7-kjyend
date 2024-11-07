package store.validator;

public class Validators {

    public void validateProductFormat(String product) {
        String regex = "^[가-힣0-9\\[\\],-]+$";
        if (!product.matches(regex)) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    public void validateYesOrNo(String yesNoInput) {
        if (!(yesNoInput.equalsIgnoreCase("y") || yesNoInput.equalsIgnoreCase("n"))) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
    }
}

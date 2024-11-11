package store.view;

import java.util.List;
import store.domain.Product;
import store.domain.Receipt;
import store.utils.Utils;

public class OutputView {

    private final Utils utils;

    public OutputView(Utils utils) {
        this.utils = utils;
    }

    public void startGame() {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.\n");
    }

    public void printProductList(List<Product> products) {
        for (Product product : products) {
            if (product.getQuantity() == 0) {
                System.out.println(
                        "- " + product.getName() + " " + product.getPriceUtil() + "원 " + product.getPromotionQuantity()
                                + "개 " + product.getPromotion());
            }
            if (product.getQuantity() != 0) {
                System.out.println(
                        "- " + product.getName() + " " + product.getPriceUtil() + "원 " + product.getQuantity()
                                + "개 " + product.getPromotion());
            }
        }
    }

    public void printReceiptList(List<Receipt> receiptList) {
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t\t수량\t금액");
        printList(receiptList);
    }

    private void printList(List<Receipt> receiptList) {
        for (Receipt receipt : receiptList) {
            int sum = receipt.getQuantity() + receipt.getPromotionQuantity();
            String fullPrice = utils.getPriceUtil(receipt.getPrice() * sum);
            System.out.println(receipt.getName() + "\t\t" + sum + "\t" + fullPrice);
        }
    }

    public void printPromotionDetail(List<Receipt> receiptList) {
        System.out.println("=============증\t정===============");
        for (Receipt receipt : receiptList) {
            if (receipt.getTargetQuantity() > 0) {
                System.out.println(receipt.getName() + "\t\t" + receipt.getTargetQuantity());
            }
        }
    }

    public void printReceiptListDetail(List<Receipt> receiptList, String membership) {
        System.out.println("====================================");
        int sumPrice = getSumPrice(receiptList);
        int targetPrice = getTargetPrice(receiptList);
        int membershipPrice = 0;
        if (membership.equals("Y")) {
            membershipPrice = getMembershipPrice(receiptList);
        }
        printFullReceipt(sumPrice, targetPrice, membershipPrice);
    }

    private int getTargetPrice(List<Receipt> receiptList) {
        int targetPrice = 0;
        for (Receipt receipt : receiptList) {
            if (receipt.getTargetQuantity() > 0) {
                targetPrice += receipt.getTargetQuantity() * receipt.getPrice();
            }
        }
        System.out.println("행사할인\t\t\t-" + utils.getPriceUtil(targetPrice));
        return targetPrice;
    }

    private int getMembershipPrice(List<Receipt> receiptList) {
        int membershipPrice = 0;
        for (Receipt receipt : receiptList) {
            membershipPrice = receipt.getPrice() * receipt.getQuantity();
        }
        membershipPrice = Math.min(8000, membershipPrice);
        System.out.println("멤버십할인\t\t\t-" + utils.getPriceUtil(membershipPrice));
        return membershipPrice;
    }

    private int getSumPrice(List<Receipt> receiptList) {
        int sumQuantity = 0;
        int sumPrice = 0;
        for (Receipt receipt : receiptList) {
            sumQuantity += receipt.getQuantity() + receipt.getPromotionQuantity();
            sumPrice += receipt.getPrice() * sumQuantity;
        }
        System.out.println("총구매액\t\t" + sumQuantity + "\t" + utils.getPriceUtil(sumPrice));
        return sumPrice;
    }

    private void printFullReceipt(int sumPrice, int targetPrice, int membershipPrice) {
        int fullPrice = sumPrice - targetPrice - membershipPrice;
        System.out.println("내실돈\t\t\t " + utils.getPriceUtil(fullPrice));
    }
}

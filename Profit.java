import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Profit {

    // private List<Point> traditionalSellerSupply = new ArrayList<Point>();
    // private List<Point> traditionalBuyerDemand = new ArrayList<Point>();
    // private List<Point> shiftedSellerSupply = new ArrayList<Point>();
    // private List<Point> modernBuyerDemand = new ArrayList<Point>();

    // public Profit(List<Point> traditionalSellerSupply, List<Point>
    // traditionalBuyerDemand, List<Point> shiftedSellerSupply, List<Point>
    // modernBuyerDemand) {
    public Profit() {
        // this.traditionalSellerSupply = traditionalSellerSupply;
        // this.traditionalBuyerDemand = traditionalBuyerDemand;
        // this.shiftedSellerSupply = shiftedSellerSupply;
        // this.modernBuyerDemand = modernBuyerDemand;
    }

    public double getTraditionalProfit(Point intersectPoint) {
        double profit = 0;

        profit = DoubleMath.mul(intersectPoint.getX(), intersectPoint.getY());
        // // Calculate Seller Profit
        // for (int i = 2; i < traditionalSellerSupply.size(); i += 2) {
        // if (traditionalSellerSupply.get(i).getX() >= intersectPoint.getX()
        // && traditionalSellerSupply.get(i - 1).getX() < intersectPoint.getX()) {
        // profit = DoubleMath.add(profit,
        // DoubleMath.mul(
        // Math.abs(DoubleMath.sub(traditionalSellerSupply.get(i).getY(),
        // intersectPoint.getY())),
        // DoubleMath.sub(intersectPoint.getX(), traditionalSellerSupply.get(i -
        // 1).getX())));
        // break;
        // } else {
        // profit = DoubleMath.add(profit,
        // DoubleMath.mul(
        // Math.abs(DoubleMath.sub(traditionalSellerSupply.get(i).getY(),
        // intersectPoint.getY())),
        // DoubleMath.sub(traditionalSellerSupply.get(i).getX(),
        // traditionalSellerSupply.get(i - 1).getX())));
        // }
        // }

        // // Calculate Buyer Profit
        // for (int i = 2; i < traditionalBuyerDemand.size(); i += 2) {
        // if (traditionalBuyerDemand.get(i).getX() >= intersectPoint.getX()
        // && traditionalBuyerDemand.get(i - 1).getX() < intersectPoint.getX()) {
        // profit = DoubleMath.add(profit,
        // DoubleMath.mul(
        // Math.abs(DoubleMath.sub(traditionalBuyerDemand.get(i).getY(),
        // intersectPoint.getY())),
        // DoubleMath.sub(intersectPoint.getX(), traditionalBuyerDemand.get(i -
        // 1).getX())));
        // break;
        // } else {
        // profit = DoubleMath.add(profit,
        // DoubleMath.mul(
        // Math.abs(DoubleMath.sub(traditionalBuyerDemand.get(i).getY(),
        // intersectPoint.getY())),
        // DoubleMath.sub(traditionalBuyerDemand.get(i).getX(),
        // traditionalBuyerDemand.get(i - 1).getX())));
        // }
        // }

        return profit;
    }

    // public double getModernProfit(Point intersectPoint) {
    public double getModernProfit(List<Point> modernBuyerDemand, List<Point> shiftedSellerSupply) {
        double profit = 0;
        int currentSeller = 2;
        int prevSeller = 0;
        int currentBuyer = 2;
        int prevBuyer = 0;

        double sellerRemain = 0;
        double buyerRemain = 0;
        double temp = 0;

        // while (shiftedSellerSupply.get(currentSeller).getX() < 0) {
        // currentSeller += 2;
        // }
        // prevSeller = currentSeller;
        // while (currentSeller < shiftedSellerSupply.size()) {
        // // seller
        // if (shiftedSellerSupply.get(currentSeller - 1).getX() <= 0) {
        // sellerRemain = shiftedSellerSupply.get(currentSeller).getX();
        // } else if (currentSeller == prevSeller) {
        // sellerRemain = temp;
        // } else {
        // sellerRemain = DoubleMath.sub(shiftedSellerSupply.get(currentSeller).getX(),
        // shiftedSellerSupply.get(currentSeller - 1).getX());
        // prevSeller = currentSeller;
        // }
        // // buyer
        // if (currentBuyer == prevBuyer) {
        // buyerRemain = temp;
        // } else {
        // buyerRemain = DoubleMath.sub(modernBuyerDemand.get(currentBuyer).getX(),
        // modernBuyerDemand.get(currentBuyer - 1).getX());
        // prevBuyer = currentBuyer;
        // }

        // if (sellerRemain > buyerRemain) {
        // temp = DoubleMath.sub(sellerRemain, buyerRemain);
        // profit = DoubleMath.add(profit,
        // DoubleMath.mul(buyerRemain,
        // DoubleMath.sub(modernBuyerDemand.get(currentBuyer).getY(),
        // shiftedSellerSupply.get(currentSeller).getY())));
        // currentBuyer += 2;
        // } else if (sellerRemain < buyerRemain) {
        // temp = DoubleMath.sub(buyerRemain, sellerRemain);
        // profit = DoubleMath.add(profit,
        // DoubleMath.mul(sellerRemain,
        // DoubleMath.sub(modernBuyerDemand.get(currentBuyer).getY(),
        // shiftedSellerSupply.get(currentSeller).getY())));
        // currentSeller += 2;
        // } else {
        // temp = 0;
        // profit = DoubleMath.add(profit,
        // DoubleMath.mul(sellerRemain,
        // DoubleMath.sub(modernBuyerDemand.get(currentBuyer).getY(),
        // shiftedSellerSupply.get(currentSeller).getY())));
        // currentSeller += 2;
        // currentBuyer += 2;
        // }

        // // break
        // if (modernBuyerDemand.get(currentBuyer).getX() >
        // shiftedSellerSupply.get(shiftedSellerSupply.size() - 1)
        // .getX()) {
        // break;
        // }
        // }

        for (int i = 2; i < shiftedSellerSupply.size() - 1; i += 2) {
            if (shiftedSellerSupply.get(i - 1).getX() <= 0 && shiftedSellerSupply.get(i).getX() <= 0) {
                continue;
            } else if (shiftedSellerSupply.get(i - 1).getX() <= 0 && shiftedSellerSupply.get(i).getX() > 0) {
                profit = DoubleMath.add(profit,
                        DoubleMath.mul(shiftedSellerSupply.get(i).getX(), shiftedSellerSupply.get(i).getY()));
            } else {
                profit = DoubleMath.add(profit, DoubleMath.mul(
                        DoubleMath.sub(shiftedSellerSupply.get(i).getX(), shiftedSellerSupply.get(i - 1).getX()),
                        shiftedSellerSupply.get(i).getY()));
            }
        }

        return profit;
    }

}

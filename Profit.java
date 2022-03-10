import java.util.ArrayList;
import java.util.List;

public class Profit {
    
    private List<Point> sellerSupply = new ArrayList<Point>();
    private List<Point> buyerDemand = new ArrayList<Point>();

    public Profit(List<Point> sellerSupply, List<Point> buyerDemand) {
        this.sellerSupply = sellerSupply;
        this.buyerDemand = buyerDemand;
    }

    public double getTraditionalProfit( Point intersectPoint) {
        double profit = 0;

        // Calculate Seller Profit
        for (int i = 2; i < sellerSupply.size(); i += 2) {
            if (sellerSupply.get(i).getX() >= intersectPoint.getX() && sellerSupply.get(i - 1).getX() < intersectPoint.getX()) {
                profit = DoubleMath.add(profit, DoubleMath.mul(Math.abs(DoubleMath.sub(sellerSupply.get(i).getY(), intersectPoint.getY())), DoubleMath.sub(intersectPoint.getX(), sellerSupply.get(i - 1).getX())));
                break;
            } else {
                profit = DoubleMath.add(profit, DoubleMath.mul(Math.abs(DoubleMath.sub(sellerSupply.get(i).getY(), intersectPoint.getY())), DoubleMath.sub(sellerSupply.get(i).getX(), sellerSupply.get(i - 1).getX())));
            }
        }

        // Calculate Buyer Profit
        for (int i = 2; i < buyerDemand.size(); i += 2) {
            if(buyerDemand.get(i).getX() >= intersectPoint.getX() && buyerDemand.get(i - 1).getX() < intersectPoint.getX()) {
                profit = DoubleMath.add(profit, DoubleMath.mul(Math.abs(DoubleMath.sub(buyerDemand.get(i).getY(), intersectPoint.getY())), DoubleMath.sub(intersectPoint.getX(), buyerDemand.get(i - 1).getX())));
                break;
            } else {
                profit = DoubleMath.add(profit, DoubleMath.mul(Math.abs(DoubleMath.sub(buyerDemand.get(i).getY(), intersectPoint.getY())), DoubleMath.sub(buyerDemand.get(i).getX(), buyerDemand.get(i - 1).getX())));
            }
         }

        return profit;
    }
}

import java.io.IOException;
import java.util.*;

public class mainTest {
    public static void main(String[] args) throws IOException {

        /************************
         * Generate Data
         ************************/
        
        // DataGenerator dataGenerator = new DataGenerator();
        DataGenerator dataGenerator = new DataGenerator(1);
        Users[] originalSeller;
        Users[] originalBuyers;
        Map<Integer, Double> sellersPriceMap = new HashMap<>();
        Map<Integer, Double> buyersPricMap = new HashMap<>();
        
        originalSeller = dataGenerator.getSellers();
        // Set Seller's Price to Map
        for (int i = 0; i < originalSeller.length; i++) {
            sellersPriceMap.put(i, originalSeller[i].getPrice());
        }
        // Print Result
        System.out.println();
        System.out.println("==============================");
        System.out.println("|    Generate Seller Data    |");
        System.out.println("==============================\n");
        for (Map.Entry<Integer, Double> entry : sellersPriceMap.entrySet()) {
            System.out.println("Seller: " + entry.getKey() + "\tAmount: " + originalSeller[entry.getKey()].getAmount() + "\tPrice: " + entry.getValue());
		}
        
        originalBuyers = dataGenerator.getBuyers();
        // Set Buyer's Price to Map
        for (int i = 0; i < originalBuyers.length; i++) {
            buyersPricMap.put(i, originalBuyers[i].getPrice());
        }
        // Print Result
        System.out.println();
        System.out.println("==============================");
        System.out.println("|    Generate buyer Data     |");
        System.out.println("==============================\n");
        for (Map.Entry<Integer, Double> entry : buyersPricMap.entrySet()) {
            System.out.println("Buyer: " + entry.getKey() + "\tAmount: " + originalBuyers[entry.getKey()].getAmount() + "\tPrice: " + entry.getValue());
		}
        
        /************************
         * Data Processing
         ************************/
        
        DataProcessing dataProcessing = new DataProcessing(originalSeller, originalBuyers, sellersPriceMap, buyersPricMap);
        List<Point> traditionalSellerSupply = dataProcessing.traditionalSellerSupply();
        List<Point> traditionalBuyerDemand = dataProcessing.traditionalBuyerDemand(dataGenerator.getGridPrice());
        
        // Print Chart Point Result
        // System.out.println();
        // for (int i = 0; i < traditionalSellerSupply.size(); i++) {
        //     System.out.println("X: " + traditionalSellerSupply.get(i).getX() + "\t\tY: " + traditionalSellerSupply.get(i).getY());
        // }
        // System.out.println();
        // for (int i = 0; i < traditionalBuyerDemand.size(); i++) {
        //     System.out.println("X: " + traditionalBuyerDemand.get(i).getX() + "\t\tY: " + traditionalBuyerDemand.get(i).getY());
        // }
        
        /************************
         * Draw Chart
         ************************/
        
        System.out.println();
        System.out.println("===============================");
        System.out.println("|  Draw Chart & Save to Image |");
        System.out.println("===============================\n");
        DrawChart drawChart = new DrawChart(traditionalSellerSupply, traditionalBuyerDemand);

        double ceil;
        double floor;
        
        if (traditionalSellerSupply.get(traditionalSellerSupply.size() - 2).getY() > traditionalBuyerDemand.get(1).getY()) {
            ceil = traditionalSellerSupply.get(traditionalSellerSupply.size() - 2).getY();
        } else {
            ceil = traditionalBuyerDemand.get(1).getY();
        }
        
        if (traditionalSellerSupply.get(1).getY() < traditionalBuyerDemand.get(traditionalBuyerDemand.size() - 2).getY()) {
            floor = traditionalSellerSupply.get(1).getY();
        } else {
            floor = traditionalBuyerDemand.get(traditionalBuyerDemand.size() - 2).getY();
        }
        
        drawChart.draw("Traditional_Supply_n_Demand_LineChart", floor, ceil);
        
        System.out.println();
        System.out.println("==============================");
        System.out.println("|      Find Equilibrium      |");
        System.out.println("==============================\n");
        
        Point intersectPoint = drawChart.getIntersectPoint();
        System.out.println("Equilibrium Point: \tPoint(" + intersectPoint.getX() + ", " + intersectPoint.getY() + ")");

        System.out.println();
        System.out.println("==============================");
        System.out.println("|      Calculate Profit      |");
        System.out.println("==============================\n");

        Profit traditionalProfit = new Profit(traditionalSellerSupply, traditionalBuyerDemand);
        System.out.println("Traditional Profit: " + traditionalProfit.getTraditionalProfit(intersectPoint));

        System.out.println("\n\n[+] Mission Completed !!!\n");
    }
}

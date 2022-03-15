import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

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
            System.out
                    .println("Seller: " + entry.getKey() + "\tQuantity: " + originalSeller[entry.getKey()].getQuantity()
                            + "\tPrice: " + entry.getValue());
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
            System.out
                    .println("Buyer: " + entry.getKey() + "\tQuantity: " + originalBuyers[entry.getKey()].getQuantity()
                            + "\tPrice: " + entry.getValue());
        }

        /************************
         * Data Processing
         ************************/

        DataProcessing dataProcessing = new DataProcessing(originalSeller, originalBuyers, sellersPriceMap,
                buyersPricMap, dataGenerator.getGridPrice());
        // List<Point> traditionalSellerSupply =
        // dataProcessing.traditionalSellerSupply();
        // List<Point> traditionalBuyerDemand =
        // dataProcessing.traditionalBuyerDemand(dataGenerator.getGridPrice());
        List<Point> traditionalSellerSupply = dataProcessing.getTraditionalSellerData();
        List<Point> modernSellerSupply = dataProcessing.getModernSellerData();
        List<Point> traditionalBuyerDemand = dataProcessing.getTraditionalBuyerData();
        List<Point> modernBuyerDemand = dataProcessing.getModernBuyerData();
        List<Entry<Integer, Double>> sortingSellersPrice = dataProcessing.getSortingSellersPrice();
        List<Entry<Integer, Double>> sortingBuyersPrice = dataProcessing.getSortingBuyersPrice();

        // Print Result
        System.out.println();
        System.out.println("==============================");
        System.out.println("|      Sorting By Price      |");
        System.out.println("==============================\n");
        System.out.println("sortingSellersPrice:");
        sortingSellersPrice.forEach(result -> System.out.println("Key: " +
                result.getKey() + "\t\tValue: " + result.getValue() + "\tQuantity: " +
                originalSeller[result.getKey()].getQuantity()));
        System.out.println();
        System.out.println("sortingBuyersPrice:");
        sortingBuyersPrice.forEach(result -> System.out.println("Key: " +
                result.getKey() + "\t\tValue: " + result.getValue() + "\tQuantity: " +
                originalBuyers[result.getKey()].getQuantity()));

        // for (int i = 0; i < modernSellerSupply.size(); i++) {
        // System.out.println(modernSellerSupply.get(i).getX() + "\t" +
        // modernSellerSupply.get(i).getY());
        // }

        // Print Chart Point Result
        // System.out.println();
        // for (int i = 0; i < traditionalSellerSupply.size(); i++) {
        // System.out.println("X: " + traditionalSellerSupply.get(i).getX() + "\t\tY: "
        // + traditionalSellerSupply.get(i).getY());
        // }
        // System.out.println("traditionalSellerSupply");
        // for (int i = 0; i < traditionalSellerSupply.size(); i++) {
        // System.out.println("X: " + traditionalSellerSupply.get(i).getX() + "\t\tY: "
        // + traditionalSellerSupply.get(i).getY());
        // }

        /************************
         * Draw Chart
         ************************/

        System.out.println();
        System.out.println("===============================");
        System.out.println("|  Draw Chart & Save to Image |");
        System.out.println("===============================\n");
        // DrawChart drawChartByTraditional = new DrawChart(traditionalSellerSupply,
        // traditionalBuyerDemand);
        // DrawChart drawChartByModern = new DrawChart(modernSellerSupply,
        // traditionalBuyerDemand);
        DrawChart drawChart = new DrawChart(traditionalSellerSupply, traditionalBuyerDemand, modernSellerSupply,
                modernBuyerDemand);

        double floor;
        double ceil;
        double quantity;

        // Floor
        if (traditionalSellerSupply.get(1).getY() < traditionalBuyerDemand.get(traditionalBuyerDemand.size() - 2)
                .getY()) {
            floor = traditionalSellerSupply.get(1).getY();
        } else {
            floor = traditionalBuyerDemand.get(traditionalBuyerDemand.size() - 2).getY();
        }

        // Ceiling
        if (traditionalSellerSupply.get(traditionalSellerSupply.size() - 2).getY() > traditionalBuyerDemand.get(1)
                .getY()) {
            ceil = traditionalSellerSupply.get(traditionalSellerSupply.size() - 2).getY();
        } else {
            ceil = traditionalBuyerDemand.get(1).getY();
        }

        // Quantity
        if (traditionalSellerSupply.get(traditionalSellerSupply.size() - 1).getX() > traditionalBuyerDemand
                .get(traditionalBuyerDemand.size() - 1).getX()) {
            quantity = traditionalSellerSupply.get(traditionalSellerSupply.size() - 1).getX();
        } else {
            quantity = traditionalBuyerDemand.get(traditionalBuyerDemand.size() - 1).getX();
        }

        // drawChartByTraditional.draw("Traditional_Supply_and_Demand_LineChart", floor,
        // ceil);
        // drawChartByModern.draw("Modern_Supply_and_Demand_LineChart", floor, ceil);
        drawChart.draw(floor, ceil, quantity);

        System.out.println();
        System.out.println("==============================");
        System.out.println("|    Find Intersect Point    |");
        System.out.println("==============================\n");

        Point traditionalIntersectPoint = drawChart.getTraditionalIntersectPoint();
        Point modernIntersectPoint = drawChart.getModernIntersectPoint();
        System.out.println("Traditional Intersect Point: (" + traditionalIntersectPoint.getX() + ", "
                + traditionalIntersectPoint.getY() + ")");
        System.out.println(
                "Modern Intersect Point: (" + modernIntersectPoint.getX() + ", " + modernIntersectPoint.getY() + ")");

        System.out.println();
        System.out.println("==============================");
        System.out.println("|      Calculate Profit      |");
        System.out.println("==============================\n");

        List<Point> shiftedSellerSupply = drawChart.getShiftedSellerSupply();
        // Profit profit = new Profit(traditionalSellerSupply, traditionalBuyerDemand,
        // shiftedSellerSupply,
        // modernBuyerDemand);
        Profit profit = new Profit();
        System.out.println("Traditional Profit: " + profit.getTraditionalProfit(traditionalIntersectPoint));
        // System.out.println("Modern Profit: " +
        // profit.getModernProfit(modernIntersectPoint));
        System.out.println("Modern Profit: " + profit.getModernProfit(modernBuyerDemand, shiftedSellerSupply));
        System.out.println("modernBuyerDemand");
        // for (int i = 0; i < modernBuyerDemand.size(); i++) {
        // System.out.println("X: " + modernBuyerDemand.get(i).getX() + "\t\tY: " +
        // modernBuyerDemand.get(i).getY());
        // }

        System.out.println("\n\n[+] Mission Completed !!!\n");
    }
}

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class mainTest {
    public static void main(String[] args) throws IOException {

        System.out.println("==============================");
        System.out.println("             Start            ");
        System.out.println("==============================");

        /************************
         * Generate Data
         ************************/

        // DataGenerator dataGenerator = new DataGenerator();
        // DataGenerator dataGenerator = new DataGenerator(1);
        DataGenerator dataGenerator = new DataGenerator(10, 20, 8.0, 2.0, 0.1, 7.9,
                5.0, 1.0, 0.1, 7.5, 4.0);
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
        System.out.println("[+] Generate Seller Data\n");
        for (Map.Entry<Integer, Double> entry : sellersPriceMap.entrySet()) {
            System.out
                    .println("\tSeller: " + entry.getKey() + "\tQuantity: "
                            + originalSeller[entry.getKey()].getQuantity()
                            + "\tPrice: " + entry.getValue());
        }

        originalBuyers = dataGenerator.getBuyers();
        // Set Buyer's Price to Map
        for (int i = 0; i < originalBuyers.length; i++) {
            buyersPricMap.put(i, originalBuyers[i].getPrice());
        }
        // Print Result
        System.out.println();
        System.out.println("[+] Generate Buyer Data\n");
        for (Map.Entry<Integer, Double> entry : buyersPricMap.entrySet()) {
            System.out
                    .println(
                            "\tBuyer: " + entry.getKey() + "\tQuantity: " + originalBuyers[entry.getKey()].getQuantity()
                                    + "\tPrice: " + entry.getValue());
        }

        /************************
         * For Loop 執行次數
         ************************/
        for (int i = 0; i < 20; i++) { // 改 i 調整執行次數

            System.out.println("\n==============================");
            System.out.println("             No " + (i + 1) + "            ");
            System.out.println("==============================");
            /************************
             * Data Processing
             ************************/

            DataProcessing dataProcessing = new DataProcessing(originalSeller, originalBuyers, sellersPriceMap,
                    buyersPricMap, dataGenerator.getGridPrice());
            List<Point> traditionalSellerSupply = dataProcessing.getTraditionalSellerData();
            List<Point> modernSellerSupply = dataProcessing.getModernSellerData();
            List<Point> traditionalBuyerDemand = dataProcessing.getTraditionalBuyerData();
            List<Point> modernBuyerDemand = dataProcessing.getModernBuyerData();
            List<Entry<Integer, Double>> sortingSellersPrice = dataProcessing.getSortingSellersPrice();
            List<Entry<Integer, Double>> sortingBuyersPrice = dataProcessing.getSortingBuyersPrice();

            // Print Result
            /*
             * System.out.println();
             * System.out.println("\t[+] Sorting By Price\n");
             * System.out.println("\t- sortingSellersPrice:\n");
             * sortingSellersPrice.forEach(result -> System.out.println("\t\tKey: " +
             * result.getKey() + "\t\tValue: " + result.getValue() + "\tQuantity: " +
             * originalSeller[result.getKey()].getQuantity()));
             * System.out.println();
             * System.out.println("\t- sortingBuyersPrice:\n");
             * sortingBuyersPrice.forEach(result -> System.out.println("\t\tKey: " +
             * result.getKey() + "\t\tValue: " + result.getValue() + "\tQuantity: " +
             * originalBuyers[result.getKey()].getQuantity()));
             */
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
            System.out.println("\t[+] Draw Chart & Save to Image\n");
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

            drawChart.draw(floor, ceil, quantity, i);

            /*
             * System.out.println();
             * System.out.println("\t[+] Find Intersect Point\n");
             */
            Point traditionalIntersectPoint = drawChart.getTraditionalIntersectPoint();
            Point modernIntersectPoint = drawChart.getModernIntersectPoint();
            /*
             * System.out.println("\t\tTraditional Intersect Point: (" +
             * traditionalIntersectPoint.getX() + ", "
             * + traditionalIntersectPoint.getY() + ")");
             * System.out.println(
             * "\t\tModern Intersect Point: (" + modernIntersectPoint.getX() + ", " +
             * modernIntersectPoint.getY()
             * + ")");
             */
            System.out.println();
            System.out.println("\t[+] Calculate Profit\n");

            List<Point> shiftedSellerSupply = drawChart.getShiftedSellerSupply();

            Profit profit = new Profit();
            System.out.println("\t\tTraditional Profit: " + profit.getTraditionalProfit(traditionalIntersectPoint));

            profit.calculateModernProfit(modernBuyerDemand, shiftedSellerSupply);
            System.out.println("\t\tModern Profit: ");
            System.out.println(
                    "\t\t\tSeller Profit = " + profit.getModernSellerProfit());
            System.out.println(
                    "\t\t\tBuyer Profit = " + profit.getModernBuyerProfit());
            System.out.println(
                    "\t\t\tTotal Profit = " + profit.getModernProfit());

            System.out.println("\n\t\t成交量: " + shiftedSellerSupply.get(shiftedSellerSupply.size() - 1).getX());

            System.out.println("\n\t\t均價: " + profit.getPriceAvg());
            System.out.println("\t\t最高: " + profit.getPriceMax());
            System.out.println("\t\t最低: " + profit.getPriceMin());

            /************************
             * Pricing Function
             ************************/

            double r_deal = 0.1; // r =================> 需求程度
            double r_notDeal = 0.4; // r =================> 需求程度

            // Seller
            for (Map.Entry<Integer, Double> entry : sellersPriceMap.entrySet()) {
                // 沒成交 P_(t,n+1) = P_(t,n) - r * (P_(t,n) - P_(t,n)^min)
                if (entry.getValue() > profit.getPriceMax()) {
                    originalSeller[entry.getKey()].setPrice(DoubleMath.sub(entry.getValue(),
                            DoubleMath.mul(r_notDeal, DoubleMath.sub(entry.getValue(), profit.getPriceMin()))));
                } else { // 成交 P_(t,n+1) = P_(t,n) - r * (P_(t,n) - P_(t,n)^avg)
                    originalSeller[entry.getKey()].setPrice(DoubleMath.sub(entry.getValue(),
                            DoubleMath.mul(r_deal, DoubleMath.sub(entry.getValue(), profit.getPriceAvg()))));
                }
            }

            // Buyer
            for (Map.Entry<Integer, Double> entry : buyersPricMap.entrySet()) {
                // 沒成交 P_(t,n+1) = P_(t,n) - r * (P_(t,n) - P_(t,n)^max)
                if (entry.getValue() < profit.getPriceMin()) {
                    originalBuyers[entry.getKey()].setPrice(DoubleMath.sub(entry.getValue(),
                            DoubleMath.mul(r_notDeal, DoubleMath.sub(entry.getValue(), profit.getPriceMax()))));
                } else { // 成交 P_(t,n+1) = P_(t,n) - r * (P_(t,n) - P_(t,n)^avg)
                    originalBuyers[entry.getKey()].setPrice(DoubleMath.sub(entry.getValue(),
                            DoubleMath.mul(r_deal, DoubleMath.sub(entry.getValue(), profit.getPriceAvg()))));
                }
            }

            // Reset Seller's Price to Map
            sellersPriceMap.clear();
            for (int j = 0; j < originalSeller.length; j++) {
                sellersPriceMap.put(j, originalSeller[j].getPrice());
            }
            // Print Result
            System.out.println();
            System.out.println("[+] New Seller Data\n");
            for (Map.Entry<Integer, Double> entry : sellersPriceMap.entrySet()) {
                System.out
                        .println("\tSeller: " + entry.getKey() + "\tQuantity: "
                                + originalSeller[entry.getKey()].getQuantity()
                                + "\tPrice: " + entry.getValue());
            }

            // Reset Buyer's Price to Map
            buyersPricMap.clear();
            for (int j = 0; j < originalBuyers.length; j++) {
                buyersPricMap.put(j, originalBuyers[j].getPrice());
            }
            // Print Result
            System.out.println();
            System.out.println("[+] New Buyer Data\n");
            for (Map.Entry<Integer, Double> entry : buyersPricMap.entrySet()) {
                System.out
                        .println(
                                "\tBuyer: " + entry.getKey() + "\tQuantity: "
                                        + originalBuyers[entry.getKey()].getQuantity()
                                        + "\tPrice: " + entry.getValue());
            }

        }
        System.out.println("\n\n==============================");
        System.out.println("  Mission Completed !!!!!!!   ");
        System.out.println("==============================\n");
    }
}

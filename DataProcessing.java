/**
 * DataProcessing
 */
import java.util.*;
import java.util.Map.Entry;

public class DataProcessing {

    private Users[] originalSeller;
    private Users[] originalBuyers;
    private Map<Integer, Double> sellersPriceMap = new HashMap<>();
    private Map<Integer, Double> buyersPricMap = new HashMap<>();

    List<Point> traditionalSellerData = new ArrayList<Point>();
    List<Point> traditionalBuyerData = new ArrayList<Point>();

    public DataProcessing(Users[] originalSeller, Users[] originalBuyers, Map<Integer, Double> sellersPriceMap, Map<Integer, Double> buyersPricMap) {
        this.originalSeller = originalSeller;
        this.originalBuyers = originalBuyers;
        this.sellersPriceMap = sellersPriceMap;
        this.buyersPricMap = buyersPricMap;
    }

    public List<Point> traditionalSellerSupply() {
        
        // Sorting by Price
        List<Entry<Integer, Double>> sortingSellersPrice = new ArrayList<>(sellersPriceMap.entrySet());
		sortingSellersPrice.sort(Entry.comparingByValue());
        // System.out.println();
		// sortingSellersPrice.forEach(result -> System.out.println("Key: " + result.getKey() + "\t\tValue: " + result.getValue() + "\tAmount: " + originalSeller[result.getKey()].getAmount()));
        Point prev = new Point(0,0);
        
        // Find Point
        for (int i = 0; i < sortingSellersPrice.size(); i++) {
            if (sortingSellersPrice.get(i).getValue() > prev.getY()) {
                traditionalSellerData.add(prev);
                prev = new Point(prev.getX(), sortingSellersPrice.get(i).getValue());

                traditionalSellerData.add(prev);
                prev = new Point(prev.getX(), prev.getY());
            }
            prev = new Point(Math.ceil((prev.getX() + originalSeller[sortingSellersPrice.get(i).getKey()].getAmount()) * 100) / 100, sortingSellersPrice.get(i).getValue());
            if (i + 1 == sortingSellersPrice.size()) {
                traditionalSellerData.add(prev);
                prev = new Point(prev.getX(), prev.getY());
            }
        }

        return traditionalSellerData;
        
    }
    
    public List<Point> traditionalBuyerDemand(double ceilPrice) {
        
        // Sorting by Price
        List<Entry<Integer, Double>> sortingBuyersPrice = new ArrayList<>(buyersPricMap.entrySet());
		sortingBuyersPrice.sort(Entry.<Integer, Double>comparingByValue().reversed());
        // System.out.println();
		// sortingBuyersPrice.forEach(result -> System.out.println("Key: " + result.getKey() + "\t\tValue: " + result.getValue() + "\tAmount: " + originalBuyers[result.getKey()].getAmount()));
        Point prev = new Point(0,ceilPrice);

        // Find Point
        for (int i = 0; i < sortingBuyersPrice.size(); i++) {
            if (sortingBuyersPrice.get(i).getValue() < prev.getY()) {
                traditionalBuyerData.add(prev);
                prev = new Point(prev.getX(), sortingBuyersPrice.get(i).getValue());

                traditionalBuyerData.add(prev);
                prev = new Point(prev.getX(), prev.getY());
            }
            prev = new Point(Math.ceil((prev.getX() + originalBuyers[sortingBuyersPrice.get(i).getKey()].getAmount()) * 100) / 100, sortingBuyersPrice.get(i).getValue());
            if (i + 1 == sortingBuyersPrice.size()) {
                traditionalBuyerData.add(prev);
                prev = new Point(prev.getX(), prev.getY());
            }
        }

        return traditionalBuyerData;

    }
}
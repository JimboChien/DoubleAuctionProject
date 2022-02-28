import java.io.File;
import java.io.IOException;
import java.util.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.awt.Color;

public class DrawChart {

    private List<Point> sellerSupply = new ArrayList<Point>();
    private List<Point> buyerDemand = new ArrayList<Point>();

    public DrawChart(List<Point> sellerSupply, List<Point> buyerDemand) {
        this.sellerSupply = sellerSupply;
        this.buyerDemand = buyerDemand;
    }

    public void draw(String saveFileName, double xFloor, double xCeil) throws IOException {

        System.out.println("Save Chart \"" + saveFileName + "\" to output Folder......");

        // Supply
        XYSeries supply = new XYSeries("Supply");
        for (int i = 0; i < sellerSupply.size(); i++) {
            supply.add(sellerSupply.get(i).getX(), sellerSupply.get(i).getY());
        }

        // Demand
        XYSeries demand = new XYSeries("Demand");
        for (int i = 0; i < buyerDemand.size(); i++) {
            demand.add(buyerDemand.get(i).getX(), buyerDemand.get(i).getY());
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(supply);
        dataset.addSeries(demand);

        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                "Traditional Supply and Demand",
                "Quantity",
                "Price",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        XYPlot xyPlot = (XYPlot) xylineChart.getPlot();
        xyPlot.setDomainCrosshairVisible(true);
        xyPlot.setRangeCrosshairVisible(true);
        XYItemRenderer renderer = xyPlot.getRenderer();
        renderer.setSeriesPaint(0, Color.blue);
        renderer.setSeriesPaint(1, Color.red);

        // Change Y-Axis Range
        // NumberAxis domain = (NumberAxis) xyPlot.getDomainAxis();
        // domain.setRange(0.00, 1.00);
        // domain.setTickUnit(new NumberTickUnit(0.1));
        // domain.setVerticalTickLabels(true);

        // Change X-Axis Range
        NumberAxis range = (NumberAxis) xyPlot.getRangeAxis();
        range.setRange(xFloor - 0.2, xCeil + 0.2);
        range.setTickUnit(new NumberTickUnit(1));

        // Save Chart to Image
        int width = 640; /* Width of the image */
        int height = 480; /* Height of the image */

        String outputDirectory = "./output";
        File directory = new File(outputDirectory);
        if (!directory.exists()) {
            System.out.println("新建 output 資料夾中...");
            directory.mkdir();
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }

        File XYChart = new File("./output/" + saveFileName + ".jpeg");
        ChartUtils.saveChartAsJPEG(XYChart, xylineChart, width, height);

        System.out.println("Done !!!");
    }

    public Point getIntersectPoint() {
        boolean intersected;
        for (int i = 0; i < sellerSupply.size() - 1; i++) {
            for (int j = 0; j < buyerDemand.size() - 1; j++) {
                intersected = intersectCheck(sellerSupply.get(i), sellerSupply.get(i + 1), buyerDemand.get(j),
                        buyerDemand.get(j + 1));
                if (intersected) {
                    System.out.println("Find Instersected:\n\tSeller Point 1\t(" + sellerSupply.get(i).getX() + ", "
                            + sellerSupply.get(i).getY() + ")\tSeller Point 2\t(" + sellerSupply.get(i + 1).getX()
                            + ", " + sellerSupply.get(i + 1).getY() + ")\n\tBuyer Point 1\t("
                            + buyerDemand.get(j).getX() + ", " + buyerDemand.get(j).getY() + ")\tBuyer Point 2\t("
                            + buyerDemand.get(j + 1).getX() + ", " + buyerDemand.get(j + 1).getY() + ")\n");
                    return intersectPoint(sellerSupply.get(i), sellerSupply.get(i + 1), buyerDemand.get(j),
                            buyerDemand.get(j + 1));
                }
            }
        }
        return null;
    }

    private Boolean intersectCheck(Point sellerP1, Point sellerP2, Point buyerP1, Point buyerP2) {

        // x y 座標是否重疊
        if (Math.max(sellerP1.getX(), sellerP2.getX()) < Math.min(buyerP1.getX(), buyerP2.getX())
                || Math.max(sellerP1.getY(), sellerP2.getY()) < Math.min(buyerP1.getY(), buyerP2.getY())
                || Math.max(buyerP1.getX(), buyerP2.getX()) < Math.min(sellerP1.getX(), sellerP2.getY())
                || Math.max(buyerP1.getY(), buyerP2.getY()) < Math.min(sellerP1.getY(), sellerP2.getY())) {

            return false;
        }
        // 不相交則向量外積則大於0
        double a = sellerP1.getX() - buyerP1.getX();
        double b = buyerP2.getY() - buyerP1.getY();
        double c = sellerP1.getY() - buyerP1.getY();
        double d = buyerP2.getX() - buyerP1.getX();
        double e = sellerP2.getX() - buyerP1.getX();
        double f = sellerP2.getY() - buyerP1.getY();
        double g = buyerP1.getX() - sellerP1.getX();
        double h = sellerP2.getY() - sellerP1.getY();
        double i = buyerP1.getY() - sellerP1.getY();
        double j = sellerP2.getX() - sellerP1.getX();
        double k = buyerP2.getX() - sellerP1.getX();
        double l = buyerP2.getY() - sellerP1.getY();
        if (((a * b - c * d) * (e * b - f * d)) > 0
                || ((g * h - i * j) * (k * h - l * j)) > 0) {

            return false;
        }
        return true;
    }

    private Point intersectPoint(Point sellerP1, Point sellerP2, Point buyerP1, Point buyerP2) {
        double sellerYDiff = sellerP1.getY() - sellerP2.getY();
        double sellerXDiff = sellerP2.getX() - sellerP1.getX();
        double sellerVector = sellerYDiff * sellerP1.getX() + sellerXDiff * sellerP1.getY();

        double buyerYDiff = buyerP1.getY() - buyerP2.getY();
        double buyerXDiff = buyerP2.getX() - buyerP1.getX();
        double buyerVector = buyerYDiff * buyerP1.getX() + buyerXDiff * buyerP1.getY();

        double det_k = sellerYDiff * buyerXDiff - buyerYDiff * sellerXDiff;

        if (Math.abs(det_k) < 0.00001) {
            return null;
        }

        double a = buyerXDiff / det_k;
        double b = -1 * sellerXDiff / det_k;
        double c = -1 * buyerYDiff / det_k;
        double d = sellerYDiff / det_k;

        double x = Math.ceil((a * sellerVector + b * buyerVector) * 100) / 100;
        double y = Math.ceil((c * sellerVector + d * buyerVector) * 100) / 100;

        return new Point(x, y);
    }

    public double getTraditionalProfit(Point equilibriumPoint) {
        return equilibriumPoint.getX() * equilibriumPoint.getY();
    }
}

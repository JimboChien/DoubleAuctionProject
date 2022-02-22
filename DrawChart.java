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

    List<Point> sellerSupply = new ArrayList<Point>();
    List<Point> buyerDemand = new ArrayList<Point>();

    public DrawChart(List<Point> sellerSupply) {
        this.sellerSupply = sellerSupply;
    }

    public DrawChart(List<Point> sellerSupply, List<Point> buyerDemand) {
        this.sellerSupply = sellerSupply;
        this.buyerDemand = buyerDemand;
    }

    public void draw() throws IOException {

        // Supply
        XYSeries supply = new XYSeries( "Supply" );
        for (int i = 0; i < sellerSupply.size(); i++) {
            supply.add(sellerSupply.get(i).getX(), sellerSupply.get(i).getY());
        }
        
        // Demand
        XYSeries demand = new XYSeries( "Demand" );
        for (int i = 0; i < buyerDemand.size(); i++) {
            demand.add(buyerDemand.get(i).getX(), buyerDemand.get(i).getY());
        }
        
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries( supply );
        dataset.addSeries( demand );

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
        range.setRange(3.5, 8.5);
        range.setTickUnit(new NumberTickUnit(1));
         
        int width = 640; /* Width of the image */
        int height = 480; /* Height of the image */ 
        File XYChart = new File( "Traditional_Supply_n_Demand_LineChart.jpeg" ); 
        ChartUtils.saveChartAsJPEG( XYChart, xylineChart, width, height);
        
    }   
    
}


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class plotterSalterSmoother {

    // Method to generate square root data
    public List<Double[]> generateSquareRootData(double start, double end, double step) {
        List<Double[]> data = new ArrayList<>();
        for (double x = start; x <= end; x += step) {
            if (x < 0) {
                continue; // Skip negative values for square root
            }
            double y = Math.sqrt(x);
            data.add(new Double[]{x, y});
        }
        return data;
    }

    // Method to salt data
    public List<Double[]> saltData(List<Double[]> data, double saltAmount) {
        List<Double[]> saltedData = new ArrayList<>();
        Random random = new Random();
        for (Double[] point : data) {
            double x = point[0];
            double y = point[1] + (random.nextDouble() * 2 - 1) * saltAmount;
            saltedData.add(new Double[]{x, y});
        }
        return saltedData;
    }

    // Method to smooth data
    public List<Double[]> smoothData(List<Double[]> data, int windowSize) {
        List<Double[]> smoothedData = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            double sum = 0;
            int count = 0;
            for (int j = Math.max(0, i - windowSize); j <= Math.min(i + windowSize, data.size() - 1); j++) {
                sum += data.get(j)[1];
                count++;
            }
            smoothedData.add(new Double[]{data.get(i)[0], sum / count});
        }
        return smoothedData;
    }

    // Method to create and save a chart
    public void createAndSaveChart(String title, String filename, List<Double[]> data) throws IOException {
        XYSeries series = new XYSeries(title);
        for (Double[] point : data) {
            series.add(point[0], point[1]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
            title,
            "X",
            "Y",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );

        ChartUtilities.saveChartAsPNG(new File(filename), chart, 800, 600);
    }
}


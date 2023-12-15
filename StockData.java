import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class StockData {
    // Lists to store stock data: dates, opening prices, smoothed opening prices, and RSI values
    private ArrayList<String> dates;
    private ArrayList<Double> opens;
    private ArrayList<Double> smoothedOpens;
    private ArrayList<Double> rsiValues;

    // File names for input and output
    private static final String FILENAME = "AMZN.csv"; // File name
    private static final String OUTPUT_FILENAME = "Smoothed_AMZN.csv"; // Output file name
    private int windowSize = 50; // Default window size for smoothing
    private static final String RSI_OUTPUT_FILENAME = "RSI_AMZN.csv";
    private int rsiPeriod = 14;

    public StockData()
    {
        dates = new ArrayList<>();
        opens = new ArrayList<>();
        smoothedOpens = new ArrayList<>();
        rsiValues = new ArrayList<>();
    }

    // Getters for accessing the data
    public ArrayList<Double> getOpens() {
        return opens;
    }

    public ArrayList<Double> getSmoothedOpens() {
        return smoothedOpens;
    }

    public ArrayList<String> getDates() {
        return dates;
    }

    // Read stock data from a file
    public void readDataFromFile() {

        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            br.readLine();  // Skipping the header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String date = parts[0];
                double open = Double.parseDouble(parts[1].trim());

                dates.add(date);
                opens.add(open);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        rsiValues = calculateRSI(14);
        smoothOpenValues();
    }

    // Smooth opening prices using a moving average
    public void smoothOpenValues() {
        smoothedOpens.clear(); // Clear existing smoothed values

        for (int i = 0; i < opens.size(); i++) {
            double sum = 0;
            int count = 0;
            for (int j = Math.max(0, i - windowSize + 1); j <= Math.min(i + windowSize - 1, opens.size() - 1); j++) {
                sum += opens.get(j);
                count++;
            }
            smoothedOpens.add(sum / count);
        }
    }

    // Write the smoothed data to a file
    public void writeSmoothedDataToFile() {
        try (FileWriter writer = new FileWriter(OUTPUT_FILENAME)) {
            writer.write("Date,Original_Open,Open_Smoothed\n");

            for (int i = 0; i < dates.size(); i++) {
                writer.write(dates.get(i) + "," + opens.get(i) + "," + smoothedOpens.get(i) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    // Calculate the Relative Strength Index (RSI) for the stock data
    public ArrayList<Double> calculateRSI(int period) {
        ArrayList<Double> rsi = new ArrayList<>();
        ArrayList<Double> gains = new ArrayList<>();
        ArrayList<Double> losses = new ArrayList<>();

        // Step 1: Calculate daily price changes
        for (int i = 1; i < opens.size(); i++) {
            double change = opens.get(i) - opens.get(i - 1);
            if (change > 0) {
                gains.add(change);
                losses.add(0.0);
            } else {
                gains.add(0.0);
                losses.add(Math.abs(change));
            }
        }

        double avgGain = 0, avgLoss = 0;

        // Step 2: Calculate initial averages of gains and losses
        for (int i = 0; i < period; i++) {
            avgGain += gains.get(i);
            avgLoss += losses.get(i);
        }
        avgGain /= period;
        avgLoss /= period;

        // Step 3: Calculate RSI for each day
        for (int i = period; i < gains.size(); i++) {
            avgGain = (avgGain * (period - 1) + gains.get(i)) / period;
            avgLoss = (avgLoss * (period - 1) + losses.get(i)) / period;

            double rs = avgGain / avgLoss;
            rsi.add(100 - (100 / (1 + rs)));
        }

        // Add initial RSI values as null or a default value, as RSI is not defined for the first few days
        for (int i = 0; i < period; i++) {
            rsi.add(0, null); // Or set it to a default value if preferred
        }

        return rsi;
    }

    public void writeRSIToCSV() {
        ArrayList<Double> rsiValues = calculateRSI(rsiPeriod);

        try (FileWriter writer = new FileWriter(RSI_OUTPUT_FILENAME)) {
            writer.write("Date,RSI\n");

            // Start the loop from 'rsiPeriod' to ensure indices are in bounds for rsiValues
            for (int i = rsiPeriod; i < dates.size(); i++) {
                // Adjusted index for rsiValues as it has fewer elements
                String rsiString = rsiValues.get(i - rsiPeriod) != null ? String.format("%.2f", rsiValues.get(i - rsiPeriod)) : "N/A";
                writer.write(dates.get(i) + "," + rsiString + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}

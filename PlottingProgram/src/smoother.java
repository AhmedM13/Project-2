import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class smoother {
    private String inputFile;
    private String outputFile;

    public smoother(String inputFile, String outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    /**
     * Smooths the salted sqrt(x) function 
     * @param windowSize the amount you want to smooth
     */
    public void smoothData(int windowSize) {
        List<Double> yValues = new ArrayList<>();
        List<Double> xValues = new ArrayList<>();
        List<Double> smoothedYValues = new ArrayList<>();

        // Load data from CSV file into the ArrayList
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            reader.readLine(); // Skip header

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                xValues.add(Double.parseDouble(values[0]));
                yValues.add(Double.parseDouble(values[1]));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        // Apply smoothing
        for (int i = 0; i < yValues.size(); i++) {
            double sum = 0;
            int count = 0;
            for (int j = Math.max(0, i - windowSize + 1); j <= Math.min(i + windowSize - 1, yValues.size() - 1); j++) 
            {
                sum += yValues.get(j);
                count++;
            }
            smoothedYValues.add(sum / count);
        }

        // Write smoothed data to new CSV file
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write("x,y_smoothed\n");

            for (int i = 0; i < xValues.size(); i++) {
                writer.write(xValues.get(i) + "," + smoothedYValues.get(i) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class salter {
    private String inputFile;
    private String outputFile;

    public salter(String inputFile, String outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }
    
    /**
     * Salts the sqrt(x) graph
     * @param saltAmount amount you want to salt
     */
    public void saltData(double saltAmount) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             FileWriter writer = new FileWriter(outputFile)) {

            Random random = new Random();
            
            writer.write("x,y\n");
            // Skip the header of the input file
            reader.readLine();
            
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                double x = Double.parseDouble(values[0]);
                double y = Double.parseDouble(values[1]) + (random.nextDouble() * 2 - 1) * saltAmount;
                writer.write(x + "," + y + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error processing file: " + e.getMessage());
        }
    }
}


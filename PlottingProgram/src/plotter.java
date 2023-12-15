import java.io.FileWriter;
import java.io.IOException;

public class plotter 
{
	private String fileName;
	
    public plotter(String fileName) 
    {
        this.fileName = fileName;
    }
    
    /**
     * Generates the sqrt(x) graph in a csv
     * @param start the starting x value, 
     * @param end the last x value
     * @param step how much you want to increment by
     */
    public void generateSquareRootPoints(double start, double end, double step) 
    {
        try (FileWriter writer = new FileWriter(fileName)) 
        {
            writer.write("x,y\n"); // CSV header

            for (double x = start; x <= end; x += step) 
            {
                if (x < 0) 
                {
                    continue; // Skip negative values for square root
                }
                double y = Math.sqrt(x);
                writer.write(x + "," + y + "\n");
            }
        } catch (IOException e) 
        {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

}

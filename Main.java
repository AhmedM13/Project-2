public class Main
{
    public static void main(String[] args)
    {
        StockData loader = new StockData();
        loader.readDataFromFile();
        loader.smoothOpenValues();
        loader.writeSmoothedDataToFile();
        loader.writeRSIToCSV();

        double startingBalance = 10000.0;

        // Create and initialize an instance of StockData
        StockData stockData = new StockData();
        stockData.readDataFromFile();

        Bot simulation = new Bot(startingBalance, stockData);
        simulation.runSimulation();
    }
}

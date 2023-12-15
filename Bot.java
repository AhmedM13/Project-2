import java.util.ArrayList;
import java.util.Random;

public class Bot {
    private double balance;
    private int sharesOwned;
    private StockData stockData;
    private static final int MAX_SHARES_TO_TRADE = 100; // Max number of shares to buy/sell
    private Random random;

    public Bot(double startingBalance, StockData stockData) {
        this.balance = startingBalance;
        this.sharesOwned = 0;
        this.stockData = stockData;
        this.random = new Random();
    }

    public double getBalance() {
        return balance;
    }

    public int getSharesOwned() {
        return sharesOwned;
    }

    public void runSimulation() {
        ArrayList<String> dates = stockData.getDates();

        for (int i = 0; i < dates.size(); i++) {
            int tradeAction = tradeEvaluator(i); // Determine trade action
            executeTrade(i, tradeAction); // Execute trade
            reportNetWorth(i); // Report net worth
        }
    }

    private int tradeEvaluator(int index) {
        int action = random.nextInt(3); // 0: do nothing, 1: buy, 2: sell
        double currentPrice = stockData.getOpens().get(index);

        switch (action) {
            case 1: // Buy
                return Math.min(MAX_SHARES_TO_TRADE, (int) (balance / currentPrice));
            case 2: // Sell
                return -Math.min(MAX_SHARES_TO_TRADE, sharesOwned);
            default: // Do nothing
                return 0;
        }
    }

    private void executeTrade(int index, int tradeAction) {
        double currentPrice = stockData.getOpens().get(index);
        if (tradeAction > 0) { // Buy
            int sharesToBuy = tradeAction;
            double cost = sharesToBuy * currentPrice;
            if (cost <= balance) {
                balance -= cost;
                sharesOwned += sharesToBuy;
            }
        } else if (tradeAction < 0) { // Sell
            int sharesToSell = -tradeAction;
            double revenue = sharesToSell * currentPrice;
            balance += revenue;
            sharesOwned -= sharesToSell;
        }
    }

    private void reportNetWorth(int index) {
        double currentPrice = stockData.getOpens().get(index);
        double netWorth = balance + (sharesOwned * currentPrice);
        System.out.println("Date: " + stockData.getDates().get(index) + ", Net Worth: " + netWorth);
    }
}

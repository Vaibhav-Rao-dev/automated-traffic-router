public class TradingEngine {
    public static void main(String[] args) {
        double buyPrice = 1000.0; // Example Buy Price
        double currentPrice = 1250.0; // Simulated Live Price from API

        double profitTarget = buyPrice * 1.20; // 20% Profit
        double stopLoss = buyPrice * 0.95;    // 5% Loss

        System.out.println("Analyzing Market...");

        if (currentPrice >= profitTarget) {
            System.out.println("CRITICAL: Take Profit Triggered at 20%");
            // executeTrade("SELL", "NIFTY50_STOCK");
        } else if (currentPrice <= stopLoss) {
            System.out.println("CRITICAL: Stop Loss Triggered at 5%");
            // executeTrade("SELL", "NIFTY50_STOCK");
        } else {
            System.out.println("Market stable. No action required.");
        }
    }
}

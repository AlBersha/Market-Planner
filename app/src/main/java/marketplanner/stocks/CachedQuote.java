package marketplanner.stocks;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CachedQuote {
    public String symbol;
    public String cacheDate;
    public String lastTradeDate;
    public String prevDayQuote;
    public String recentQuote;
    public String companyName;

    public CachedQuote(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPrice() {
        if (recentQuote == null)
            return null;
        return new BigDecimal(recentQuote);
    }
    public BigDecimal getPrevPrice() {
        if (prevDayQuote == null)
            return null;
        return new BigDecimal(prevDayQuote);
    }

    public static String getCompanyName(CachedQuote quote) {
        if (quote == null)
            return null;
        return quote.companyName;
    }

    public static BigDecimal getPrice(CachedQuote quote) {
        if (quote == null)
            return null;
        return quote.getPrice();
    }

    public static BigDecimal getPriceChange(CachedQuote quote) {
        if (quote == null || quote.getPrice() == null || quote.getPrevPrice() == null)
            return null;
        return quote.getPrice().subtract(quote.getPrevPrice());
    }

    public static BigDecimal getPercentChange(CachedQuote quote) {
        BigDecimal change = getPriceChange(quote);
        if (change == null)
            return null;
        change = change.divide(quote.getPrevPrice(), 4, RoundingMode.HALF_UP);
        return change.multiply(new BigDecimal(100));
    }
}

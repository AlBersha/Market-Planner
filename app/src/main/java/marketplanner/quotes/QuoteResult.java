package marketplanner.quotes;

import android.os.Bundle;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class QuoteResult {
    public static final String SYMBOL_NOT_FOUND = "symbol_not_found";

    public boolean success;
    public String symbol;
    public String lastTradeDate;
    public String prevDayQuote;
    public String recentQuote;
    public String companyName;
    public String error;

    public static QuoteResult deserialize(Bundle b) {
        QuoteResult result = new QuoteResult();
        result.lastTradeDate = b.getString("last_trade_date");
        result.prevDayQuote = b.getString("prev_day_quote");
        result.recentQuote = b.getString("recent_quote");
        result.success = b.getBoolean("success");
        result.symbol = b.getString("symbol");
        result.companyName = b.getString("company_name");
        result.error = b.getString("error");
        return result;
    }

    public Bundle serialize() {
        Bundle b = new Bundle();
        b.putString("symbol", this.symbol);
        b.putBoolean("success", this.success);
        b.putString("last_trade_date", this.lastTradeDate);
        b.putString("prev_day_quote", this.prevDayQuote);
        b.putString("recent_quote", this.recentQuote);
        b.putString("company_name", this.companyName);
        b.putString("error", this.error);
        return b;
    }

    // We don't make use of this yet, but in theory we could remember that a symbol isn't found
    // to avoid re-querying it and incurring API use.
    public boolean notFound() {
        return !success && error != null && error.equals(SYMBOL_NOT_FOUND);
    }

    public static String normalizeTimestamp(long ts) {
        // Timezone is not really correct here but whatever.
        Date date = new Date(ts);
        LocalDate local = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return local.getYear() + "-" + local.getMonthValue() + "-" + local.getDayOfMonth();
    }
}

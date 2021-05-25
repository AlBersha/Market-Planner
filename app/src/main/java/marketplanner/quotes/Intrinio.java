package marketplanner.quotes;

import marketplanner.stocks.UrlBuilder;
import marketplanner.stocks.Utilities;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Intrinio implements IQuoteService
{
    private static final String TAG = "Intrinio";
    private static final String BASE_URL = "https://api-v2.intrinio.com/";

    private String api_key_;

    public Intrinio(String api_key) {
        api_key_ = api_key;
    }

    @Override
    public void shutdown() {
    }

    @Override
    public List<QuoteResult> query(List<QuoteRequest> requests) throws Exception {
        ArrayList<QuoteResult> results = new ArrayList<QuoteResult>();
        for (QuoteRequest request : requests) {
            QuoteResult result;
            try {
                result = query(request);
            } catch (Exception e) {
                result = new QuoteResult();
                result.success = false;
                result.error = e.getMessage();
            }
            results.add(result);
        }
        return results;
    }

    private QuoteResult query(QuoteRequest request) throws Exception {
        QuoteResult result = new QuoteResult();
        result.symbol = request.symbol;

        String base = BASE_URL + "securities/" + request.symbol + "/prices/realtime";
        UrlBuilder builder = new UrlBuilder(base);
        builder.addParam("api_key", api_key_);
        URL url = builder.getUrl();

        // Note: the Intrinio API embeds numbers in JSON rather than strings, which is nonsense
        // for currency values. We just coerce it to String here which is lossy. We would probably
        // need a custom JSON parser or JSONTokener to fix this.
        JSONObject object = UrlBuilder.downloadUrlAsJson(url);
        if (object.has("error"))
            throw new Exception(object.getString("error"));
        result.recentQuote = Double.toString(object.getDouble("last_price"));

        // This is not really correct use of timezones, but whatever.
        LocalDate trade_date = Utilities.parseIsoDateTime(object.getString("last_time"));
        result.lastTradeDate = Utilities.toYearMonthDay(trade_date);

        if (request.fetchName || request.isSameDate(result.lastTradeDate))
            fillExtraQuoteData(request, result);

        result.success = true;
        return result;
    }

    private void fillExtraQuoteData(QuoteRequest request, QuoteResult result) throws Exception {
        String base = BASE_URL + "securities/" + request.symbol + "/prices";
        UrlBuilder builder = new UrlBuilder(base);
        builder.addParam("api_key", api_key_);
        builder.addParam("page_size", "2");
        URL url = builder.getUrl();

        JSONObject object = UrlBuilder.downloadUrlAsJson(url);
        if (object.has("error"))
            throw new Exception(object.getString("error"));

        if (object.has("security")) {
            JSONObject security = object.getJSONObject("security");
            result.companyName = security.getString("name");
        }

        JSONArray prices = object.getJSONArray("stock_prices");
        for (int i = 0; i < prices.length(); i++) {
            JSONObject price = prices.getJSONObject(i);
            String lastTradeDate = price.getString("date");
            if (!result.lastTradeDate.equals(lastTradeDate)) {
                result.prevDayQuote = Double.toString(price.getDouble("close"));
                break;
            }
        }
    }

    @Override
    public int getBatchSize() {
        return 1;
    }

    @Override
    public SymbolSuggestion[] suggestSymbols(String prefix) {
        try {
            JSONArray results = search(prefix);
            SymbolSuggestion[] suggestions = new SymbolSuggestion[results.length()];
            for (int i = 0; i < results.length(); i++) {
                JSONObject object = results.getJSONObject(i);
                SymbolSuggestion suggestion = new SymbolSuggestion();
                suggestion.companyName = object.getString("name");
                suggestion.symbol = object.getString("ticker");
                suggestions[i] = suggestion;
            }
            return suggestions;
        } catch (Exception e) {
            return new SymbolSuggestion[0];
        }
    }

    JSONArray search(String prefix) throws Exception {
        String base = BASE_URL + "securities/search";
        UrlBuilder builder = new UrlBuilder(base);
        builder.addParam("api_key", api_key_);
        builder.addParam("query", prefix);
        builder.addParam("page_size", "10");
        URL url = builder.getUrl();
        JSONObject object = UrlBuilder.downloadUrlAsJson(url);
        if (object.has("error"))
            throw new Exception(object.getString("error"));
        return object.getJSONArray("securities");
    }

    @Override
    public void prefetchSearchData() throws InterruptedException {
    }
}

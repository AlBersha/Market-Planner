package marketplanner.quotes;

import android.os.Bundle;

import androidx.annotation.NonNull;

public class SymbolSuggestion {
    public String symbol;
    public String companyName;

    public static SymbolSuggestion deserialize(Bundle b) {
        SymbolSuggestion s = new SymbolSuggestion();
        s.companyName = b.getString("companyName");
        s.symbol = b.getString("symbol");
        return s;
    }

    public Bundle serialize() {
        Bundle b = new Bundle(2);
        b.putString("companyName", companyName);
        b.putString("symbol", symbol);
        return b;
    }

    @NonNull
    @Override
    public String toString() {
        return companyName + " | " + symbol;
    }
}

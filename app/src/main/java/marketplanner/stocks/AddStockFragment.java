package marketplanner.stocks;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import marketplanner.quotes.IQuoteService;
import marketplanner.quotes.SymbolSuggestion;
import marketplanner.stocks.databinding.FragmentAddStockBinding;

public class AddStockFragment extends Fragment {
    private Settings settings_;
    private IQuoteService quote_service_;
    private SymbolSearchAdapter search_adapter_;
    private SymbolSearchThread thread_;
    private Handler message_handler_;

    private FragmentAddStockBinding binding;

    public AddStockFragment() {
    }

//    public static AddStockFragment newInstance(String param1, String param2) {
//        AddStockFragment fragment = new AddStockFragment();
//        Bundle args = new Bundle();
//
//        fragment.setArguments(args);
//        return fragment;
//    }

    public void onCreate(LayoutInflater inflater, ViewGroup container ,
                         Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_add_stock, container, false);

        settings_ = StockApplication.getSettings();

        if (!initializeQuoteService())
            return;

        SymbolSearchThread.OnSearchResults callback = suggestions -> postSearchResultMessage(suggestions);

        message_handler_ = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                handleSearchMessage(msg);
            }
        };

        thread_ = new SymbolSearchThread(quote_service_, callback);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_stock, container, false);
    }


    @Override
    public void onDestroy() {
        if (quote_service_ != null) {
            quote_service_.shutdown();
            quote_service_ = null;
        }
        if (thread_ != null) {
            thread_.shutdown();
            thread_ = null;
        }
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (quote_service_ == null)
            return;

        final AutoCompleteTextView view = (AutoCompleteTextView) binding.symbolSearch;
        view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                performSearch(s.toString());
            }
        });

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
                SymbolSuggestion suggestion = search_adapter_.getItem(position);
                view.setText(suggestion.symbol);
                addSymbol(suggestion.symbol, suggestion.companyName);
            }
        });

        view.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        actionId == EditorInfo.IME_ACTION_NEXT)
                {
                    addSymbol(v.getText().toString(), null);
                    return true;
                }
                return false;
            }
        });

        search_adapter_ = new SymbolSearchAdapter(getContext(), R.layout.stock_suggestion);
        view.setAdapter(search_adapter_);
        view.setThreshold(0);
    }

    private void addSymbol(String symbol, String companyName) {
        settings_.addSymbol(symbol, companyName);
//        finish();
    }

    // Executed in the worker thread.
    private void performSearch(String text) {
        if (text.length() == 0)
            return;

        thread_.beginSearch(text);
    }

    private boolean initializeQuoteService() {
        quote_service_ = settings_.createQuoteService();
        if (quote_service_ == null) {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
            return false;
        }
        return true;
    }

    // This is invoked in the search thread.
    private void postSearchResultMessage(SymbolSuggestion[] suggestions) {
        Bundle b = new Bundle();
        b.putInt("count", suggestions.length);
        for (int i = 0; i < suggestions.length; i++) {
            Bundle item = suggestions[i].serialize();
            b.putBundle(Integer.toString(i), item);
        }

        Message msg = new Message();
        msg.setAsynchronous(true);
        msg.setData(b);
        message_handler_.sendMessage(msg);
    }

    private void handleSearchMessage(Message msg) {
        Bundle b = msg.getData();
        int count = b.getInt("count");

        SymbolSuggestion[] suggestions = new SymbolSuggestion[count];
        for (int i = 0; i < count; i++) {
            Bundle item = b.getBundle(Integer.toString(i));
            suggestions[i] = SymbolSuggestion.deserialize(item);
        }
        search_adapter_.update(suggestions);
    }
}
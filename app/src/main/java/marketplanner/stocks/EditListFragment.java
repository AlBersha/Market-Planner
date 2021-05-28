package marketplanner.stocks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import marketplanner.stocks.databinding.FragmentEditListBinding;

public class EditListFragment extends Fragment {
    private Settings settings_;
    private FragmentEditListBinding binding;


    public EditListFragment() {
        // Required empty public constructor
    }

    public static EditListFragment newInstance(String param1, String param2) {
        EditListFragment fragment = new EditListFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentEditListBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_edit_list , container, false);


        settings_ = StockApplication.getSettings();

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        final Context context = getContext();

        populate();

        View button = binding.addButton;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditListFragment.class);
                startActivity(intent);
            }
        });
    }

    private void populate() {
        LinearLayoutManager layout = new LinearLayoutManager(getContext());

        RecyclerView container = binding.stockList;
        container.setLayoutManager(layout);

        DividerItemDecoration decoration = new DividerItemDecoration(
                container.getContext(), layout.getOrientation());
        decoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider_line));
        container.addItemDecoration(decoration);

        Map<String, CachedQuote> cached_quotes = settings_.getCachedQuotes();
        List<CachedQuote> quotes = new ArrayList<CachedQuote>();
        String[] symbols = settings_.getStockSymbols();
        for (String symbol : symbols) {
            CachedQuote quote = cached_quotes.get(symbol);
            if (quote == null)
                quote = new CachedQuote(symbol);
            quotes.add(quote);
        }

        StockViewAdapter adapter = new StockViewAdapter(getContext(), settings_, quotes);
        container.setAdapter(adapter);

        addTouchHelper(container, adapter);
    }

    private void addTouchHelper(RecyclerView container, final StockViewAdapter adapter) {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.LEFT;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                adapter.reorder(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT)
                    adapter.removeItem(viewHolder);
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }
        });
        helper.attachToRecyclerView(container);
    }
}
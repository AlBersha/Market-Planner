package marketplanner.stocks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class StockViewAdapter extends RecyclerView.Adapter<StockViewAdapter.ViewHolder>
{
    private LayoutInflater inflater_;
    private Settings settings_;
    private List<CachedQuote> data_;

    StockViewAdapter(Context context, Settings settings, List<CachedQuote> data) {
        inflater_ = LayoutInflater.from(context);
        settings_ = settings;
        data_ = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater_.inflate(R.layout.stock_edit_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(data_.get(position));
    }

    @Override
    public int getItemCount() {
        return data_.size();
    }

    public void reorder(int source_pos, int target_pos) {
        String source_symbol = data_.get(source_pos).symbol;
        String target_symbol = data_.get(target_pos).symbol;

        Collections.swap(data_, source_pos, target_pos);
        notifyItemMoved(source_pos, target_pos);

        settings_.reorderSymbols(source_symbol, target_symbol);
    }

    public void removeItem(RecyclerView.ViewHolder holder) {
        int position = holder.getAdapterPosition();
        removeItem(data_.get(position).symbol, position);

        // notifyItemRemoved does not seem to be enough for swipe removal.
        notifyDataSetChanged();
    }

    private void removeItem(String symbol, int position) {
        data_.remove(position);
        notifyItemRemoved(position);

        settings_.removeSymbol(symbol);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private View root_;

        public ViewHolder(View itemView) {
            super(itemView);
            root_ = itemView;
        }

        @Override
        public void onClick(View v) {
        }

        private void bind(final CachedQuote quote) {
            TextView symbol_name = (TextView)root_.findViewById(R.id.symbol_name);
            symbol_name.setText(quote.symbol);

            if (quote.companyName != null) {
                TextView company_name = (TextView)root_.findViewById(R.id.company_name);
                company_name.setText(quote.companyName);
            }

            View button = root_.findViewById(R.id.remove_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem(quote.symbol, getAdapterPosition());
                }
            });
        }
    }
}

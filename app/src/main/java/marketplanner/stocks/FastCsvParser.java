package marketplanner.stocks;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// JSON deserialization is enormously slow on large data sets, so
// we use this faster parser instead.
public class FastCsvParser
{
    private static final String TAG = "FastCsvParser";

    private String[] lines_;
    private Map<String, Integer> keys_;

    public FastCsvParser(String contents) {
        lines_ = Utilities.fastSplitLines(contents);

        String[] header = parse(lines_[0]);

        keys_ = new HashMap<String, Integer>();
        for (int i = 0; i < header.length; i++)
            keys_.put(header[i], i);
    }

    // This assumes the lines are sorted by the given key.
    public boolean hasItem(String key_name, String item) {
        List<Map<String, String>> objects = new ArrayList<Map<String, String>>();
        if (!keys_.containsKey(key_name))
            return false;

        int key_index = keys_.get(key_name);

        int low = 0;
        int high = numRows() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;

            String[] row = getRow(mid);
            while (row == null && mid <= high)
                row = getRow(mid++);
            if (row == null)
                break;

            int cc = row[key_index].compareToIgnoreCase(item);
            if (cc == 0)
                return true;
            if (cc > 0)
                high = mid - 1;
            else
                low = mid + 1;
        }
        return false;
    }

    // This assumes the lines are sorted by the given key.
    public List<Map<String, String>> findWithPrefix(String key_name, String prefix, int max_count)
    {
        List<Map<String, String>> objects = new ArrayList<Map<String, String>>();
        if (!keys_.containsKey(key_name))
            return objects;

        int key_index = keys_.get(key_name);

        int low = 0;
        int high = numRows() - 1;
        int first_row = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;

            String[] row = getRow(mid);
            while (row == null && mid <= high)
                row = getRow(mid++);
            if (row == null)
                break;

            String value = row[key_index].substring(
                    0,
                    Math.min(prefix.length(), row[key_index].length()));
            int cc = value.compareToIgnoreCase(prefix);
            if (cc == 0) {
                // Keep searching left to find the first occurrence.
                first_row = mid;
                high = mid - 1;
            } else if (cc > 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        if (first_row == -1)
            return objects;

        for (int i = first_row; i < lines_.length && objects.size() < max_count; i++) {
            String[] row = getRow(i);
            if (row == null)
                continue;
            String value = row[key_index].substring(
                    0,
                    Math.min(prefix.length(), row[key_index].length()));
            if (!value.equalsIgnoreCase(prefix))
                break;
            objects.add(lineToObject(row));
        }
        return objects;
    }

    private int numRows() {
        return lines_.length - 1;
    }
    private String[] getRow(int line_number) {
        return parse(lines_[line_number + 1]);
    }

    private String[] parse(String line) {
        String[] parts = line.split(",");
        if (keys_ == null || parts.length == keys_.size())
            return parts;

        // Don't bother handling this yet, since IEXCloud doesn't export
        // quotes in its CSV format.
        Log.e(TAG, "Could not parse CSV line: " + line);
        return null;
    }

    private Map<String, String> lineToObject(String[] components) {
        Map<String, String> map = new HashMap<String, String>();
        for (Map.Entry<String, Integer> item : keys_.entrySet())
            map.put(item.getKey(), components[item.getValue()]);
        return map;
    }
}

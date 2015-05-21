package us.medexpert.medexpert.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.devspark.robototextview.widget.RobotoTextView;

import java.util.ArrayList;
import java.util.List;

import us.medexpert.medexpert.R;
import us.medexpert.medexpert.db.entity.SearchListEntity;

/**
 * Created by user on 21.05.15.
 */
public class SearchListAdapter extends BaseAdapter {

    public static final int ITEM_TYPE_HEADR = 0;
    public static final int ITEM_TYPE_CATEGORY = 1;
    public static final int ITEM_TYPE_DRUG = 2;

    private final int VIEW_TYPE_COUNT = 3;

    private List<SearchListEntity> data = new ArrayList<>();

    private Context context;
    private LayoutInflater inflater;

    public SearchListAdapter(Context context) {
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public SearchListEntity getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = null;
        switch (getItemViewType(data.get(position).getType())) {
            case ITEM_TYPE_HEADR: {
                item = inflater.inflate(R.layout.category_header_item, null);
                RobotoTextView header = (RobotoTextView)item.findViewById(R.id.category_header_text);
                header.setText(data.get(position).getName());
            }
            case ITEM_TYPE_CATEGORY: {

            }
            case ITEM_TYPE_DRUG: {

            }
        }
        return null;
    }

    public List<SearchListEntity> getData() {
        return data;
    }

    public void setData(List<SearchListEntity> data) {
        this.data = data;
    }
}

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
import us.medexpert.medexpert.db.entity.Category;

/**
 * Created by user on 18.05.15.
 */
public class CatalogFragmentListAdapter extends BaseAdapter {

    private List<Category> items;
    private Context context;
    private LayoutInflater inflater;

    private static final int VIEW_TYPES_COUNT = 2;

    public static final int CATALOG_CATEGORY_TYPE_HEADER = 0;
    public static final int CATALOG_CATEGORY_TYPE_ITEM = 1;

    public CatalogFragmentListAdapter(Context context, List data) {
        this.context = context;
        this.items = data;
        inflater = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Object getItem(int position) {
        return items == null ? null : items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPES_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(items.get(position).getType() == CATALOG_CATEGORY_TYPE_HEADER) {
            View header = inflater.inflate(R.layout.category_header_item, null);
            ((RobotoTextView)header.findViewById(R.id.category_header_text)).setText(items.get(position).getCatName());
            return header;
        } else {
            View item = inflater.inflate(R.layout.category_main_item, null);
            RobotoTextView letter = (RobotoTextView)item.findViewById(R.id.category_letter_text);
            RobotoTextView mane = (RobotoTextView)item.findViewById(R.id.category_main_text);
            letter.setText(items.get(position).getCatName().substring(0, 1));
            mane.setText(items.get(position).getCatName());
            item.setTag(items.get(position).getId());
            return item;
        }
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public List<Category> makeResultList(List<Category> topCategories, List<Category> allCategories) {
        List<Category> result = new ArrayList<>();
        result.add(new Category(-1, context.getString(R.string.catalog_popular_categories_header_txt), CATALOG_CATEGORY_TYPE_HEADER));
        for(Category c : topCategories) {
            result.add(c);
        }
        result.add(new Category(-1, context.getString(R.string.catalog_all_categories_header_txt),
                CATALOG_CATEGORY_TYPE_HEADER));
        for(Category c : allCategories) {
            result.add(c);
        }

        return result;
    }
}

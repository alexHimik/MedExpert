package us.medexpert.medexpert.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.devspark.robototextview.widget.RobotoTextView;

import java.util.ArrayList;
import java.util.List;

import us.medexpert.medexpert.R;
import us.medexpert.medexpert.db.entity.Product;

/**
 * Created by user on 23.05.15.
 */
public class LastViewedDrugsAdapter extends BaseAdapter {

    private Fragment context;
    private List<Product> items = new ArrayList<>();

    public LastViewedDrugsAdapter(Fragment context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Product getItem(int position) {
        return items == null ? null : items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Product pr = items.get(position);
        if (v == null) {
            v = ((LayoutInflater) context.getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.home_item_favor, null);
        }

        String st = pr.getName();
        int i1 = st.indexOf("(");
        if (i1>0) {
            st = st.substring(0,i1).trim();
        }

        ((RobotoTextView) v.findViewById(R.id.name)).setText(st);

        ((RobotoTextView) v.findViewById(R.id.gener)).setText(pr.getNameCat());
        ((RobotoTextView) v.findViewById(R.id.price)).setText(pr.getPrice());
        ((ImageView) v.findViewById(R.id.iv2)).setImageDrawable(context.getResources().
                getDrawable(R.drawable.med_ic_pink_heart_checked));
        return v;
    }

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }
}
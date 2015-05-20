package us.medexpert.medexpert.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.devspark.robototextview.widget.RobotoTextView;

/**
 * Created by user on 19.05.15.
 */
public class CategoryListAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    private class ViewHolder {
        public RobotoTextView drugName;
        public ImageView drugImage;
        public RobotoTextView drugPrice;
        public RobotoTextView drugDescription;
    }
}

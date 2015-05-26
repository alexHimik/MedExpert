package us.medexpert.medexpert.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.devspark.robototextview.widget.RobotoTextView;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
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
        switch (getItemViewType(position)) {
            case ITEM_TYPE_HEADR: {
                item = inflater.inflate(R.layout.category_header_item, null);
                RobotoTextView header = (RobotoTextView)item.findViewById(R.id.category_header_text);
                header.setText(data.get(position).getName());
                return item;
            }
            case ITEM_TYPE_CATEGORY: {
                item = inflater.inflate(R.layout.search_category_item, null);
                RobotoTextView name = (RobotoTextView)item.findViewById(R.id.search_category_main_text);
                RobotoTextView amount = (RobotoTextView)item.findViewById(R.id._search_category_amount_text);
                name.setText(data.get(position).getName());
                if(data.get(position).getAmount() != null &&
                        data.get(position).getAmount().length() > 0) {
                    amount.setText(data.get(position).getAmount() + " pills");
                }
                return item;
            }
            case ITEM_TYPE_DRUG: {
                item = inflater.inflate(R.layout.category_drug_item_layout, null);
                RobotoTextView drugName = (RobotoTextView)item.findViewById(R.id.drug_name);
                ImageView drugImage = (ImageView)item.findViewById(R.id.drug_image);
                ImageView likeImage = (ImageView)item.findViewById(R.id.category_like_img);
                RobotoTextView drugPrice = (RobotoTextView)item.findViewById(R.id.price);
                RobotoTextView drugDescription = (RobotoTextView)item.findViewById(R.id.drug_description);
                RobotoTextView genericText = (RobotoTextView)item.findViewById(R.id.generic_text);

                drugName.setText(data.get(position).getName());
                BitmapPool pool = Glide.get(context).getBitmapPool();

                Glide.with(context).load(context.getResources().getString(R.string.app_site_base_url)
                        + data.get(position).getImage()).bitmapTransform(new CropCircleTransformation(pool)).into(drugImage);
                if(data.get(position).isFavorite()) {
                    Glide.with(context).load(R.drawable.med_ic_pink_heart_checked).into(likeImage);
                }
                drugPrice.setText(data.get(position).getPrice());
                drugDescription.setText(data.get(position).getDescription());
                genericText.setText(data.get(position).getGeneric());
                return item;
            }
            default: {
                return item;
            }
        }
    }

    public List<SearchListEntity> getData() {
        return data;
    }

    public void setData(List<SearchListEntity> data) {
        this.data = data;
    }
}

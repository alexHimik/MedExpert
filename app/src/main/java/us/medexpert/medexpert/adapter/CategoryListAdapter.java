package us.medexpert.medexpert.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.devspark.robototextview.widget.RobotoTextView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import us.medexpert.medexpert.R;
import us.medexpert.medexpert.db.entity.Product;
import us.medexpert.medexpert.db.tables.ProductHelper;

/**
 * Created by user on 19.05.15.
 */
public class CategoryListAdapter extends CursorAdapter {

    private Fragment fragment;

    public CategoryListAdapter(Fragment context, Cursor c, boolean autoRequery) {
        super(context.getActivity(), c, autoRequery);
        fragment = context;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder;
        if(view.getTag() == null) {
            holder = new ViewHolder();
            holder.drugName = (RobotoTextView)view.findViewById(R.id.drug_name);
            holder.drugImage = (ImageView)view.findViewById(R.id.drug_image);
            holder.likeImage = (ImageView)view.findViewById(R.id.category_like_img);
            holder.drugPrice = (RobotoTextView)view.findViewById(R.id.price);
            holder.drugDescription = (RobotoTextView)view.findViewById(R.id.drug_description);
            holder.genericText = (RobotoTextView)view.findViewById(R.id.generic_text);
            view.setTag(holder);
        }
        holder = (ViewHolder)view.getTag();
        String name = cursor.getString(cursor.getColumnIndex(ProductHelper.TITLE_COLUMN));

        if(name.indexOf("(") != -1) {
            String justName = name.substring(0, name.indexOf("(") - 1);
            holder.drugName.setText(justName);
        } else {
            holder.drugName.setText(name);
        }
        BitmapPool pool = Glide.get(context).getBitmapPool();

        Glide.with(fragment).load(
                context.getResources().getString(R.string.app_site_base_url) +
                        cursor.getString(cursor.getColumnIndex(ProductHelper.IMAGE_COLUMN))).
                bitmapTransform(new CropCircleTransformation(pool)).into(holder.drugImage);
        holder.drugPrice.setText(cursor.getString(cursor.getColumnIndex(
                ProductHelper.DRUG_PRICE_COLUMN)));
        int like = cursor.getInt(cursor.getColumnIndex(ProductHelper.LIKED_COLUMN));
        if(like > 0) {
            Glide.with(fragment).load(R.drawable.med_ic_pink_heart_checked).into(holder.likeImage);
        } else {
            Glide.with(fragment).load(R.drawable.med_ic_grey_heart_unchecked).into(holder.likeImage);
        }

        holder.drugDescription.setText(cursor.getString(
                cursor.getColumnIndex(ProductHelper.DESCRIPTION_COLUMN)));

        if(name.indexOf("(") != -1) {
            holder.genericText.setText(name.substring(name.indexOf("(") + 1, name.indexOf(")")));
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(
                R.layout.category_drug_item_layout, parent, false);
    }

    @Override
    public Object getItem(int position) {
        if(position >= 0) {
            getCursor().moveToPosition(position);
            return getCursor().getInt(getCursor().getColumnIndex(
                    ProductHelper.ID_COLUMN));
        } else {
            return position;
        }
    }

    private class ViewHolder {
        public RobotoTextView drugName;
        public ImageView drugImage;
        public ImageView likeImage;
        public RobotoTextView drugPrice;
        public RobotoTextView drugDescription;
        public RobotoTextView genericText;
    }
}

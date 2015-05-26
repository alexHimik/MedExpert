package us.medexpert.medexpert.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.devspark.robototextview.widget.RobotoTextView;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import us.medexpert.medexpert.R;
import us.medexpert.medexpert.db.entity.Product;

public class FavorAdapter extends BaseAdapter {
    private Fragment context;
    private List<Product> items;
    ImageView[] star = new ImageView [5];

    public FavorAdapter(Fragment context, List<Product> items) {
        this.context = context;
        this.items = items;
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
         if (i1 > 0) {
             st = st.substring(0,i1).trim();
         }

         ((RobotoTextView) v.findViewById(R.id.name)).setText(st);

         ((RobotoTextView) v.findViewById(R.id.gener)).setText(pr.getNameCat());
         ((RobotoTextView) v.findViewById(R.id.price)).setText(pr.getPrice());
         ((ImageView) v.findViewById(R.id.iv2)).setImageDrawable(context.getResources().
                 getDrawable(R.drawable.med_ic_pink_card_heart));
         ImageView iv = (ImageView) v.findViewById(R.id.iv1);
         BitmapPool pool = Glide.get(context.getActivity()).getBitmapPool();
         Glide.with(context).load(context.getResources().getString(R.string.app_site_base_url) + pr.getImg())
                 .bitmapTransform(new CropCircleTransformation(pool)).into(iv);
         setRating(v, star,Math.round(pr.getDrugRate()));
         return v;
    }

    private void setRating(View v, ImageView[] star, int rat){
        star[0] = (ImageView) v.findViewById(R.id.star1);
        star[1] = (ImageView) v.findViewById(R.id.star2);
        star[2] = (ImageView) v.findViewById(R.id.star3);
        star[3] = (ImageView) v.findViewById(R.id.star4);
        star[4] = (ImageView) v.findViewById(R.id.star5);
        for (int s=0; s<5;s++){
            if (s<rat) star[s].setImageDrawable(context.getResources().getDrawable(R.drawable.med_ic_yellow_star));
            else star[s].setImageDrawable(context.getResources().getDrawable(R.drawable.med_ic_grey_star));
        }
    }

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }
}

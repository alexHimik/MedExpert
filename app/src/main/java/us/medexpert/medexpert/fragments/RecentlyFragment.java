package us.medexpert.medexpert.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.devspark.robototextview.widget.RobotoTextView;

import java.util.List;

import us.medexpert.medexpert.R;
import us.medexpert.medexpert.activity.MainActivity;
import us.medexpert.medexpert.adapter.LastViewedDrugsAdapter;
import us.medexpert.medexpert.db.entity.Product;
import us.medexpert.medexpert.loader.LastViwedDrugsLoader;

public class RecentlyFragment extends BaseFragment
        implements LoaderManager.LoaderCallbacks<List<Product>> {
    public static final String TAG = "RecentlyFragment";
    public static final int RECENTLY_ID = 6;

    private View parent;
    private LinearLayout ll;

    private LastViewedDrugsAdapter listAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View customBar = super.getActionBarCustomView(inflater);
        ((MainActivity) getActivity()).getSupportActionBar().setCustomView(customBar);

        parent = inflater.inflate(R.layout.recently, container, false);
        ll = (LinearLayout) parent.findViewById(R.id.ll);
        ll.setGravity(Gravity.CENTER_VERTICAL);

        getLoaderManager().initLoader(LastViwedDrugsLoader.ID, null, this);


        return parent;
    }

    @Override
    public void initActionBarItems() {
        sortBarItem.setVisibility(View.VISIBLE);
        rightBarItem.setVisibility(View.VISIBLE);
        sortBarItem.setOnClickListener(barClickListener);
        rightBarItem.setOnClickListener(barClickListener);
        leftItemTouch.setOnClickListener(barClickListener);
        leftbarItem.setBackgroundResource(R.drawable.med_ic_white_hamburger);
        ((RobotoTextView) centerBatItem).setText(getString(R.string.viewed_string));
    }


    private View.OnClickListener barClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MainActivity) getActivity()).onClick(v);
        }
    };


    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return RECENTLY_ID;
    }

    @Override
    public Loader<List<Product>> onCreateLoader(int id, Bundle args) {
        Loader loader = null;
        if(id == LastViwedDrugsLoader.ID) {
            loader = new LastViwedDrugsLoader(getActivity());
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<Product>> loader, List<Product> data) {
        if(data != null && data.size() > 0) {
            ListView listView = new ListView(getActivity());
            listAdapter = new LastViewedDrugsAdapter(this);
            listView.setAdapter(listAdapter);
            listAdapter.getItems().addAll(data);
            //TODO add list to the root layout
        } else {
            View v = ((LayoutInflater) getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.recently_item_img, null);
            ll.addView(v);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Product>> loader) {

    }
}

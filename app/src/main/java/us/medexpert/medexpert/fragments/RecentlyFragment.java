package us.medexpert.medexpert.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.devspark.robototextview.widget.RobotoTextView;

import java.util.Collections;
import java.util.List;

import us.medexpert.medexpert.R;
import us.medexpert.medexpert.activity.MainActivity;
import us.medexpert.medexpert.adapter.LastViewedDrugsAdapter;
import us.medexpert.medexpert.db.entity.Product;
import us.medexpert.medexpert.dialog.SortDialog;
import us.medexpert.medexpert.loader.LastViwedDrugsLoader;
import us.medexpert.medexpert.tools.FragmentFactory;
import us.medexpert.medexpert.tools.comparator.AscDrugDateComparator;
import us.medexpert.medexpert.tools.comparator.AscDrugNameNameComparator;
import us.medexpert.medexpert.tools.comparator.DescDrugDateComparator;
import us.medexpert.medexpert.tools.comparator.DescDrugNameComparator;

public class RecentlyFragment extends BaseFragment
        implements ListView.OnItemClickListener, LoaderManager.LoaderCallbacks<List<Product>> {
    public static final String TAG = "RecentlyFragment";
    public static final int RECENTLY_ID = 6;

    private View parent;
    private LinearLayout ll;
    private LayoutInflater inflat;
    private ViewGroup contain;
    private LastViewedDrugsAdapter listAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View customBar = super.getActionBarCustomView(inflater);
        ((MainActivity) getActivity()).getSupportActionBar().setCustomView(customBar);
        inflat = inflater;
        contain = container;
        parent = inflater.inflate(R.layout.recently, container, false);
        ll = (LinearLayout) parent.findViewById(R.id.ll);

        getLoaderManager().initLoader(LastViwedDrugsLoader.ID, null, this);
        return parent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(sortReceiver,
                new IntentFilter(SortDialog.SORT_ITEMS_EVENT));
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(sortReceiver);
        super.onDestroy();
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
            ListView lv = (ListView) inflat.inflate(R.layout.favor_list, contain, false);
            listAdapter = new LastViewedDrugsAdapter(this);
            listAdapter.getItems().addAll(data);
            lv.setAdapter(listAdapter);
            lv.setOnItemClickListener(this);
            ll.addView(lv);
        } else {
            View v = ((LayoutInflater) getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.recently_item_img, null);
            ll.setGravity(Gravity.CENTER_VERTICAL);
            ll.addView(v);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Product>> loader) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Product pr = listAdapter.getItem(position);
        Bundle data = new Bundle();
        data.putString(PillInfoFragment.PRODUCT_NAME_KEY, pr.getName());
        data.putInt(PillInfoFragment.PRODUCT_ID_KEY, pr.getId());
        data.putInt(PillInfoFragment.CATEGORY_ID_KEY, pr.getId_category());
        ((MainActivity)getActivity()).handleFragmentSwitching(FragmentFactory.ID_PILLINFO, data);
    }

    private BroadcastReceiver sortReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(SortDialog.SORT_ITEMS_EVENT.equals(intent.getAction())) {
                switch (intent.getIntExtra(SortDialog.SORT_TYPE_KEY, -1)) {
                    case SortDialog.SORT_BY_NAME_ASC: {
                        Collections.sort(listAdapter.getItems(), new AscDrugNameNameComparator());
                        listAdapter.notifyDataSetChanged();
                        break;
                    }
                    case SortDialog.SORT_BY_NAME_DESC: {
                        Collections.sort(listAdapter.getItems(), new DescDrugNameComparator());
                        listAdapter.notifyDataSetChanged();
                        break;
                    }
                    case SortDialog.SORT_BY_POP_ASC: {
                        Collections.sort(listAdapter.getItems(), new AscDrugDateComparator());
                        listAdapter.notifyDataSetChanged();
                        break;
                    }
                    case SortDialog.SORT_BY_POP_DESC: {
                        Collections.sort(listAdapter.getItems(), new DescDrugDateComparator());
                        listAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }
    };
}

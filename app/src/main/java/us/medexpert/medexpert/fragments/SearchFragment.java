package us.medexpert.medexpert.fragments;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import us.medexpert.medexpert.R;
import us.medexpert.medexpert.activity.MainActivity;
import us.medexpert.medexpert.adapter.SearchListAdapter;
import us.medexpert.medexpert.db.entity.SearchListEntity;
import us.medexpert.medexpert.db.tables.CategoryTableHelper;
import us.medexpert.medexpert.db.tables.ProductHelper;
import us.medexpert.medexpert.loader.DefaultSearchDataLoader;
import us.medexpert.medexpert.tools.FragmentFactory;

public class SearchFragment extends BaseFragment
        implements LoaderManager.LoaderCallbacks<List<SearchListEntity>> {

    public static final String TAG = "SearchFragment";

    private ListView contentList;
    private SearchListAdapter listAdapter;
    private Runnable lastSearchQuerry;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentList = (ListView)inflater.inflate(R.layout.search_fragment, container, false);
        getActionBarCustomView(inflater);
        listAdapter = new SearchListAdapter(getActivity());
        contentList.setAdapter(listAdapter);
        contentList.setOnItemClickListener(onItemClickListener);
        getLoaderManager().initLoader(DefaultSearchDataLoader.ID, null, this);
        return contentList;
    }

    @Override
    public View getActionBarCustomView(LayoutInflater inflater) {
        View customBar = inflater.inflate(R.layout.search_bar_layout, null);
        leftbarItem = customBar.findViewById(R.id.search_lens);
        centerBatItem = customBar.findViewById(R.id.search_input);
        rightBarItem = customBar.findViewById(R.id.search_clear);

        initActionBarItems();
        ((ActionBarActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(getResources().getColor(R.color.med_blue)));
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int sideMargins = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                getResources().getDimension(R.dimen.padding_4dp),
                getResources().getDisplayMetrics());
        params.setMargins(sideMargins, 0, sideMargins, 0);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setCustomView(customBar, params);
        return customBar;
    }

    @Override
    public void initActionBarItems() {
        ((EditText)centerBatItem).addTextChangedListener(searchInputListener);
        rightBarItem.setOnClickListener(backListener);
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FragmentFactory.ID_SEARCH;
    }

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.search_clear) {
                ((EditText)centerBatItem).setText("");
            }
        }
    };

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SearchListEntity entity = listAdapter.getItem(position);
            Bundle data = new Bundle();
            switch (entity.getType()) {
                case SearchListAdapter.ITEM_TYPE_CATEGORY: {
                    data.putString(CategoryDrugListFragment.CATEGORY_NAME_KEY, entity.getName());
                    data.putInt(CategoryDrugListFragment.CATEGORY_ID_KEY, entity.getId());
                    ((MainActivity)getActivity()).handleFragmentSwitching(
                            FragmentFactory.ID_CATEGORY ,data);
                    break;
                }
                case SearchListAdapter.ITEM_TYPE_DRUG: {
                    data.putString(PillInfoFragment.PRODUCT_NAME_KEY, entity.getName());
                    data.putInt(PillInfoFragment.PRODUCT_ID_KEY, entity.getId());
                    data.putInt(PillInfoFragment.CATEGORY_ID_KEY, entity.getCategoryId());
                    ((MainActivity)getActivity()).handleFragmentSwitching(
                            FragmentFactory.ID_PILLINFO, data);
                    break;
                }
            }
        }
    };

    @Override
    public Loader<List<SearchListEntity>> onCreateLoader(int id, Bundle args) {
        if(id == DefaultSearchDataLoader.ID) {
            return new DefaultSearchDataLoader(getActivity());
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<SearchListEntity>> loader, List<SearchListEntity> data) {
        Message msg = Message.obtain();
        msg.obj = data;
        searchHandler.sendMessage(msg);
    }

    @Override
    public void onLoaderReset(Loader<List<SearchListEntity>> loader) {
        //do nothing here
    }

    private TextWatcher searchInputListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //do nothing here
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //do nothing here
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(lastSearchQuerry != null) {
                searchHandler.removeCallbacks(lastSearchQuerry);
            }
            lastSearchQuerry = new SearchQuery(s.toString(), searchHandler);
            searchHandler.post(lastSearchQuerry);
        }
    };

    private Handler searchHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            listAdapter.getData().clear();
            listAdapter.getData().addAll((List<SearchListEntity>)msg.obj);
            listAdapter.notifyDataSetChanged();
        }
    };

    private class SearchQuery implements Runnable {

        private String query;
        private Handler handler;

        public SearchQuery(String query, Handler handler) {
            this.query = query;
            this.handler = handler;
        }

        @Override
        public void run() {
            CategoryTableHelper categoryTableHelper = new CategoryTableHelper();
            ProductHelper categoryDrugsTableHelper = ProductHelper.getInstance(getActivity());
            List<SearchListEntity> categories = categoryTableHelper.getCategoriesForSearch(
                    getActivity(), query);
            List<SearchListEntity> drugs = categoryDrugsTableHelper.getDrugsForSearch(query);
            List<SearchListEntity> result = new ArrayList<>();
            SearchListEntity catHeader = new SearchListEntity();
            catHeader.setId(-1);
            catHeader.setName("Category");
            result.add(catHeader);
            result.addAll(categories);
            SearchListEntity pillsHeader = new SearchListEntity();
            pillsHeader.setId(-1);
            pillsHeader.setName("Pills");
            result.add(pillsHeader);
            result.addAll(drugs);

            Message msg = Message.obtain();
            msg.obj = result;
            handler.sendMessage(msg);
        }
    }
}

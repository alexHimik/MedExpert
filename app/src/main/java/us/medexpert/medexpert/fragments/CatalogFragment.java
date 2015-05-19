package us.medexpert.medexpert.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.devspark.robototextview.widget.RobotoTextView;

import java.util.ArrayList;
import java.util.List;

import us.medexpert.medexpert.R;
import us.medexpert.medexpert.activity.MainActivity;
import us.medexpert.medexpert.adapter.CatalogFragmentListAdapter;
import us.medexpert.medexpert.db.entity.Category;
import us.medexpert.medexpert.tools.FragmentFactory;

/**
 * Created by user on 18.05.15.
 */
public class CatalogFragment extends BaseFragment {

    public static final String TAG = "CatalogFragment";

    private ListView categoriesList;
    private CatalogFragmentListAdapter adapter;
    private List<Category> elements = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View customBar = getActionBarCustomView(inflater);
        ((MainActivity)getActivity()).getSupportActionBar().setCustomView(customBar);
        categoriesList = (ListView)inflater.inflate(R.layout.catalog_fragment, container, false);
        categoriesList.setOnItemClickListener(onItemClickListener);
        adapter = new CatalogFragmentListAdapter(getActivity(), elements);
        categoriesList.setAdapter(adapter);
        initMockData();
        return categoriesList;
    }

    @Override
    public void initActionBarItems() {
        leftItemTouch.setOnClickListener(clickListener);
        rightBarItem.setOnClickListener(clickListener);
        ((RobotoTextView)centerBatItem).setText(getString(R.string.catalog_string));
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FragmentFactory.ID_CATALOG;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.right_drawer_item) {
                //TODO handle issue with search widget or screen
            } else {
                ((MainActivity)getActivity()).onClick(v);
            }
        }
    };

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };

    private void initMockData() {
        List<Category> mostPopular = new ArrayList<>();
        List<Category> all = new ArrayList<>();
        mostPopular.add(new Category(1, "Most Popular One", CatalogFragmentListAdapter.CATALOG_CATEGORY_TYPE_ITEM));
        mostPopular.add(new Category(2, "Most Popular Two", CatalogFragmentListAdapter.CATALOG_CATEGORY_TYPE_ITEM));
        mostPopular.add(new Category(3, "Most Popular Three", CatalogFragmentListAdapter.CATALOG_CATEGORY_TYPE_ITEM));
        mostPopular.add(new Category(4, "Most Popular four", CatalogFragmentListAdapter.CATALOG_CATEGORY_TYPE_ITEM));

        all.add(new Category(5, "Probenzol Diamide", CatalogFragmentListAdapter.CATALOG_CATEGORY_TYPE_ITEM));
        all.add(new Category(6, "Probenzol Diamide-3", CatalogFragmentListAdapter.CATALOG_CATEGORY_TYPE_ITEM));
        all.add(new Category(7, "Lidocaine hydrocloride", CatalogFragmentListAdapter.CATALOG_CATEGORY_TYPE_ITEM));
        all.add(new Category(8, "Gliceryne", CatalogFragmentListAdapter.CATALOG_CATEGORY_TYPE_ITEM));
        all.add(new Category(9, "Carbonated water", CatalogFragmentListAdapter.CATALOG_CATEGORY_TYPE_ITEM));
        all.add(new Category(10, "Probenzol Diamid-1", CatalogFragmentListAdapter.CATALOG_CATEGORY_TYPE_ITEM));
        all.add(new Category(11, "Probenzol Diamid-4", CatalogFragmentListAdapter.CATALOG_CATEGORY_TYPE_ITEM));
        all.add(new Category(12, "Probenzol Diamide-2", CatalogFragmentListAdapter.CATALOG_CATEGORY_TYPE_ITEM));

        elements.addAll(adapter.makeResultList(mostPopular, all));
        adapter.notifyDataSetChanged();
    }
}

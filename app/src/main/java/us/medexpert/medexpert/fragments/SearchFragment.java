package us.medexpert.medexpert.fragments;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import us.medexpert.medexpert.R;
import us.medexpert.medexpert.tools.FragmentFactory;

public class SearchFragment extends BaseFragment {

    public static final String TAG = "SearchFragment";

    private ListView contentList;
    private BaseAdapter listAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentList = (ListView)inflater.inflate(R.layout.search_fragment, container, false);
        getActionBarCustomView(inflater);
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
                new ColorDrawable(getResources().getColor(R.color.med_white)));
        ((ActionBarActivity)getActivity()).getSupportActionBar().setCustomView(customBar);
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
            if(v.getId() == R.id.right_drawer_item) {
                ((EditText)centerBatItem).setText("");
            }
        }
    };

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

        }
    };
}

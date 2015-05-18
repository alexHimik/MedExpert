package us.medexpert.medexpert.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devspark.robototextview.widget.RobotoTextView;

import us.medexpert.medexpert.R;
import us.medexpert.medexpert.activity.MainActivity;
import us.medexpert.medexpert.tools.FragmentFactory;

/**
 * Created by user on 15.05.15.
 */
public class AboutFragment extends BaseFragment {

    public static final String TAG = "AboutFragment";

    private RobotoTextView email;
    private RobotoTextView site;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View customBar = getActionBarCustomView(inflater);
        ((MainActivity)getActivity()).getSupportActionBar().setCustomView(customBar);
        View parent = inflater.inflate(R.layout.about_fragment_layout, container, false);
        email = (RobotoTextView)parent.findViewById(R.id.about_email);
        site = (RobotoTextView)parent.findViewById(R.id.about_site);

        email.setOnClickListener(clickListener);
        site.setOnClickListener(clickListener);
        return parent;
    }

    @Override
    public void initActionBarItems() {
        leftItemTouch.setOnClickListener(clickListener);
        rightBarItem.setOnClickListener(clickListener);
        ((RobotoTextView)centerBatItem).setText(getString(R.string.about_string));
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FragmentFactory.ID_ABOUT;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.about_email) {
                openEmailClient();
            } else if(v.getId() == R.id.about_site) {
                openBrowser();
            } else if(v.getId() == R.id.left_drawer_item_touch) {
                ((MainActivity)getActivity()).onClick(v);
            } else if(v.getId() == R.id.right_drawer_item) {
                ((MainActivity)getActivity()).onClick(v);
            }
        }
    };

    private void openEmailClient() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { getString(R.string.about_email) });
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(intent, ""));
    }

    private void openBrowser() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                "http://" + getString(R.string.about_site)));
        startActivity(intent);
    }
}

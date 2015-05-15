package us.medexpert.medexpert.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devspark.robototextview.widget.RobotoTextView;

import us.medexpert.medexpert.R;

/**
 * Created by user on 15.05.15.
 */
public class AboutFragment extends BaseFragment {

    public static final String TAG = "AboutFragment";
    public static final int FRAGMENT_ID = 111;

    private RobotoTextView email;
    private RobotoTextView site;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.about_fragment_layout, container, false);
        email = (RobotoTextView)parent.findViewById(R.id.about_email);
        site = (RobotoTextView)parent.findViewById(R.id.about_site);

        email.setOnClickListener(clickListener);
        site.setOnClickListener(clickListener);

        return parent;
    }

    @Override
    public View getActionBarCustomView(LayoutInflater inflater) {
        return super.getActionBarCustomView(inflater);
    }

    @Override
    public void initActionBarItems() {

    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FRAGMENT_ID;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.about_email) {
                openEmailClient();
            } else if(v.getId() == R.id.about_site) {

            }
        }
    };

    private void openEmailClient() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { getString(R.string.about_email) });
        intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        intent.putExtra(Intent.EXTRA_TEXT, "mail body");
        startActivity(Intent.createChooser(intent, ""));
    }
}

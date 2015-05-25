package us.medexpert.medexpert.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.devspark.robototextview.widget.RobotoTextView;

import us.medexpert.medexpert.R;
import us.medexpert.medexpert.activity.MainActivity;
import us.medexpert.medexpert.tools.FragmentFactory;

/**
 * Created by user on 20.05.15.
 */
public class SellersFragment extends BaseFragment {

    public static final String TAG = "SellersFragment";

    private View parent;
    private LinearLayout ll;
    private MyWebViewClient webViewClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View customBar = super.getActionBarCustomView(inflater);
        ((MainActivity) getActivity()).getSupportActionBar().setCustomView(customBar);
        parent = inflater.inflate(R.layout.sellers, container, false);
        ll = (LinearLayout) parent.findViewById(R.id.ll);
        ll.setGravity(Gravity.CENTER_VERTICAL);
        Bundle args = getArguments();
        if (args != null) {
            String url = args.getString("url");
            if (url != null) {
                webViewClient = new MyWebViewClient();
                WebView webView = (WebView) ll.findViewById(R.id.webView);
                webView.setWebViewClient(webViewClient);
                webView.loadUrl(url);
            }
        }
        return parent;
    }

    @Override
    public void initActionBarItems() {
        rightBarItem.setVisibility(View.VISIBLE);
        rightBarItem.setOnClickListener(barClickListener);
        leftItemTouch.setOnClickListener(barClickListener);
        leftbarItem.setBackgroundResource(R.drawable.med_ic_white_back);
        ((RobotoTextView) centerBatItem).setText(getString(R.string.sellers_string));
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
        return FragmentFactory.ID_SELLERS;
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}

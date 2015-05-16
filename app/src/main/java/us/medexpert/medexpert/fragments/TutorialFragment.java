package us.medexpert.medexpert.fragments;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.devspark.robototextview.widget.RobotoButton;
import com.devspark.robototextview.widget.RobotoTextView;

import us.medexpert.medexpert.R;
import us.medexpert.medexpert.activity.MainActivity;
import us.medexpert.medexpert.tools.FragmentFactory;
import us.medexpert.medexpert.tools.PreferenceTools;

/**
 * Created by user on 16.05.15.
 */
public class TutorialFragment extends BaseFragment {

    public static final String TAG ="TutorialFragmentOne";

    private RobotoButton nextButton;
    private RobotoTextView header;
    private RobotoTextView content;
    private ImageView image;
    private View parent;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View customBar = getActionBarCustomView(inflater);
        ((MainActivity)getActivity()).getSupportActionBar().setCustomView(customBar);
        parent = inflater.inflate(R.layout.tutorial_fragment_layout_one, container, false);
        initViews(parent);
        return parent;
    }

    @Override
    public void initActionBarItems() {
        leftItemTouch.setVisibility(View.INVISIBLE);
        centerBatItem.setVisibility(View.INVISIBLE);
        rightBarItem.setVisibility(View.INVISIBLE);
    }

    @Override
    public View getActionBarCustomView(LayoutInflater inflater) {
        RelativeLayout barLayout = (RelativeLayout)inflater.inflate(R.layout.action_bar_header,
                null);
        leftbarItem = barLayout.findViewById(R.id.left_drawer_item);
        rightBarItem = barLayout.findViewById(R.id.right_drawer_item);
        centerBatItem = barLayout.findViewById(R.id.action_bar_title);
        leftItemTouch = barLayout.findViewById(R.id.left_drawer_item_touch);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        initActionBarItems();
        return barLayout;
    }

    private void initViews(View parent) {
        nextButton = (RobotoButton)parent.findViewById(R.id.tutorial_btn);
        image = (ImageView)parent.findViewById(R.id.tutorial_img);
        image.setBackgroundDrawable(getResources().getDrawable(R.drawable.med_tutorial_1));
        header = (RobotoTextView)parent.findViewById(R.id.tutorial_header);
        content = (RobotoTextView)parent.findViewById(R.id.tutorial_text);

        nextButton.setOnClickListener(clickListener);
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FragmentFactory.ID_TUTORIAL;
    }

    private void handleButtonPress() {
        if(PreferenceTools.getTutorialShowedState(getActivity())) {
            ((MainActivity)getActivity()).getSupportFragmentManager().popBackStack();
            ((MainActivity)getActivity()).handleFragmentSwitching(FragmentFactory.ID_HOME, null);
        } else {
            parent.setBackgroundColor(getResources().getColor(R.color.med_blue));
            image.setBackgroundDrawable(getResources().getDrawable(R.drawable.med_tutorial_2));
            header.setText(getString(R.string.tutorial_2_header));
            content.setText(getString(R.string.tutorial_2_text));
            nextButton.setText(getString(R.string.tutorial_launch_app_text));
            nextButton.setTextColor(getResources().getColor(R.color.med_blue));
            PreferenceTools.setTutorialShowedValue(getActivity(), true);
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.tutorial_btn) {
                handleButtonPress();
            }
        }
    };
}

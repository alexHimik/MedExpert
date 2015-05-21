package us.medexpert.medexpert.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;


import us.medexpert.medexpert.R;
//import us.medexpert.medexpert.fragments.BackStackDataDescriber;
import us.medexpert.medexpert.fragments.BaseFragment;
import us.medexpert.medexpert.fragments.SearchFragment;
import us.medexpert.medexpert.tools.FragmentFactory;
import us.medexpert.medexpert.tools.PreferenceTools;
import us.medexpert.medexpert.tools.ViewTools;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private FrameLayout contentLayout;
    private RelativeLayout drawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getWindow().setBackgroundDrawable(new ColorDrawable(
                getResources().getColor(R.color.med_white)));
        initViews();
//        handleFragmentSwitching(FragmentFactory.ID_PILLINFO, null);
        resolveFirstStart();
    }

    private void resolveFirstStart() {
        if(PreferenceTools.getTutorialShowedState(this)) {
            handleFragmentSwitching(FragmentFactory.ID_HOME, null);
        } else {
            handleFragmentSwitching(FragmentFactory.ID_TUTORIAL, null);
        }
    }

    private void initViews() {
        moveDrawerToTop();
        initActionBar();
        initDrawer();

    }

    private void moveDrawerToTop() {
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        drawerLayout = (DrawerLayout)inflater.inflate(R.layout.drawer_decor, null);
        contentLayout = (FrameLayout)drawerLayout.findViewById(R.id.content_frame);

        ViewGroup decor = (ViewGroup) getWindow().getDecorView();
        View child = decor.getChildAt(0);
        decor.removeView(child);

        contentLayout.addView(child, 0);
        decor.addView(drawerLayout);

        contentLayout = (FrameLayout)drawerLayout.findViewById(R.id.content_frame);
        drawerList = (RelativeLayout)drawerLayout.findViewById(R.id.left_drawer);
        drawerList.setPadding(0, ViewTools.getStatusBarHeight(this), 0, 0);
    }

    private void initActionBar() {
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name,
                R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                View customBar = getSupportActionBar().getCustomView();
                if(customBar != null) {
                    customBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                View customBar = getSupportActionBar().getCustomView();
                if(customBar != null) {
                    customBar.setVisibility(View.INVISIBLE);
                }
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    private void initDrawer() {
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(Gravity.START|Gravity.LEFT)){
            drawerLayout.closeDrawers();
            return;
        }
        if(getSupportFragmentManager().getBackStackEntryCount() == 1) {
            getSupportFragmentManager().popBackStack();
            finish();
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_drawer_item_touch:
                togleLeftDrawer();
                break;
            case R.id.right_drawer_item:
                handleFragmentSwitching(SearchFragment.FRAGMENT_ID, null);
                break;
        }
//        if(v.getId() == R.id.left_drawer_item_touch) {
//            togleLeftDrawer();
//        } else if(v.getId() == R.id.right_drawer_item) {
//            handleFragmentSwitching(SearchFragment.FRAGMENT_ID, null);
//        }
    }


    public void handleFragmentSwitching(int position, Bundle args) {
        Fragment fragment = FragmentFactory.getFragment(
                getSupportFragmentManager(), position);
        if(args != null) {
            fragment.setArguments(args);
        }
        showNewFragment(fragment, fragment.getTag());
//        showNewFragment(fragment, ((BaseFragment)fragment).getFragmentTag());
    }

    private void showNewFragment(Fragment fragment, String fragmentTag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(((BaseFragment)fragment).getFragmentId() == SearchFragment.FRAGMENT_ID) {
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        transaction.replace(R.id.main_content, fragment, fragmentTag);
        if(getFragmentManager().findFragmentByTag(fragmentTag) == null) {
            transaction.addToBackStack(fragmentTag);
        }
        transaction.commit();
    }

//
//    public void onEventMainThread(ErrorEvent errorEvent) {
//        DialogTools.showInfoDialog(this, getString(R.string.error_dialog_title),
//                errorEvent.getMessage());
//    }
//
//    /** error handler for network responses from the Volley */
//    @Override
//    public void onErrorResponse(VolleyError error) {
//        if(error.getCause() instanceof UnknownHostException) {
//            DialogTools.showNetworkErrorDialog(this, getString(R.string.error_dialog_title),
//                    getString(R.string.network_unreachable_alarm));
//        }
//
//    }

//    private void handleCategoryOrRubricSelection(int fragmentId, String dataKey, Serializable data) {
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(dataKey, data);
//        if(drawerLayout.isDrawerOpen(drawerList)) {
//            drawerLayout.closeDrawer(drawerList);
//        }
//        clearBackStack();
//        handleFragmentSwitching(fragmentId, bundle);
//    }

    public void togleLeftDrawer() {
        if(drawerLayout.isDrawerOpen(drawerList)) {
            drawerLayout.closeDrawer(drawerList);
        } else {
            drawerLayout.openDrawer(drawerList);
        }
    }

    public void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}

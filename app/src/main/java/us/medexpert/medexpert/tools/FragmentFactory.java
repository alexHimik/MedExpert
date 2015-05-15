package us.medexpert.medexpert.tools;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import us.medexpert.medexpert.fragments.AboutFragment;
import us.medexpert.medexpert.fragments.HomeFragment;

public class FragmentFactory {
    public static final int
            ID_HOME = 0,
            ID_CATALOG = 1,
            ID_SELLERS = 2
            ;

    public static Fragment getFragment(FragmentManager fm, int fragmentId) {
        Fragment fr = fm.findFragmentById(fragmentId);
        return (fr != null) ? fr : createFragment(fragmentId);
    }

    private static Fragment createFragment(int fragmentId) {
        switch (fragmentId){
            case ID_HOME: {
                return new HomeFragment();
            }
            case AboutFragment.FRAGMENT_ID: {
                return new AboutFragment();
            }

            default: {
                return null;
            }
        }
    }
}

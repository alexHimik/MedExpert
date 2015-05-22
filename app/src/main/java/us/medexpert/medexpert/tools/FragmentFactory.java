package us.medexpert.medexpert.tools;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import us.medexpert.medexpert.fragments.AboutFragment;
import us.medexpert.medexpert.fragments.CatalogFragment;
import us.medexpert.medexpert.fragments.CategoryDrugListFragment;
import us.medexpert.medexpert.fragments.FavoritesFragment;
import us.medexpert.medexpert.fragments.HomeFragment;
import us.medexpert.medexpert.fragments.PillInfoFragment;
import us.medexpert.medexpert.fragments.RecentlyFragment;
import us.medexpert.medexpert.fragments.SellersFragment;
import us.medexpert.medexpert.fragments.TutorialFragment;

public class FragmentFactory {
    public static final String data = "data";
    public static final int
        ID_HOME = 0,
        ID_CATALOG = 1,
        ID_SELLERS = 2,
        ID_ABOUT = 3,
        ID_TUTORIAL = 4,
        ID_FAVORITES = 5,
        ID_RECENTLY = 6,
        ID_CATEGORY = 7,
        ID_PILLINFO = 8
        ;

    public static Fragment getFragment(FragmentManager fm, int fragmentId) {
        Fragment fr = fm.findFragmentById(fragmentId);
        return (fr != null) ? fr : createFragment(fragmentId);
    }

    private static Fragment createFragment(int fragmentId) {
        switch (fragmentId){
            case ID_HOME:
                return new HomeFragment();
            case ID_ABOUT:
                return new AboutFragment();
            // Karelov - START
            case ID_SELLERS:
                return new SellersFragment();
            // Karelov - END
            case ID_TUTORIAL:
                return new TutorialFragment();
            case ID_CATALOG:
                return new CatalogFragment();
            case ID_FAVORITES:
                return new FavoritesFragment();
            case ID_RECENTLY:
                return new RecentlyFragment();
            case ID_PILLINFO:
                return new PillInfoFragment();
            case ID_CATEGORY:
                return new CategoryDrugListFragment();
            default: {
                return null;
            }
        }
    }
}

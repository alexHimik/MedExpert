package us.medexpert.medexpert.tools;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import us.medexpert.medexpert.fragments.AboutFragment;
import us.medexpert.medexpert.fragments.CatalogFragment;
import us.medexpert.medexpert.fragments.HomeFragment;
import us.medexpert.medexpert.fragments.TutorialFragment;

//import kz.voxpopuli.voxapplication.dialog.ProgressFragment;
//import kz.voxpopuli.voxapplication.fragments.About;
//import kz.voxpopuli.voxapplication.fragments.CategoryFragment;
//import kz.voxpopuli.voxapplication.fragments.ChangePasswordFragment;
//import kz.voxpopuli.voxapplication.fragments.EditUserProfileFragment;
//import kz.voxpopuli.voxapplication.fragments.CommentsListFragment;
//import kz.voxpopuli.voxapplication.fragments.ForgotPasswordFragment;
//import kz.voxpopuli.voxapplication.fragments.InfoFragment;
//import kz.voxpopuli.voxapplication.fragments.LoginFragment;
//import kz.voxpopuli.voxapplication.fragments.NewsPageFragment;
//import kz.voxpopuli.voxapplication.fragments.RegistrationFragment;
//import kz.voxpopuli.voxapplication.fragments.RubricsFragment;
//import kz.voxpopuli.voxapplication.fragments.RulesFragment;
//import kz.voxpopuli.voxapplication.fragments.SearchFragment;
//import kz.voxpopuli.voxapplication.fragments.SettingsFragment;
//import kz.voxpopuli.voxapplication.fragments.UserProfileFragment;
//import kz.voxpopuli.voxapplication.fragments.VacanciesFragment;
//import kz.voxpopuli.voxapplication.fragments.VersionFragment;

public class FragmentFactory {
    public static final int
            ID_HOME = 0,
            ID_CATALOG = 1,
            ID_SELLERS = 2,
            ID_ABOUT = 3,
            ID_TUTORIAL = 4;
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
            case ID_TUTORIAL:
                return new TutorialFragment();
            case ID_CATALOG:
                return new CatalogFragment();
            default: {
                return null;
            }
        }
    }
}

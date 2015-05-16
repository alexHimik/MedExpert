package us.medexpert.medexpert.tools;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 16.05.15.
 */
public class PreferenceTools {
    private static final String PREFERENCES_NAME = "med_expert_prefs";
    private static final String TUTORIAL_STATE_KEY = "tutorial_state_key";

    public static void setTutorialShowedValue(Context context, boolean tutorialShowed) {
        getEditor(context).putBoolean(TUTORIAL_STATE_KEY, tutorialShowed).commit();
    }

    public static boolean getTutorialShowedState(Context context) {
        return getPreferences(context).getBoolean(TUTORIAL_STATE_KEY, false);
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
    }
}

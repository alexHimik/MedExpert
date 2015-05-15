package us.medexpert.medexpert.tools;

import android.app.Activity;
import android.content.res.Resources;
import android.widget.TextView;

public class ViewTools {

    public static TextView updateActionBarsTextColor(Activity activity, int resColor) {
        int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        TextView title = null;
        if (actionBarTitleId > 0) {
            title = (TextView) activity.findViewById(actionBarTitleId);
            if (title != null) {
                title.setTextColor(activity.getResources().getColor(resColor));
            }
        }
        return title;
    }

    public static int getStatusBarHeight(Activity activity) {
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}

package us.medexpert.medexpert.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.devspark.robototextview.widget.RobotoTextView;

import us.medexpert.medexpert.R;
import us.medexpert.medexpert.activity.MainActivity;
import us.medexpert.medexpert.fragments.SellersFragment;

/**
 * Created by user on 18.05.15.
 */
public class WarningDialog extends Dialog {
    // Karelov - START
    MainActivity mainActivity;
    // Karelov - END

    public WarningDialog(Context context) {
        super(context, R.style.TransparentProgressDialog);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.gravity = Gravity.CENTER_HORIZONTAL;

        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(true);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.warning_dialog, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        // Karelov - START
        RobotoTextView tv = (RobotoTextView) layout.findViewById(R.id.attention_continue);
        tv.setOnClickListener(clickListener);
        mainActivity = (MainActivity) context;
        // Karelov - END

        addContentView(layout, params);
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wlmp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(wlmp);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
            // Karelov - START
            Bundle args = new Bundle();
            args.putString("url", "http://www.google.com");
            mainActivity.handleFragmentSwitching(SellersFragment.FRAGMENT_ID, args);
            // Karelov - END
        }
    };


}

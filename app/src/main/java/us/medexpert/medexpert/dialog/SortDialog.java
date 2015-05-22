package us.medexpert.medexpert.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import us.medexpert.medexpert.R;

/**
 * Created by user on 18.05.15.
 */
public class SortDialog extends Dialog {


    public SortDialog(Context context) {
        super(context, R.style.TransparentProgressDialog);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.gravity = Gravity.CENTER_HORIZONTAL;

        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(true);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.sort_dialog, null);
        ListView lv_sort = (ListView) layout.findViewById(R.id.lv_sort);
        lv_sort.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                context, R.array.sortBy,
                R.layout.simple_list_item_single_choice_left);
        lv_sort.setAdapter(adapter);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

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
        }
    };
}

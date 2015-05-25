package us.medexpert.medexpert.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import us.medexpert.medexpert.R;

/**
 * Created by user on 18.05.15.
 */
public class SortDialog extends Dialog {

    public static final int SORT_BY_NAME_ASC = 0;
    public static final int SORT_BY_NAME_DESC = 1;
    public static final int SORT_BY_POP_ASC = 2;
    public static final int SORT_BY_POP_DESC = 3;
    public static final String SORT_ITEMS_EVENT = "sort_items_event";
    public static final String SORT_TYPE_KEY = "sort_type";

    private Context activity;
    private ListView lv_sort;

    public SortDialog(Context context) {
        super(context, R.style.TransparentProgressDialog);
        activity = context;
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.gravity = Gravity.CENTER_HORIZONTAL;

        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(true);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.sort_dialog, null);
        lv_sort = (ListView) layout.findViewById(R.id.lv_sort);
        lv_sort.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                context, R.array.sortBy,
                R.layout.simple_list_item_single_choice_left);
        lv_sort.setAdapter(adapter);
        lv_sort.setOnItemClickListener(onItemClickListener);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        addContentView(layout, params);
    }

    @Override
    public void show() {
        super.show();
        lv_sort.setSelection(0);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wlmp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(wlmp);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent sortChange = new Intent(SORT_ITEMS_EVENT);
            sortChange.putExtra(SORT_TYPE_KEY, position);
            LocalBroadcastManager.getInstance(activity).sendBroadcast(sortChange);
            dismiss();
        }
    };
}

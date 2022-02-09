package com.mynasmah.mykamus.ui.adapter;

/**
 * Created by ANAS on 2/8/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mynasmah.mykamus.data.model.History;
import com.mynasmah.mykamus.R;
import com.mynasmah.mykamus.utils.Constants;
import com.mynasmah.mykamus.data.model.save.SaveHistory;
import com.mynasmah.mykamus.ui.view.activity.ActivityHistory;

import java.util.List;

public class HistoryAdapter extends ArrayAdapter<History> {

    private Context context;
    private List<History> histories;
    private SaveHistory saveHistory;

    public HistoryAdapter(Context context, List<History> history) {
        super(context, R.layout.listview_kamus_history, history);
        this.context        = context;
        this.histories      = history;
        this.saveHistory    = new SaveHistory();
    }

    private class ViewHolder {
        TextView txt_type,txt_history;
        ImageView ic_delete,ic_search;
        LinearLayout layoutHistory;

    }

    @Override
    public int getCount() {
        return histories.size();
    }

    @Override
    public History getItem(int position) {
        return histories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            assert inflater != null;
            convertView = inflater.inflate(R.layout.listview_kamus_history, null);

            holder = new ViewHolder();

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.txt_type         = convertView.findViewById(R.id.txt_type);
        holder.txt_history      = convertView.findViewById(R.id.txt_history);
        holder.ic_delete        = convertView.findViewById(R.id.ic_clear);
        holder.ic_search        = convertView.findViewById(R.id.ic_search);
        holder.layoutHistory    = convertView.findViewById(R.id.layout_history);

        final History history = getItem(position);
        final ViewHolder finalHolder = holder;

        if(history != null) {

            if (history.getText() == null)
                history.setText("كتب");
            if (history.getType() == null)
                history.setType(Constants.OFFLINE);
            if (history.getBahasa() == null)
                history.setBahasa(Constants.AR);
            if (history.getId() < 1)
                history.setId(history.getText().length());

            holder.txt_type.setText(history.getType());

            holder.txt_history.setText(history.getText());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (history.getType().equals(Constants.OFFLINE)) {

                    holder.txt_type.setBackground(context.getDrawable(R.drawable.radius_quick_dark));

                } else {

                    holder.txt_type.setBackground(context.getDrawable(R.drawable.radius_quick_green));

                }
            }

            holder.ic_search.setOnClickListener(v -> {

                Intent intent = new Intent();

                intent.putExtra(Constants.EXTRA_HISTORY_TEXT, history.getText());

                intent.putExtra(Constants.EXTRA_HISTORY_BAHASA, history.getBahasa());

                ((ActivityHistory) context).setResult(Activity.RESULT_OK, intent);

                ((ActivityHistory) context).finish();

            });

            finalHolder.ic_delete.setOnClickListener(view -> {

                if (checkFavoriteItem(history)) {

                    saveHistory.removeHistory(getContext(), history);

                    remove(history);

                    ((ActivityHistory) context).setCountHistory(getCount());

                    Snackbar.make(view, "The History has been successfully removed!", Snackbar.LENGTH_SHORT).show();

                }
            });

        }

        return convertView;
    }

    private boolean checkFavoriteItem(History checkHistory) {

        boolean check = false;

        List<History> favorites = saveHistory.getHistory(getContext());

        if (favorites != null) {

            for (History history : favorites) {

                if (history.equals(checkHistory)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    @Override
    public void add(History history) {
        histories.add(history);
        notifyDataSetChanged();
    }

    @Override
    public void remove(History history) {
        histories.remove(history);
        notifyDataSetChanged();
    }
}
package com.mynasmah.mykamus.ui.adapter;

/**
 * Created by ANAS on 2/8/2018.
 */

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mynasmah.mykamus.R;

import java.util.List;


public class SuggestAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> suggestions;

    public SuggestAdapter(Context context, List<String> suggest) {
        super(context, R.layout.listview_kamus_suggestion, suggest);
        this.context        = context;
        this.suggestions    = suggest;
    }

    private class ViewHolder {
        TextView txt_suggest;
    }

    @Override
    public int getCount() {
        return suggestions.size();
    }

    @Override
    public String getItem(int position) {
        return suggestions.get(position);
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
            convertView = inflater.inflate(R.layout.listview_kamus_suggestion, null);

            holder = new ViewHolder();

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        holder.txt_suggest      = convertView.findViewById(R.id.txt_suggest);

        holder.txt_suggest.setText(getItem(position));

        return convertView;
    }

}
package com.cookandroid.final_project;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private List<String> items;
    Drawable originalBackground;

    public CustomAdapter(Context context, List<String> items) {
        this.context = context;
        this.items = items;
    }
    @Override
    public int getCount() {
        return items.size();
    }
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.planneritem_layout, null);
        }
        TextView textView = view.findViewById(R.id.minute);
        textView.setText(items.get(position));
        originalBackground = textView.getBackground();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleBackground(textView);
            }
        });

        return view;
    }
    private void toggleBackground(TextView textView) {
        if (textView.getBackground() == originalBackground) {
            textView.setBackgroundColor(Color.parseColor("#B6D1B7"));
        } else {
            textView.setBackground(originalBackground);
        }
    }
}

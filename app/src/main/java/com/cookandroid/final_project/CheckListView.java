package com.cookandroid.final_project;

import android.content.Context;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CheckListView extends LinearLayout {
    private Context context;
    private LinearLayout checkListViewLayout;

    public CheckListView(Context context) {
        super(context);
        this.context = context;
        initialize();
    }
    private void initialize() {
        // 체크리스트에 사용될 레이아웃 설정
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        CheckBox checkBox = new CheckBox(context);
        TextView textView = new TextView(context);

        checkListViewLayout.addView(checkBox);
        checkListViewLayout.addView(textView);

        addView(checkListViewLayout);
    }
    public LinearLayout getCheckListViewLayout() {
        return checkListViewLayout;
    }
    public void setCheckedListContext(String content) {
    }
}

package com.cookandroid.final_project;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class dateAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    String[] arrDate;
    HashMap<String, CheckListView> checkListViews;
    HashMap<String, List<String>> dateChecklistMap;

    public dateAdapter(Context context, String[] arrDate, HashMap<String, List<String>> dateChecklistMap) {
        this.context = context;
        this.arrDate = arrDate;
        this.checkListViews = new HashMap<>();
        this.dateChecklistMap = dateChecklistMap;
    }

    @Override
    public int getCount() {
        return arrDate.length;
    }
    @Override
    public Object getItem(int position) {
        return arrDate[position];
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    // position : 현재 아이템의 위치
    // view : 재활용하는 뷰
    // viewGroup : 뷰의 부모 그룹. view를 이 그룹에 추가하기 위해 사용
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null) {
            view = inflater.inflate(R.layout.date_layout, null);
        }
        TextView date = view.findViewById(R.id.dateNum);
        date.setText(arrDate[position]);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 특정 date를 클릭했을 때 해당 위치(position)에 해당되는 arrDate의 값을 clickedDate에 저장한다.
                final String clickedDate = arrDate[position];

                // dateChecklistMap : Key에 String, value에 List<String>을 저장하는 HashMap
                // get(clickedDate) : clickedDate를 Key값으로 갖는 value를 가져온다.
                final List<String>[] checklistWrapper = new List[]{dateChecklistMap.get(clickedDate)};
                // 만약 clickedDate에 맞는 value 값이 없을 때
                if (checklistWrapper[0] == null) {
                    // checklist에 새로운 체크리스트를 생성 후 put()을 사용해서 저장한다.
                    checklistWrapper[0] = new ArrayList<>();
                    dateChecklistMap.put(clickedDate, checklistWrapper[0]);
                }

                // 캘린더의 날짜를 클릭하면 색이 변한다.
                if (date.getBackground() != null) {
                    date.setBackground(null);
                } else {
                    date.setBackgroundColor(Color.parseColor("#B6D1B7"));
                }

            }
        });

        return view;
    }
}


package com.cookandroid.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatePickerDialog datePickerDialog;

    GridView gridView;

    // 캘린더에 사용되는 날짜 리스트
    String[] dateNumText = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
    };
    private HashMap<String, List<String>> dateChecklistMap = new HashMap<>();
    private List<String> checklistItems = new ArrayList<>();

    public void onPlannerViewButtonClick(View view) {
        Intent intent = new Intent(this, PlannerActivity.class);
        intent.putStringArrayListExtra("checklistItems", (ArrayList<String>) checklistItems);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 캘린더 gridView의 아이템을 dateAdapter.java로 넣기
        gridView = findViewById(R.id.gridView);
        dateAdapter dateAp = new dateAdapter(MainActivity.this, dateNumText, dateChecklistMap);
        gridView.setAdapter(dateAp);

        // 버튼 누르면 월 글씨 부분이 변경
        final TextView textView = findViewById(R.id.month);
        ImageButton leftBtn = findViewById(R.id.LeftButton);
        ImageButton rightBtn = findViewById(R.id.RightButton);

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textFromTV = textView.getText().toString();

                if (textFromTV.equals(getString(R.string.november))) {
                    textView.setText(getString(R.string.october));
                }
                else if (textFromTV.equals(getString(R.string.december))) {
                    textView.setText(getString(R.string.november));
                }
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textFromTV = textView.getText().toString();

                if (textFromTV.equals(getString(R.string.november))) {
                    textView.setText(getString(R.string.december));
                }
                else if (textFromTV.equals(getString(R.string.october))) {
                    textView.setText(getString(R.string.november));
                }
            }
        });

        EditText listText = findViewById(R.id.inputField);  // 일정 입력란
        ImageButton addBtn = findViewById(R.id.listInput);  // 일정 추가 버튼
        LinearLayout checkListItem = findViewById(R.id.checkListItem);  // 일정 리스트 목록

        // listInput을 누르면 inputField의 텍스트가 checkListItem에 저장
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // inflater을 사용해 checklist_layout을 가져옴
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View checklistLayout = inflater.inflate(R.layout.checklist_layout, null);

                    // checklist_layout.xml의 deleteButton과 checklistItemText를 가져옴
                    ImageButton deleteBtn = checklistLayout.findViewById(R.id.deleteButton);  // 삭제 버튼
                    CheckBox addText = checklistLayout.findViewById(R.id.checklistItemText);  // 일정 리스트 텍스트 뷰

                    // userInput에는 listText에 사용자가 작성한 일정을 문자열로 저장한 뒤 addText로 보낸 후 listText는 다시 공백으로 만든다.
                    String userInput = listText.getText().toString();
                    checklistItems.add(userInput);
                    addText.setText(userInput);
                    listText.setText("");

                    addText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                addText.setPaintFlags(addText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            } else {
                                addText.setPaintFlags(addText.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                            }
                        }
                    });

                    deleteBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((ViewGroup) checklistLayout.getParent()).removeView(checklistLayout);
                        }
                    });
                    checkListItem.addView(checklistLayout);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // footer.xml을 가져와 plannerViewButton을 누르면 다음 화면으로 전환
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View footerLayout = inflater.inflate(R.layout.footer_layout, null);
        ImageButton plannerViewBtn = footerLayout.findViewById(R.id.plannerViewButton);

        plannerViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlannerViewButtonClick(view);
            }
        });


    }

    public class ChecklistItem {
        private String text;
        private long timerDuration;
        private Date date;
        private float starRating;
    }
}
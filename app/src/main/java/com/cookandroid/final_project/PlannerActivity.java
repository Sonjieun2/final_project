package com.cookandroid.final_project;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PlannerActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private boolean isChronometerRunning = false;
    private AlertDialog alertDialog;
    private long chronometerBaseTime;
    GridView gridView1, gridView2;
    TextView dateText;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planner_layout);

        gridView1 = findViewById(R.id.gridView1);
        gridView2 = findViewById(R.id.gridView2);
        setupGridView();

        dateText = findViewById(R.id.pickDateView);
        ImageButton datePickBtn = findViewById(R.id.datePick);

        datePickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int pYear = calendar.get(Calendar.YEAR);
                int pMonth = calendar.get(Calendar.MONTH);
                int pDay = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(PlannerActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = year + "/" + month + "/" + day;
                        dateText.setText(date);
                    }
                }, pYear, pMonth, pDay);
                datePickerDialog.show();
            }
        });

        chronometer = findViewById(R.id.timer);

        List<String> checklistItems = getIntent().getStringArrayListExtra("checklistItems");

        LinearLayout plannerView = findViewById(R.id.plannerView);
        for (String item : checklistItems) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(item);
            checkBox.setTextSize(23);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        checkBox.setPaintFlags(checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    } else {
                        checkBox.setPaintFlags(checkBox.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    }
                }
            });
            plannerView.addView(checkBox);
        }

        ImageButton timerBtn = findViewById(R.id.timerBtn);
        timerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view);
            }
        });

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View footerLayout = inflater.inflate(R.layout.footer_layout, null);
        ImageButton homeViewBtn = footerLayout.findViewById(R.id.homeViewButton);

        homeViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHomeViewButtonClick(view);
            }
        });

    }
    private void setupGridView() {
        List<String> gridViewItem = generateGridViewItems();

        CustomAdapter gridViewAdapter = new CustomAdapter(this, gridViewItem);
        gridView1.setAdapter(gridViewAdapter);
        gridView2.setAdapter(gridViewAdapter);
    }
    private List<String> generateGridViewItems() {
        List<String> items = new ArrayList<>();
        for (int i=0 ; i<6*12 ; i++) {
            items.add("");
        }
        return items;
    }
    public void openDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlannerActivity.this);
        builder.setTitle("timer");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        builder.setView(dialogView);

        Chronometer dialogChronometer = dialogView.findViewById(R.id.dialogTimer);
        ImageButton startStopBtn = dialogView.findViewById(R.id.startStopButton);
        ImageButton resetBtn = dialogView.findViewById(R.id.resetButton);
        ImageButton closeBtn = dialogView.findViewById(R.id.closeViewButton);

        startStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChronometerRunning) {
                    long elapsedMillis = SystemClock.elapsedRealtime() - chronometerBaseTime;
                    chronometer.stop();
                    dialogChronometer.stop();
                    isChronometerRunning = false;
                    startStopBtn.setImageResource(R.drawable.timer_start_btn);
                } else {
                    chronometerBaseTime = SystemClock.elapsedRealtime() - chronometer.getBase();
                    chronometer.start();
                    dialogChronometer.start();
                    isChronometerRunning = true;
                    startStopBtn.setImageResource(R.drawable.timer_stop_btn);
                }
            }
        });
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.stop();
                dialogChronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime());
                dialogChronometer.setBase(SystemClock.elapsedRealtime());
                chronometerBaseTime = 0;
                isChronometerRunning = false;
            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.show();
    }
    // 홈 버튼 누르면 홈 화면으로 이동
    public void onHomeViewButtonClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

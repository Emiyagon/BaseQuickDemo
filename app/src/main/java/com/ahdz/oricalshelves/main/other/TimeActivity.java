package com.ahdz.oricalshelves.main.other;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ahdz.oricalshelves.R;
import com.ahdz.oricalshelves.util.ListUtil;
import com.ahdz.oricalshelves.util.RxTimerUtil;
import com.ahdz.oricalshelves.view.dialog.ComPopupDialog;
import com.ahdz.oricalshelves.view.ioswheel.LoopView;
import com.ahdz.oricalshelves.view.ioswheel.OnItemSelectedListener;

import java.util.Calendar;
import java.util.List;

public class TimeActivity extends AppCompatActivity {

    private TextView tv_time;

    private boolean isStart = false;
    private List<Integer> yearList = ListUtil.numList(1970, 2050);
    private Calendar calendar = Calendar.getInstance();
    private int nowYear;
    private int nowMonth;
    private int nowDay;
    private int chooseYear,chooseMonth,chooseDay,chooseMinute,chooseHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        tv_time = findViewById(R.id.tv_time);
        calendar = Calendar.getInstance();
        nowYear = calendar.get(Calendar.YEAR);
        nowMonth = calendar.get(Calendar.MONTH+1);
        nowDay = calendar.get(Calendar.DAY_OF_MONTH);
        chooseYear = nowYear;
        chooseMonth = nowMonth;
        chooseDay = nowDay;
        chooseMinute=calendar.get(Calendar.MINUTE);
        chooseHour =calendar.get(Calendar.HOUR_OF_DAY);


        LoopView year = findViewById(R.id.year);
        year.setItems(ListUtil.returnnumList(1970,2050));

        year.setItemsVisibleCount(8);

        year.setDividerColor(0xffFED452); //分割线颜色#FED452

        year.setTextSize(19f);
        //  隔1000ms计时一次
        RxTimerUtil.interval(1000, number -> {
            if (!isStart) {
                return;
            }


        });

        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStart = true;


                new ComPopupDialog(TimeActivity.this, R.layout.dialog_time) {
                    LoopView year,month,day,minute,hour;
                    View enter;
                    @Override
                    public void initView() {
                        View itemView = getContentView();
                        year = itemView.findViewById(R.id.year);
                        month = itemView.findViewById(R.id.month);
                        day = itemView.findViewById(R.id.day);
                        minute = itemView.findViewById(R.id.minute);
                        hour = itemView.findViewById(R.id.hour);
                        enter = itemView.findViewById(R.id.enter);


                    }

                    @Override
                    public void initEvent() {

                        year.setItems(ListUtil.returnnumList(1970,2050));
                        month.setItems(ListUtil.returnnumList(1,13));
                        day.setItems(ListUtil.returnnumList(1,31));
                        hour.setItems(ListUtil.returnnumList(0,24));
                        minute.setItems(ListUtil.returnnumList(0,60));

                        year.setItemsVisibleCount(8);
                        month.setItemsVisibleCount(8);
                        day.setItemsVisibleCount(8);
                        hour.setItemsVisibleCount(8);
                        minute.setItemsVisibleCount(8);

                        year.setDividerColor(0xffFED452); //分割线颜色#FED452
                        month.setDividerColor(0xffFED452); //分割线颜色#FED452
                        day.setDividerColor(0xffFED452); //分割线颜色#FED452

                        year.setTextSize(19f);

                        //   app:awv_centerTextColor="0xffff0000"
                        //app:awv_dividerTextColor="0xffeeeeee"
                        year.setCenterTextColor(getResources().getColor(R.color.textBlack));
                        month.setCenterTextColor(getResources().getColor(R.color.textBlack));
                        day.setCenterTextColor(getResources().getColor(R.color.textBlack));

                        year.setOuterTextColor(getResources().getColor(R.color.textGray));
                        month.setOuterTextColor(getResources().getColor(R.color.textGray));
                        day.setOuterTextColor(getResources().getColor(R.color.textGray));

//                        year.setInitPosition(nowYear-1970);
//                        month.setInitPosition(nowMonth);
//                        day.setInitPosition(nowDay);
//                        hour.setInitPosition(calendar.get(Calendar.HOUR_OF_DAY));
//                        minute.setInitPosition(calendar.get(Calendar.MINUTE));
                        year.setListener(index -> {
                            chooseYear = 1970 + index;
                        });
                        month.setListener(index -> {
                            chooseMonth = index+1;
                        });
                        day.setListener(index -> {
                            chooseDay = index + 1;
                        });
                        hour.setListener(index -> {
                            chooseHour = index;
                        });
                        minute.setListener(index -> {
                            chooseMinute = index;
                        });
                    }
                }.show();
            }
        });

    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxTimerUtil.cancel();
    }
}
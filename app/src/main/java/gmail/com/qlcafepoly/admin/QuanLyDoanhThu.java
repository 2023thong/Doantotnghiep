package gmail.com.qlcafepoly.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import gmail.com.qlcafepoly.R;

public class QuanLyDoanhThu extends AppCompatActivity {
    TextView tvTongDoanhThu;
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_doanh_thu);
        tvTongDoanhThu = findViewById(R.id.tvTongDoanhThu);
        calendarView = findViewById(R.id.calendarView);
        int totalAmount = GlobalData.totalAmount;
        // Find the TextView in your QuanLyDoanhThu layout
        // Set the totalAmount to the TextView
        tvTongDoanhThu.setText(String.valueOf(totalAmount) + " vnd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        tvTongDoanhThu.setText(currentDate + ": " + totalAmount + " vnd");

        // Set an OnDateChangeListener to handle date changes
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                // Update the TextView with the selected date
                tvTongDoanhThu.setText(selectedDate + ": " + totalAmount + " vnd");
            }
        });
    }
    public void backdoanhthu (View view){
        finish();
    }
}
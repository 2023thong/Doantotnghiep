package gmail.com.qlcafepoly.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.nhanvien.Oder;

public class QuanLyDoanhThu extends AppCompatActivity {
    private CalendarView calendarView;
    private TextView tvTongDoanhThu;

    private Map<String, List<Oder>> dailyOders = new HashMap<>();
    private Map<String, Integer> dailyTotalAmounts = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_doanh_thu);
        tvTongDoanhThu = findViewById(R.id.tvTongDoanhThu);
        calendarView = findViewById(R.id.calendarView);
        calculateDailyTotalAmounts();
        int totalAmount = GlobalData.totalAmount;
        // Find the TextView in your QuanLyDoanhThu layout
        // Set the totalAmount to the TextView
        tvTongDoanhThu.setText(String.valueOf(totalAmount) + " vnd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        tvTongDoanhThu.setText(currentDate + ": " + totalAmount + " vnd");

        // Set an OnDateChangeListener to handle date changes
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                showTotalAmountForSelectedDate(selectedDate);
            }
        });
    }
    private void calculateDailyTotalAmounts() {
        // Fetch data from your source and populate dailyOders
        // For example:
        // Oder oder1 = new Oder("25/11/2023", "10000", "MaOder1", ...);
        // Oder oder2 = new Oder("25/11/2023", "15000", "MaOder2", ...);
        // Oder oder3 = new Oder("26/11/2023", "20000", "MaOder3", ...);

        // Assuming odersList is a list of Oder objects
        // Replace this with the actual data fetching logic
        List<Oder> odersList = fetchDataForDailyOders();

        // Iterate through the list of oders and populate dailyOders map
        for (Oder oder : odersList) {
            String selectedDate = oder.getNgay();

            // Check if the date is already in the map
            if (dailyOders.containsKey(selectedDate)) {
                dailyOders.get(selectedDate).add(oder);
            } else {
                // If not, create a new list and add the Oder
                List<Oder> newList = new ArrayList<>();
                newList.add(oder);
                dailyOders.put(selectedDate, newList);
            }
        }

        // Calculate total amounts for each MaOder
        for (Map.Entry<String, List<Oder>> entry : dailyOders.entrySet()) {
            String date = entry.getKey();
            List<Oder> oders = entry.getValue();

            int totalAmount = 0;

            for (Oder oder : oders) {
                totalAmount += Integer.parseInt(oder.getTongTien());
            }

            dailyTotalAmounts.put(date, totalAmount);
        }
    }

    // Replace this method with your actual data fetching logic
    private List<Oder> fetchDataForDailyOders() {
        // Fetch data from your source and return a list of Oder objects
        List<Oder> odersList = new ArrayList<>();

        // Assuming Ngay is a String representing datetime
        // Assuming you want to convert Ngay to dd/MM/yyyy format
        odersList = OderUtils.convertNgayFormat(odersList);

        Log.d("Debug", "Fetched data: " + odersList.size() + " records");

        return odersList;
    }

    private void showTotalAmountForSelectedDate(String selectedDate) {
        int totalAmount = dailyTotalAmounts.containsKey(selectedDate) ? dailyTotalAmounts.get(selectedDate) : 0;
        Log.d("Debug", "Selected Date: " + selectedDate + ", Total Amount: " + totalAmount);

        String displayText = String.format("%s: %d VND", selectedDate, totalAmount);
        tvTongDoanhThu.setText(displayText);
    }

    public void backdoanhthu (View view){
        finish();
    }
}
package gmail.com.qlcafepoly.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.nhanvien.Oder;
import gmail.com.qlcafepoly.nhanvien.Thongtinoder;

public class QuanLyDoanhThu extends AppCompatActivity {
    private CalendarView calendarView;
    private TextView tvTongDoanhThu, tvTongDoanhThuNgay;
    private ListView lvTongDoanhThuNgay;

    private Map<String, List<Oder>> dailyOders = new HashMap<>();
    private Map<String, Integer> dailyTotalAmounts = new HashMap<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_doanh_thu);
        tvTongDoanhThu = findViewById(R.id.tvTongDoanhThu);
        tvTongDoanhThuNgay = findViewById(R.id.tvTongDoanhThuNgay);
        calendarView = findViewById(R.id.calendarView);
        int totalAmount = GlobalData.totalAmount;
        // Find the TextView in your QuanLyDoanhThu layout
        // Set the totalAmount to the TextView
        tvTongDoanhThu.setText(String.valueOf(totalAmount) + " vnd");
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                // Increment month by 1 to match human-readable month representation
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                Toast.makeText(QuanLyDoanhThu.this, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
                calculateAndDisplayTotalForSelectedDate(selectedDate);
            }
        });
    }
    private void calculateAndDisplayTotalForSelectedDate(String selectedDate) {
        int totalAmountForSelectedDate = 0;

        // Lấy danh sách đơn hàng cho ngày được chọn
        List<Thongtinoder> ordersForSelectedDate = getOrdersForSelectedDate(selectedDate);

        if (ordersForSelectedDate != null) {
            for (Thongtinoder order : ordersForSelectedDate) {
                Log.d("Selected Date", selectedDate);
                Log.d("Order Date", order.getNgay());

                if (order.getTrangThai() == 1) { // Đơn hàng đã thanh toán
                    totalAmountForSelectedDate += order.getTongTien();
                }
            }
        }

        // Hiển thị tổng tiền cho ngày được chọn
        tvTongDoanhThuNgay.setText(String.valueOf(totalAmountForSelectedDate) + " VND");
    }
    private List<Thongtinoder> getOrdersForSelectedDate(String selectedDate) {
        List<Thongtinoder> ordersForSelectedDate = new ArrayList<>();

        for (Thongtinoder order : QuanLyHoaDon.listPay) {
            if (isSameDate(order.getNgay(), selectedDate)) {
                ordersForSelectedDate.add(order);
            }
        }

        return ordersForSelectedDate;
    }
    private boolean isSameDate(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date dateTime1 = sdf.parse(date1);
            Date dateTime2 = sdf.parse(date2);

            // Truncate time component
            dateTime1 = truncateTime(dateTime1);
            dateTime2 = truncateTime(dateTime2);

            return dateTime1.equals(dateTime2);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Date truncateTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    public String getFormattedDate(Date date) {
        Date orderDate = new Date();  // Hoặc là ngày cụ thể khác
        String formattedDate = getFormattedDate(orderDate);
        return formattedDate;
    }

    // Phương thức tính tổng tiền cho một MaOder trong ngày cụ thể
    private String calculateTotalAmountForMaOderOnDate(int maOder, String selectedDate) {
        String totalAmountForMaOder = String.valueOf(0);

        // Lấy danh sách đơn hàng từ ListView lvQLHD
        List<Oder> ordersFromListView = getOrdersFromListView();

        if (ordersFromListView != null) {
            for (Oder order : ordersFromListView) {
                if (order.isPaid()) {
                    int currentMaOder = Integer.parseInt(order.getMaOder());
                    if (currentMaOder == maOder) {
                        // Tính tổng tiền cho đơn hàng cụ thể
                        totalAmountForMaOder += order.getTongTien(); // (hoặc là phương thức khác bạn đã định nghĩa)
                    }
                }
            }
        }

        return totalAmountForMaOder;
    }

    private List<Oder> getOrdersFromListView() {
        // TODO: Lấy danh sách đơn hàng từ ListView lvQLHD
        // Bạn cần thay thế dòng sau đây bằng cách lấy dữ liệu từ lvQLHD của QuanLyHoaDon
        // List<Oder> orders = ???
        // return orders;
        return null;
    }
    // Replace this method with your actual data fetching logic
    public void backdoanhthu (View view){
        finish();
    }
}
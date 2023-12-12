package gmail.com.qlcafepoly.admin;


import static gmail.com.qlcafepoly.Database.Constants.BASE_URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.nhanvien.Menu_pay;
import gmail.com.qlcafepoly.nhanvien.Oder;
import gmail.com.qlcafepoly.nhanvien.OderDu;
import gmail.com.qlcafepoly.nhanvien.Pay1;
import gmail.com.qlcafepoly.nhanvien.Thongtinoder;

public class QuanLyHoaDon extends AppCompatActivity {
    public static List<Thongtinoder> listPay = new ArrayList<>();
    private List<Thongtinoder> filteredList = new ArrayList<>();
//    private List<Thongtinoder> listPay = new ArrayList<>();
    private List<Oder> odermenu = new ArrayList<>();
    private QLHD1 qlhd1;
    private ImageView imageView,imgLich;
    private TextView tvTongTien,tvTongBill;
    private ListView lvQLHD;
    private String urllink = BASE_URL+ "duantotnghiep/oder.php";
    private ProgressDialog pd;

    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_hoa_don);
        imageView = findViewById(R.id.img_Douong);
        lvQLHD = findViewById(R.id.lvQLHD);
        tvTongTien = findViewById(R.id.tvTongtien);
        tvTongBill = findViewById(R.id.tvTongBill);
        qlhd1 = new QLHD1(QuanLyHoaDon.this, listPay);
        lvQLHD.setAdapter(qlhd1);
        pd = new ProgressDialog(QuanLyHoaDon.this);
        pd.setMessage("Đang tải dữ liệu...");
        pd.setCancelable(false);
        new QuanLyHoaDon.MyAsyncTask().execute(urllink);
        updateTotalAmount();
    }
    private void filterBillsByDate(String selectedDate) {
        filteredList.clear(); // Clear previous filtered data
        for (Thongtinoder thongtinoder : listPay) {
            // Ensure that both dates are in the same format for comparison
            String thongtinoderDate = thongtinoder.getNgay();
            if (isSameDate(thongtinoderDate, selectedDate)) {
                filteredList.add(thongtinoder);
            }
        }

        // Update the adapter with the filtered data
        qlhd1.setListPay(filteredList);
    }
    private boolean isSameDate(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dateTime1 = sdf.parse(date1);
            Date dateTime2 = sdf.parse(date2);
            // So sánh ngày, tháng, năm
            return dateTime1.equals(dateTime2);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    private class MyAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Đang tải dữ liệu...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                String strJson = readJsonOnline(strings[0]);
                Log.d("//====", strJson);

                JSONObject jsonObject = new JSONObject(strJson);
                int success = jsonObject.getInt("success");
                if (success == 1) {
                    JSONArray jsonArrayPay = jsonObject.getJSONArray("oder");
                    Log.d("//=====size=====", jsonArrayPay.length() + "");
                    for (int i = 0; i < jsonArrayPay.length(); i++) {
                        JSONObject PayObject = jsonArrayPay.getJSONObject(i);
                        Log.d("MaOder", PayObject.getString("MaOder"));
                        Log.d("MaBn", PayObject.getString("MaBn"));
                        Log.d("TongTien", PayObject.getString("TongTien"));
                        Log.d("Ngay", PayObject.getString("Ngay"));
                        String MaOder = PayObject.getString("MaOder");
                        String MaBn = PayObject.getString("MaBn");
                        int TrangThai = PayObject.getInt("TrangThai");
                        String Tongtien = PayObject.getString("TongTien");
                        String Ngay = PayObject.getString("Ngay");
                        String traTien = PayObject.getString("TrangThai");

                        // Áp dụng định dạng giá tiền
                        DecimalFormat decimalFormat = new DecimalFormat("#,##0.###");
                        String formattedTongtien = decimalFormat.format(Double.parseDouble(Tongtien));
                        boolean isMaOderExists = false;
                        for (Thongtinoder existingThongtinoder : listPay) {
                            if (existingThongtinoder.getMaOder() == Integer.parseInt(MaOder)) {
                                isMaOderExists = true;

                                // Cập nhật thông tin cho Thongtinoder đã tồn tại nếu cần
                                // ...

                                break; // thoát khỏi vòng lặp nếu đã tìm thấy MaOder trong listPay
                            }
                        }

                        if (!isMaOderExists) {
                            Thongtinoder thongtinoder = new Thongtinoder();


                            thongtinoder.setMaOder(Integer.parseInt(MaOder));
                            thongtinoder.setMaBn(new String(MaBn));
                            thongtinoder.setTrangThai(TrangThai);
                            thongtinoder.setTongTien(Integer.parseInt(Tongtien));
                            thongtinoder.setFormattedTongtien(formattedTongtien);
                            thongtinoder.setNgay(Ngay);
                            thongtinoder.setTratien(Integer.parseInt(traTien));
                            listPay.add(thongtinoder);
                        }
                    }
                } else {
                    Log.d("Error: ", "Failed to fetch data. Success is not 1.");
                }
            } catch (JSONException e) {
                Log.d("Error: ", e.toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (pd.isShowing()) {
                pd.dismiss();
            }

            // Sắp xếp listPay theo thời gian giảm dần
            Collections.sort(listPay, new Comparator<Thongtinoder>() {
                @Override
                public int compare(Thongtinoder o1, Thongtinoder o2) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        Date date1 = sdf.parse(o1.getNgay());
                        Date date2 = sdf.parse(o2.getNgay());
                        return date2.compareTo(date1);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return 0;
                    }
                }
            });

            qlhd1.notifyDataSetChanged();
            updateTotalAmount();
            TotalAmount();
        }


        public String readJsonOnline(String linkUrl) {
            HttpURLConnection connection = null;
            BufferedReader bufferedReader = null;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(linkUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                return stringBuilder.toString();
            } catch (Exception ex) {
                Log.d("Error: ", ex.toString());
            }
            return null;
        }

    }
    private void updateTotalAmount() {
        int totalAmount = 0;

        for (Thongtinoder thongtinoder : listPay) {
            totalAmount += thongtinoder.getTongTien();
        }
        TextView totalAmountTextView = findViewById(R.id.tvTongTien);
        totalAmountTextView.setText(formatCurrency(totalAmount)+ " VND");
    }

    public void btnxemdanhsachban(int MaOder) {
        // Tạo một Intent và truyền tham số maOder
        Intent intent = new Intent(getApplicationContext(), XemQLHD.class);
        intent.putExtra("MaOder", MaOder);
        intent.putExtra("TotalAmount", getTotalAmount());
        startActivity(intent);
    }
    private void TotalAmount() {
        int totalAmount = 0;
        int totalMaOder = 0;
        int totalPaidBills = 0;

        for (Thongtinoder thongtinoder : listPay) {
            totalMaOder++;
            if (thongtinoder.getTrangThai() == 1) { // Check if the bill is paid
                totalAmount += thongtinoder.getTongTien();
                totalPaidBills++;
            }
        }

        GlobalData.totalAmount = totalAmount;

        TextView totalAmountTextView = findViewById(R.id.tvTongTien);
        totalAmountTextView.setText(formatCurrency(totalAmount) + " vnd");

        TextView totalPaidBillsTextView = findViewById(R.id.tvTongBill);
        totalPaidBillsTextView.setText(String.valueOf(totalPaidBills));

        TextView totalMaOderTextView = findViewById(R.id.tvTongBill);
        totalMaOderTextView.setText(String.valueOf("Số bill: " + totalMaOder));
    }
    private String formatCurrency(int amount) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        return numberFormat.format(amount);
    }
    private int getTotalAmount() {
        int totalAmount = 0;
        for (Thongtinoder thongtinoder : listPay) {
            totalAmount += thongtinoder.getTongTien();
        }

        return totalAmount;
    }
    public void backHoaDon (View view){
        finish();
    }
}
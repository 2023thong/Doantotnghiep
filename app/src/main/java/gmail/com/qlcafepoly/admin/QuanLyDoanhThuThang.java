package gmail.com.qlcafepoly.admin;

import static gmail.com.qlcafepoly.Database.Constants.BASE_URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.nhanvien.Hoadon;

public class QuanLyDoanhThuThang extends AppCompatActivity {
    private List<Hoadon> lsuList= new ArrayList<>();
    private DoanhThuThang doanhThuThang;
    private TextView tvTongDoanhThu;
    private ImageView icLich;


    private String urllink = BASE_URL +"duantotnghiep/doanhthutheothang.php";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_doanh_thu_thang);
        tvTongDoanhThu = findViewById(R.id.tvTongDoanhThu);
        int totalAmount = GlobalData.totalAmount;
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        String formattedTotalAmount = numberFormat.format(totalAmount);
        ListView doanhthuthang = findViewById(R.id.lsdoanhthuthang);
        doanhThuThang = new DoanhThuThang(QuanLyDoanhThuThang.this, lsuList);
        doanhthuthang.setAdapter(doanhThuThang);
        tvTongDoanhThu.setText(formattedTotalAmount + " vnd");
        Button btnDTNgay = findViewById(R.id.btnDTNgay1);
        btnDTNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyDoanhThuThang.this, QuanLyDoanhThu.class);
                startActivity(intent);
            }
        });
        Button btnDTThang = findViewById(R.id.btnDTThang1);
        btnDTThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyDoanhThuThang.this, QuanLyDoanhThuThang.class);
                startActivity(intent);
            }
        });
        Button btnDTNam = findViewById(R.id.btnDTNam1);
        btnDTNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyDoanhThuThang.this, QuanLyDoanhThuNam.class);
                startActivity(intent);
            }
        });
        icLich = findViewById(R.id.icLich);
        icLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendarDialog();
            }
        });
        new QuanLyDoanhThuThang.MyAsyncTask().execute(urllink);

    }
    private void showCalendarDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_calendar, null);
        builder.setView(dialogView);

        CalendarView calendarViewDialog = dialogView.findViewById(R.id.calendarViewDialog);
        calendarViewDialog.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                // Handle the selected date change
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                Toast.makeText(QuanLyDoanhThuThang.this, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Handle OK button click if needed
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Handle Cancel button click if needed
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void backdoanhthu1(View view){
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Kiểm tra xem có fragment trong Stack không
        if (fragmentManager.getBackStackEntryCount() > 0) {
            // Nếu có, quay lại fragment trước đó
            fragmentManager.popBackStack();
        } else {
            // Nếu không, kết thúc activity hoặc thực hiện hành động khác tùy thuộc vào yêu cầu của bạn
            finish();
        }
    }
    private class MyAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (pd != null) {
                pd.setMessage("Loading...");
            } else {
                // Xử lý nếu pd là null
            }

        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                String strJson = readJsonOnline(strings[0]);
                Log.d("//====", strJson);

                JSONObject jsonObject = new JSONObject(strJson);
                int success = jsonObject.getInt("success");
                if (success == 1) {
                    JSONArray jsonArrayhanghoa = jsonObject.getJSONArray("doanhthuthang");
                    Log.d("//=====size===", jsonArrayhanghoa.length() + "");

                    for (int i = 0; i < jsonArrayhanghoa.length(); i++) {
                        JSONObject nhanvienObject = jsonArrayhanghoa.getJSONObject(i);
                        Log.d("Thang", nhanvienObject.getString("Thang"));
                        Log.d("DoanhThuNgay", nhanvienObject.getString("DoanhThuThang"));
                        String Thang = nhanvienObject.getString("Thang");
                        String DTThang = nhanvienObject.getString("DoanhThuThang");
                        Hoadon hoadon = new Hoadon();
                        hoadon.setThoigian(Thang);
                        hoadon.setTongTien(Integer.parseInt(DTThang));
                        lsuList.add(hoadon);
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
            if (doanhThuThang != null) {
                doanhThuThang.notifyDataSetChanged();
            }

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
}
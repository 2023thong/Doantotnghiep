package gmail.com.qlcafepoly.nhanvien;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import gmail.com.qlcafepoly.R;

public class Pay1 extends BaseAdapter {
    private List<Thongtinoder> ttoder;
    private LayoutInflater inflater;
    private Context context;
    private String selectedDate;
    private Calendar calendar;

    public Pay1(Context context, List<Thongtinoder> ttoder) {
        this.ttoder = ttoder;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    @Override
    public int getCount() {
        return ttoder.size();
    }

    @Override
    public Object getItem(int position) {
        return ttoder.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_pay, parent, false);
        }

        Thongtinoder thongtinoder = ttoder.get(position);
        TextView tvTrangThai = convertView.findViewById(R.id.tvTrangthai);
        TextView maOder = convertView.findViewById(R.id.tvMaOder);
        TextView maBn = convertView.findViewById(R.id.tvbantrong);
        Button button = convertView.findViewById(R.id.btnxemdanhsachban);
        TextView tongTien = convertView.findViewById(R.id.tvTongtien);
        TextView ngay = convertView.findViewById(R.id.tvNgay);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Menu_pay.class);
                intent.putExtra("Maoder1", String.valueOf(thongtinoder.getMaOder()));
                intent.putExtra("Tongtien", String.valueOf(thongtinoder.getTongTien()));
                intent.putExtra("Mabn", String.valueOf(thongtinoder.getMaBn()));
                intent.putExtra("Ngay", String.valueOf(thongtinoder.getNgay()));
                intent.putExtra("SelectedDate", selectedDate);
                context.startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences = convertView.getContext().getSharedPreferences("loc", Context.MODE_PRIVATE);
        String ngay1 = sharedPreferences.getString("Ngay", "");
        Log.d("test1", thongtinoder.getNgay());

        int trangThai = thongtinoder.getTrangThai();

        try {
            if (trangThai == 1 && ngay1.equals(thongtinoder.getNgay())) {
                tvTrangThai.setText("Đã thanh toán");
                maOder.setText(String.valueOf(thongtinoder.getMaOder()));
                maBn.setText(String.valueOf(thongtinoder.getMaBn()));
                tongTien.setText(formatCurrency(Double.parseDouble(String.valueOf(thongtinoder.getTongTien()))));
                ngay.setText(formatDate(thongtinoder.getNgay()));
            } else {
                convertView.setVisibility(View.GONE);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }


        return convertView;
    }

    private String formatCurrency(double value) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(value);
    }
    private String formatDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
            Date parsedDate = sdf.parse(date);

            // Format the date to the desired format
            sdf.applyPattern("yyyy/MM/dd");
            return sdf.format(parsedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date; // Return the original date if an exception occurs
    }
    public void clear() {
        ttoder.clear();
    }

    public void addAll(List<Thongtinoder> newList) {
        ttoder.addAll(newList);
    }
}

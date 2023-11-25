package gmail.com.qlcafepoly.nhanvien;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import gmail.com.qlcafepoly.Database.Constants;
import gmail.com.qlcafepoly.Database.RequestInterface;
import gmail.com.qlcafepoly.Database.ServerResponse;
import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.admin.AdminKho;
import gmail.com.qlcafepoly.admin.Hanghoaht;
import gmail.com.qlcafepoly.admin.Suathongtinhh;
import gmail.com.qlcafepoly.admin.User;
import gmail.com.qlcafepoly.dangnhap.Danhnhap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Unpaid1 extends BaseAdapter {
    private List<Thongtinoder> ttoder;
    private LayoutInflater inflater;
    private Context context;
    private PayDU payDuAdapter;

    public Unpaid1(Context context, List<Thongtinoder> ttoder) {
        this.ttoder = ttoder;
        this.context = context;
        inflater = LayoutInflater.from(context);
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
            convertView = inflater.inflate(R.layout.list_unpaid, parent, false);
        }

        Thongtinoder thongtinoder = ttoder.get(position);

        TextView maOder = convertView.findViewById(R.id.tvMaOder);
        TextView maBn = convertView.findViewById(R.id.tvbantrong);
        TextView tongTien = convertView.findViewById(R.id.tvTongtien);
        Button button = convertView.findViewById(R.id.btnxemdanhsachban);
        Button btnThanhToan = convertView.findViewById(R.id.btnThanhToan);
        TextView ngay = convertView.findViewById(R.id.date);
        String currentDate = getCurrentDate();

        ngay.setText(currentDate);



        int trangThai = thongtinoder.getTrangThai();
        if (trangThai == 2) {


            // Gán giá trị cho các TextView
            maOder.setText(String.valueOf(thongtinoder.getMaOder()));
            maBn.setText(String.valueOf(thongtinoder.getMaBn()));
            tongTien.setText(String.valueOf(thongtinoder.getTongTien()));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maOder = thongtinoder.getMaOder();
                ((Unpaid) context).btnxemdanhsachban(maOder);
            }
        });

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(context instanceof Unpaid){
                    ((Unpaid) context).Dangnhap(String.valueOf(thongtinoder.getMaOder()), "1");

                }
                if(context instanceof Unpaid){
                    ((Unpaid) context).Suatrangthaiban((thongtinoder.getMaBn()), "1");

                }

            }
        });
        Button btnSua = convertView.findViewById(R.id.btnSuaoder);
        // Assuming you have a Button or another View with click listener
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Extract the data you want to send
                String maOder = String.valueOf(thongtinoder.getMaOder());
                String maBn = thongtinoder.getMaBn();
                String tongTien = String.valueOf(thongtinoder.getTongTien());

                // Create an Intent
                Intent intent = new Intent(context, SuaOder.class);

                // Put the data into the Intent
                intent.putExtra("MaOderoder", maOder);
                intent.putExtra("MaBnoder", maBn);
                intent.putExtra("TongTienoder", tongTien);

                // Start the new activity
                context.startActivity(intent);
                SharedPreferences sharedPreferences = context.getSharedPreferences("menu1", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Maoder", maOder);  // Replace "TenDn" with your key and TenDn with the value you want to store
                editor.apply();
            }
        });


        return convertView;
    }
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy  HH:mm", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);

    }


}
package gmail.com.qlcafepoly.nhanvien;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;
import gmail.com.qlcafepoly.R;

public class Pay1 extends BaseAdapter {
    private List<Thongtinoder> ttoder;
    private LayoutInflater inflater;
    private Context context;

    public Pay1(Context context, List<Thongtinoder> ttoder) {
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
                intent.putExtra("Maoder1",String.valueOf(thongtinoder.getMaOder()));
                intent.putExtra("Tongtien",String.valueOf(thongtinoder.getTongTien()));
                intent.putExtra("Mabn",String.valueOf(thongtinoder.getMaBn()));
                intent.putExtra("Ngay", String.valueOf(thongtinoder.getNgay()));
                context.startActivity(intent);
            }
        });
        int trangThai = thongtinoder.getTrangThai();
        if (trangThai == 1) {
            tvTrangThai.setText("Đã thanh toán");
            maOder.setText(String.valueOf(thongtinoder.getMaOder()));
            maBn.setText(String.valueOf(thongtinoder.getMaBn()));
            tongTien.setText(String.valueOf(thongtinoder.getTongTien()));
            ngay.setText(thongtinoder.getNgay());

        } else {
            // Nếu trạng thái không phải 1, ẩn convertView
            convertView.setVisibility(View.GONE);
        }


        return convertView;
    }


}
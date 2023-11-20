package gmail.com.qlcafepoly.nhanvien;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import gmail.com.qlcafepoly.R;

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

    // Trong adapter
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


        int trangThai = thongtinoder.getTrangThai();

        if (trangThai == 0) {
            maOder.setText(String.valueOf(thongtinoder.getMaOder()));
            maBn.setText(String.valueOf(thongtinoder.getMaBn()));
            tongTien.setText(String.valueOf(thongtinoder.getTongTien()));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context instanceof FragmentActivity) {
                        UnpaidFragment unpaidFragment = new UnpaidFragment();
                        Bundle args = new Bundle();
                        args.putInt("maOder", thongtinoder.getMaOder());  // Chuyển đổi int thành String ở đây nếu cần
                        unpaidFragment.setArguments(args);

                        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.lv_unpaid, unpaidFragment);
                        transaction.addToBackStack(null);  // Tuỳ chọn: Thêm vào ngăn xếp để theo dõi lịch sử điều hướng fragment
                        transaction.commit();
                    }
                }

            });

            btnThanhToan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context instanceof FragmentActivity) {
                        UnpaidFragment unpaidFragment = new UnpaidFragment();
                        Bundle args = new Bundle();
                        args.putString("maOder", String.valueOf(thongtinoder.getMaOder()));
                        args.putString("parameter2", "1");
                        unpaidFragment.setArguments(args);

                        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.lv_unpaid, unpaidFragment);
                        transaction.addToBackStack(null);  // Tuỳ chọn: Thêm vào ngăn xếp để theo dõi lịch sử điều hướng fragment
                        transaction.commit();
                    }
                }
            });

            Button btnSua = convertView.findViewById(R.id.btnSuaoder);
            btnSua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String maOder = String.valueOf(thongtinoder.getMaOder());
                    String maBn = thongtinoder.getMaBn();
                    String tongTien = String.valueOf(thongtinoder.getTongTien());

                    Intent intent = new Intent(context, SuaOder.class);
                    intent.putExtra("MaOderoder", maOder);
                    intent.putExtra("MaBnoder", maBn);
                    intent.putExtra("TongTienoder", tongTien);

                    context.startActivity(intent);

                    SharedPreferences sharedPreferences = context.getSharedPreferences("menu1", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Maoder", maOder);
                    editor.apply();
                }
            });
        }

        return convertView;
    }
}

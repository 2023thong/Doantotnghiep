package gmail.com.qlcafepoly.nhanvien;

import android.content.Context;
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

public class Pay1 extends BaseAdapter {
    private List<Thongtinoder> ttoder;
    private Context context;
    private LayoutInflater inflater;


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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof FragmentActivity) {
                    UnpaidFragment unpaidFragment = new UnpaidFragment();
                    Bundle args = new Bundle();
                    args.putInt("maOder", thongtinoder.getMaOder());  // Chuyển đổi int thành String ở đây nếu cần
                    unpaidFragment.setArguments(args);

                    FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayoutcht, unpaidFragment);
                    transaction.addToBackStack(null);  // Tuỳ chọn: Thêm vào ngăn xếp để theo dõi lịch sử điều hướng fragment
                    transaction.commit();
                }
            }

        });
        int trangThai = thongtinoder.getTrangThai();
        if (trangThai == 1) {
            tvTrangThai.setText("Đã thanh toán");
            maOder.setText(String.valueOf(thongtinoder.getMaOder()));
            maBn.setText(String.valueOf(thongtinoder.getMaBn()));
            tongTien.setText(String.valueOf(thongtinoder.getTongTien()));
        } else {
            // Nếu trạng thái không phải 1, ẩn convertView
            convertView.setVisibility(View.GONE);
        }


//        maOder.setText(String.valueOf(thongtinoder.getMaOder()));
//        maBn.setText(String.valueOf(thongtinoder.getMaBn()));
//        tongTien.setText(String.valueOf(thongtinoder.getTongtien()));
        // ... (các phần khác không thay đổi)

        return convertView;
    }

}
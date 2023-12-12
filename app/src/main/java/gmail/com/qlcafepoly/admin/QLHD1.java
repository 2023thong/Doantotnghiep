package gmail.com.qlcafepoly.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import gmail.com.qlcafepoly.R;

import gmail.com.qlcafepoly.nhanvien.PayFragment;
import gmail.com.qlcafepoly.nhanvien.Thongtinoder;

public class QLHD1 extends BaseAdapter {
    private List<Thongtinoder> ttoder;
    private List<Thongtinoder> listPay;


    private LayoutInflater inflater;
    private Context context;


    public QLHD1(Context context, List<Thongtinoder> ttoder) {
        this.ttoder = ttoder;
        this.context = context;
        this.listPay = listPay;
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
            public void onClick(View v) {
                int maOder = thongtinoder.getMaOder();
                ((QuanLyHoaDon) context).btnxemdanhsachban(maOder);
            }
        });
        int trangThai = thongtinoder.getTrangThai();
        if (trangThai == 1) {
            tvTrangThai.setText("Đã thanh toán");
            maOder.setText(String.valueOf(thongtinoder.getMaOder()));
            maBn.setText(String.valueOf(thongtinoder.getMaBn()));
            String formattedTongTien = formatCurrency(thongtinoder.getTongTien());
            tongTien.setText(formattedTongTien);
            ngay.setText(String.valueOf(thongtinoder.getNgay()));
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
    private String formatCurrency(int amount) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        return numberFormat.format(amount);
    }
    public void setListPay(List<Thongtinoder> listPay) {
        this.listPay = listPay;
        notifyDataSetChanged();
    }

}

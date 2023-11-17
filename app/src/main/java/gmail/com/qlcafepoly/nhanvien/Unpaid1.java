package gmail.com.qlcafepoly.nhanvien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import gmail.com.qlcafepoly.R;

public class Unpaid1 extends BaseAdapter {
    private List<Thongtinoder> ttoder;
    private LayoutInflater inflater;
    private Context context;

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



        int trangThai = thongtinoder.getTrangThai();
        if (trangThai == 0) {


            // Gán giá trị cho các TextView
            maOder.setText(String.valueOf(thongtinoder.getMaOder()));
            maBn.setText(String.valueOf(thongtinoder.getMaBn()));
            tongTien.setText(String.valueOf(thongtinoder.getTongTien()));
        }
//        if (trangThai == 1){
//
//            maOder.setText(String.valueOf(thongtinoder.getMaOder()));
//            maBn.setText(String.valueOf(thongtinoder.getMaBn()));
//            tongTien.setText(String.valueOf(thongtinoder.getTongtien()));
//
//        }





//        int trangThai = thongtinoder.getTrangthai();
//        if (trangThai == 0) {
//            convertView.setVisibility(View.VISIBLE);
//        }





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
                // Xử lý khi nhấn vào btnThanhToan

                if(context instanceof Unpaid){
                    ((Unpaid) context).Dangnhap(String.valueOf(thongtinoder.getMaOder()), "1");

                }


            }
        });


        return convertView;
    }


}
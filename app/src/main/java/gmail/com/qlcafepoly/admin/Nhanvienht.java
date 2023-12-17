package gmail.com.qlcafepoly.admin;

import static gmail.com.qlcafepoly.Database.Constants.BASE_URL;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import gmail.com.qlcafepoly.R;

public class Nhanvienht extends BaseAdapter {
    private List<User> usernv;
    private LayoutInflater inflater;
    private ImageView editNV,deleteNV;
    EditText edMaNV,edTenNV,edTenDN,edMatKhau,edSdt,edDiaChi,edChucVu;


    public Nhanvienht(Context context, List<User> usernv) {
        this.usernv = usernv;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return usernv.size();
    }

    @Override
    public Object getItem(int position) {
        return usernv.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_item_nhan_vien, parent, false);
        }
        User user = usernv.get(position);

        TextView tvTennv = convertView.findViewById(R.id.tvTenNv);
        TextView tvSdt = convertView.findViewById(R.id.tvSdt);
        TextView tvDiachi = convertView.findViewById(R.id.tvDiaChi);

        tvTennv.setText("Tên : "+user.getTenNv());
        tvSdt.setText("SDT: "+String.valueOf(user.getSdt()));
        tvDiachi.setText("Địa chỉ: "+user.getDiachi());
        editNV = convertView.findViewById(R.id.icEditNV);
        editNV.setOnClickListener(view -> {
            Intent intent2 = new Intent(view.getContext(),ItemThongTinNV.class);
            intent2.putExtra("DULIEUNV", user.getMaNv());
            intent2.putExtra("DULIEUNV_TenNv", user.getTenNv());
            intent2.putExtra("DULIEUNV_TenDn", user.getTenDn());
            intent2.putExtra("DULIEUNV_Matkhau", user.getMatkhau());
            intent2.putExtra("DULIEUNV_Sdt", user.getSdt());
            intent2.putExtra("DULIEUNV_Diachi", user.getDiachi());
            intent2.putExtra("DULIEUNV_Chucvu", String.valueOf(user.getChucvu()));
            view.getContext().startActivity(intent2);
        });
        String imageUrl = BASE_URL + "duantotnghiep/layhinhanh.php?MaNv=" + user.getMaNv();
        RequestQueue requestQueue = Volley.newRequestQueue(convertView.getContext());
        View finalConvertView = convertView;
        ImageRequest imageRequest = new ImageRequest(
                imageUrl,
                new com.android.volley.Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        ImageView imageView = finalConvertView.findViewById(R.id.imgthemanhnhanvien1);
                        imageView.setImageBitmap(response);
                    }
                },
                0, 0,
                null,
                Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(finalConvertView.getContext(), "Thêm Avatar", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(imageRequest);

        return convertView;
    }

    public void clear() {
    }

    public void addAll(List<User> ketQuaTimKiem) {
    }
}
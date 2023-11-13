package gmail.com.qlcafepoly.admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.dangnhap.Danhnhap;
import gmail.com.qlcafepoly.dangnhap.DoiMatKhau;


public class CanhanFragment extends Fragment {
    ConstraintLayout tvttcn;
    ConstraintLayout layout, doimk;
    TextView textView;
    ImageView view1;





    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_canhan, container, false);
        view1 = view.findViewById(R.id.imageView21);

        chitiet();

        tvttcn = view.findViewById(R.id.tvTtcn);
        layout = view.findViewById(R.id.dangxuat);
        tvttcn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Thonhtintaikhoan.class);
                startActivity(intent);
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(view);
            }
        });
        doimk = view.findViewById(R.id.doimatkhau);
        doimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), DoiMatKhau.class));
            }
        });
        textView = view.findViewById(R.id.tvTen);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("thong", Context.MODE_PRIVATE);

        String Tennv = sharedPreferences.getString("TenNv", "");
        String Manv = sharedPreferences.getString("Manv", "");

        textView.setText(Tennv);

        String imageUrl = "http://192.168.1.78:8080/duantotnghiep/layhinhanh.php?MaNv=" +Manv;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        ImageRequest imageRequest = new ImageRequest(
                imageUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        ImageView imageView = view.findViewById(R.id.imageView20);

                        imageView.setImageBitmap(response);
                    }
                },
                0, 0,
                null,
                Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Thêm Avatar", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(imageRequest);




        return view;
    }


    public void logout(View view) {
        // Xóa trạng thái đăng nhập
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();

        // Điều hướng đến màn hình đăng nhập hoặc màn hình khác tùy ý
        Intent intent = new Intent(getActivity(), Danhnhap.class);
        startActivity(intent);
        getActivity().finish();




    }
    public void chitiet(){
        // Tạo PopupMenu và liên kết nó với ImageView
        PopupMenu popupMenu = new PopupMenu(getContext(), view1);
        popupMenu.getMenuInflater().inflate(R.menu.menucanhan, popupMenu.getMenu());

        // Xử lý sự kiện khi một mục trong PopupMenu được chọn
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.hotro){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    builder.setTitle("Hỗ Trợ")
                            .setMessage("Số điện thoại: 0385555914" +"\n\n"
                                       +"Zalo: 0385555914"
                            )
                            .setCancelable(true)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })

                            .show();







                }
                if(item.getItemId() == R.id.phienban){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    builder.setTitle("Phiên bản đang sử dụng: VN2023.0.1")
                            
                            .setCancelable(true)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })

                            .show();
                }

                return true;

                        // Xử lý tùy chọn xóa chi tiết ở đây


            }
        });

        // Hiển thị PopupMenu khi ImageView được nhấn
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });
    }


}
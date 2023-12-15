package gmail.com.qlcafepoly.admin;

import static gmail.com.qlcafepoly.Database.Constants.BASE_URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import com.squareup.picasso.Callback;

import gmail.com.qlcafepoly.R;

public class Hanghoaht extends BaseAdapter {

    ThongTinHangNhap thongTinHangNhap;
    private List<User1> users;
    private LayoutInflater inflater;
    private Context context;

    AlertDialog.Builder builder;
    ImageView imgkho;

    public Hanghoaht(Context context, List<User1> users) {
        this.context = context;
        this.users = users;

    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.hienthikhohang, parent, false);
        }
        User1 user1 = users.get(position);

        TextView tvMahh = convertView.findViewById(R.id.tvMahh1);
        TextView tvMancc = convertView.findViewById(R.id.ma);
        TextView tvTenhh = convertView.findViewById(R.id.tvTensp);
        TextView tvGiatien = convertView.findViewById(R.id.tvGiatien);
        ImageView imgXoa = convertView.findViewById(R.id.btnXoahanghoa);
        imgkho = convertView.findViewById(R.id.imganhkho1);


        tvMahh.setText(user1.getMaHH());
        tvMancc.setText(user1.getSoluong());
        tvTenhh.setText(user1.getTenLh());
        tvGiatien.setText(formatCurrency(Double.parseDouble(user1.getGiaSp())));

        String imageUrl = BASE_URL + "duantotnghiep/layanhkho.php?MaHH=" + user1.getMaHH();
        imgkho.setTag(imageUrl);
        Picasso.get().invalidate(imageUrl);
        Picasso.get()
                .load(imageUrl)
                .into(imgkho, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("Picasso", "Image loaded successfully");
                    }
                    @Override
                    public void onError(Exception e) {
                        Log.e("Picasso", "Error loading image: " + e.getMessage());
                    }
                });
        SharedPreferences sharedPreferences = convertView.getContext().getSharedPreferences("oder0", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("TenDu", user1.getTenHh());
        editor.putString("SoLuong", user1.getSoluong());
        editor.apply();


        PopupMenu popupMenu = new PopupMenu(context.getApplicationContext(), imgXoa);
        popupMenu.getMenuInflater().inflate(R.menu.menuchitiet, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.xoa) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context); // Tạo đối tượng AlertDialog.Builder

                    builder.setTitle("Thông Báo Xóa Hàng Hóa")
                            .setMessage("Bạn muốn xóa mã hàng hóa " + user1.getMaHH() + " này !")
                            .setCancelable(true)
                            .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (context instanceof ThongTinHangNhap) {
                                        ((ThongTinHangNhap) context).deleteItem(user1.getMaHH());
                                    }
                                }
                            })
                            .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .show();

                }
                if(item.getItemId() == R.id.sua){
                    Intent intent = new Intent(Hanghoaht.this.context, Suathongtinhh.class);
                    intent.putExtra("DULIEU", user1.getMaHH());
                    intent.putExtra("DULIEU_MaNcc", user1.getMaNcc());
                    intent.putExtra("DULIEU_MaLh", user1.getTenLh());
                    intent.putExtra("DULIEU_TenHh", user1.getTenHh());
                    intent.putExtra("DULIEU_GiaSp", user1.getGiaSp());
                    intent.putExtra("DULIEU_Ghichu", user1.getGhichu());
                    intent.putExtra("DULIEU_Soluong", user1.getSoluong());
                    Hanghoaht.this.context.startActivity(intent);

                }
                if(item.getItemId() == R.id.chitiet){
                    String Mahh = user1.getMaHH();
                    String Mancc = user1.getMaNcc();
                    String TeLh = user1.getTenLh();
                    String Tenhh = user1.getTenHh();
                    String Giatien = user1.getGiaSp();
                    String Ghichu = user1.getGhichu();
                    String Soluong = user1.getSoluong();


                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View dialogView = inflater.inflate(R.layout.chitiet, null);



                    final TextView mahh = dialogView.findViewById(R.id.tvMahhct);
                    final TextView maNcc = dialogView.findViewById(R.id.tvManccct);
                    final TextView TenLh = dialogView.findViewById(R.id.tvTenlhct);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final TextView tenhh1 = dialogView.findViewById(R.id.tvTenhhchitiet);
                    final TextView giaTien = dialogView.findViewById(R.id.tvGiact);
                    final TextView soluong = dialogView.findViewById(R.id.tvSoluongct);
                    final TextView ghichu = dialogView.findViewById(R.id.tvGhichuct);

                    mahh.setText(user1.getMaHH());
                    maNcc.setText(user1.getMaNcc());
                    TenLh.setText(user1.getTenLh());
                    tenhh1.setText(user1.getTenHh());
                    giaTien.setText(user1.getGiaSp());
                    soluong.setText(user1.getSoluong());
                    ghichu.setText(user1.getGhichu());

                    builder.setView(dialogView);

                    builder.setTitle("Thông Tin Chi Tiết")
                            .setPositiveButton("Xong", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    dialogInterface.cancel();
                                }
                            })
                            .show();

            }
                return true;

            }
        });

        imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });


        return convertView;
    }
    private String formatCurrency(double value) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(value);
    }


}

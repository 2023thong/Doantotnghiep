package gmail.com.qlcafepoly.admin;

import static gmail.com.qlcafepoly.Database.Constants.BASE_URL;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import gmail.com.qlcafepoly.R;

public class Themncc extends BaseAdapter {


    private List<User2> users;
    private LayoutInflater inflater;
    private Context context;

    AlertDialog.Builder builder;

    public Themncc(Context context, List<User2> users) {
        this.context=context;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.hienthincc, parent, false);
        }

        User2 user1 = users.get(position);

        TextView tvMancc = convertView.findViewById(R.id.tvManccht);
        TextView tvTencc = convertView.findViewById(R.id.tvTennccht);
        TextView tvDiachi = convertView.findViewById(R.id.tvDiachiht);
        TextView tvSDt = convertView.findViewById(R.id.tvSdtht);
        ImageView view1 = convertView.findViewById(R.id.imgSuancc);
        ImageView xoa = convertView.findViewById(R.id.imgXoancc);
        ImageView anhncca = convertView.findViewById(R.id.anhncc);





        tvMancc.setText(user1.getMaNcc());
        tvTencc.setText(user1.getTenNcc());
        tvDiachi.setText(user1.getDiachi());
        tvSDt.setText((user1.getSdt()));

        String imageUrl = BASE_URL + "duantotnghiep/layanhncc.php?MaNcc=" + user1.getMaNcc();
        anhncca.setTag(imageUrl);
        Picasso.get().invalidate(imageUrl);
        Picasso.get()
                .load(imageUrl)
                .into(anhncca, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("Picasso", "Image loaded successfully");
                    }
                    @Override
                    public void onError(Exception e) {
                        Log.e("Picasso", "Error loading image: " + e.getMessage());
                    }
                });

        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SuaNcc.class);
                intent.putExtra("DULIEU", user1.getMaNcc());
                intent.putExtra("DULIEU_Tenncc", user1.getTenNcc());
                intent.putExtra("DULIEU_Diachi", user1.getDiachi());
                intent.putExtra("DULIEU_sdt", user1.getSdt());

                view.getContext().startActivity(intent);
            }
        });
        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context); // Tạo đối tượng AlertDialog.Builder

                builder.setTitle("Thông Báo Xóa Nhà Cung Cấp")
                        .setMessage("Bạn muốn xóa mã nhà cung cấp " + user1.getMaNcc() + " này !")
                        .setCancelable(true)
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (context instanceof ThemNhaCungCap) {
                                    ((ThemNhaCungCap) context).deleteNcc(user1.getMaNcc());
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
        });








        return convertView;
    }
    }


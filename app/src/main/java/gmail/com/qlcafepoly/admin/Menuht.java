package gmail.com.qlcafepoly.admin;

import static androidx.core.content.ContextCompat.startActivity;

import static java.security.AccessController.getContext;

import static gmail.com.qlcafepoly.Database.Constants.BASE_URL;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.nhanvien.OderDu;

public class Menuht extends BaseAdapter {
    private List<Menu> menudu;
    private LayoutInflater inflater;
    private ImageView view1;
    public Menuht(Context context, List<Menu> menudu) {
        this.menudu = menudu;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return menudu.size();
    }

    @Override
    public Object getItem(int position) {
        return menudu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_item_do_uong, parent, false);

        }
        Menu menu = menudu.get(position);

        TextView tvMaMn = convertView.findViewById(R.id.tvMaMn);
        TextView tvTenLh = convertView.findViewById(R.id.tvTenLh);
        TextView tvGiatien = convertView.findViewById(R.id.tvGiatien);
//      TextView tvLh = convertView.findViewById(R.id.tvLh);
        tvMaMn.setText("Mã: "+menu.getMaMn());
        tvTenLh.setText(menu.getTenDu());
        String formattedPrice = formatPrice(menu.getGiatien());
        tvGiatien.setText(formattedPrice);
//        tvLh.setText("Loại: "+menu.getTenLh());


        view1 = convertView.findViewById(R.id.icEdit);
        view1.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ItemThongTinDU.class);
            intent.putExtra("DULIEUDU", menu.getMaMn());
            intent.putExtra("DULIEUDU_TenDu", menu.getTenDu());
            intent.putExtra("DULIEUDU_Giatien", String.valueOf(menu.getGiatien()));
            intent.putExtra("DULIEUDU_TenLh", menu.getTenLh());
            view.getContext().startActivity(intent);
        });

        String imageUrl = BASE_URL +"duantotnghiep/layhinhanhmenu.php?MaMn=" + menu.getMaMn();
        RequestQueue requestQueue = Volley.newRequestQueue(convertView.getContext());
        View finalConvertView = convertView;
        ImageRequest imageRequest = new ImageRequest(
                imageUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        ImageView imageView = finalConvertView.findViewById(R.id.imganhmenu);

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
    private String formatPrice(double price) {
        // Use NumberFormat to format the price with separated thousands
        NumberFormat numberFormat = new DecimalFormat("#,###");

        // Format the price as a string
        String formattedPrice = numberFormat.format(price) + " vnđ";

        return formattedPrice;
    }
}
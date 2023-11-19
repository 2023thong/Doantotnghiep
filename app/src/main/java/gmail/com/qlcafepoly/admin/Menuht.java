package gmail.com.qlcafepoly.admin;

import static androidx.core.content.ContextCompat.startActivity;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        tvMaMn.setText("Mã: "+menu.getMaMn());
        tvTenLh.setText(menu.getTenDu());
        tvGiatien.setText("Giá tiền: "+String.valueOf(menu.getGiatien())+" vnđ");


        view1 = convertView.findViewById(R.id.icEdit);
        view1.setOnClickListener(view -> {
           Intent intent = new Intent(view.getContext(), ItemThongTinDU.class);
            intent.putExtra("DULIEUDU", menu.getMaMn());
            intent.putExtra("DULIEUDU_TenDu", menu.getTenDu());
            intent.putExtra("DULIEUDU_Giatien", String.valueOf(menu.getGiatien()));
           view.getContext().startActivity(intent);
       });

        return convertView;
    }
    }

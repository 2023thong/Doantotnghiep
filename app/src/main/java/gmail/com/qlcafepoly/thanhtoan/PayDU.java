package gmail.com.qlcafepoly.thanhtoan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.admin.User1;

public class PayDU extends BaseAdapter {
    private List<Menu1> menu2;
    private LayoutInflater inflater;

    public PayDU(Context context, List<Menu1> menu2) {
        this.menu2 = menu2;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return menu2.size();
    }

    @Override
    public Object getItem(int position) {
        return menu2.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_menupay, parent, false);


        }
        Menu1 menu1 = menu2.get(position);

        TextView tv_DoUong = convertView.findViewById(R.id.tv_DoUong);
        TextView tvMoney = convertView.findViewById(R.id.tvMoney);
        TextView tv_Soluong = convertView.findViewById(R.id.tv_Soluong);
        TextView tvmb = convertView.findViewById(R.id.tvMabn);

        tv_DoUong.setText(menu1.getTenLh());
        tvMoney.setText((String.valueOf(menu1.getGiatien())));
        tv_Soluong.setText(String.valueOf(menu1.getSoluong()));


        return convertView;
    }
}


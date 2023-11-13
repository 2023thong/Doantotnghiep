package gmail.com.qlcafepoly.nhanvien;

import static android.content.Intent.getIntent;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import gmail.com.qlcafepoly.R;
import gmail.com.qlcafepoly.model.Ban;

public class Table1 extends BaseAdapter {
    private List<Ban> ban1;
    private LayoutInflater inflater1;

    public Table1(Context context, List<Ban> ban1){
        this.ban1 = ban1;
        inflater1 = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return ban1.size();
    }
    public Object getItem(int position){
        return ban1.get(position);
    }
    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = inflater1.inflate(R.layout.item_table, parent, false);
        }
        Ban ban = ban1.get(position);

        TextView txtTenbn = convertView.findViewById(R.id.txtTenbn);
        TextView txtMabn = convertView.findViewById(R.id.txtMabn);
        TextView txtTthai = convertView.findViewById(R.id.txtTthai);

        txtTenbn.setText(ban.getTenBan());
        txtMabn.setText(ban.getMaBn());


        if (ban.getTrangthai().equals("1")) {
            txtTthai.setText("Trống");
        } else if (ban.getTrangthai().equals("2")) {
            txtTthai.setText("Đầy");
        }






        return convertView;
    }
}

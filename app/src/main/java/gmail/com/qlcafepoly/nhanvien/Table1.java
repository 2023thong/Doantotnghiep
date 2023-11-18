package gmail.com.qlcafepoly.nhanvien;




import static android.content.Intent.getIntent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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


    @SuppressLint("NewApi")
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = inflater1.inflate(R.layout.item_table, parent, false);
        }
        Ban ban = ban1.get(position);

        TextView txtTenbn = convertView.findViewById(R.id.txtTenbn);
        TextView txtMabn = convertView.findViewById(R.id.txtMabn);
        TextView txtTthai = convertView.findViewById(R.id.txtTthai);


        Button button = convertView.findViewById(R.id.themOrder);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), OderDu.class);
                intent.putExtra("MAODER", ban.getMaBn());
                view.getContext().startActivity(intent);
            }
        });

        txtTenbn.setText(ban.getTenBan());
        txtMabn.setText(ban.getMaBn());


        if (ban.getTrangthai().equals("1")) {
            txtTthai.setText("Bàn đang trống");

            txtTthai.setTextColor(convertView.getContext().getColor(android.R.color.holo_orange_light));
        } else if (ban.getTrangthai().equals("2")) {
            txtTthai.setText("Bàn đã oder");

            txtTthai.setTextColor(convertView.getContext().getColor(android.R.color.holo_red_light));
        }


        return convertView;
    }
}


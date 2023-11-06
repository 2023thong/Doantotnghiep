package gmail.com.qlcafepoly.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import gmail.com.qlcafepoly.R;


public class ItemDoUong extends AppCompatActivity {
    ImageView icEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_do_uong);
        icEdit = findViewById(R.id.icEdit);
        icEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemDoUong.this, ItemThongTinDU.class);
                startActivity(intent);
            }
        });

    }

}
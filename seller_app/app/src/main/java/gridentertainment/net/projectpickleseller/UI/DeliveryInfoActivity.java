package gridentertainment.net.projectpickleseller.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import gridentertainment.net.projectpickleseller.R;

public class DeliveryInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_info);

        final EditText name = findViewById(R.id.ed_dl_name);
        final EditText phone = findViewById(R.id.ed_dl_phone);
        final EditText vh = findViewById(R.id.ed_dl_vh);

        Button deliver = findViewById(R.id.dl_btn_deliver);
        deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent output = new Intent();
                output.putExtra("dl_name", name.getText().toString());
                output.putExtra("dl_phone", phone.getText().toString());
                output.putExtra("dl_vh", vh.getText().toString());
                setResult(RESULT_OK, output);
                finish();
            }
        });

    }
}

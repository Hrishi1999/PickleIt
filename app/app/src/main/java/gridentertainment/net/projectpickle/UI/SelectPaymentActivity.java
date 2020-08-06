package gridentertainment.net.projectpickle.UI;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import gridentertainment.net.projectpickle.Models.OrderItem;
import gridentertainment.net.projectpickle.R;
import gridentertainment.net.projectpickle.Utils.GRadioGroup;

public class SelectPaymentActivity extends AppCompatActivity {
        private String mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_payment);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='#ff5722'>Select mode of payment</font>"));

        Button btn = findViewById(R.id.btn_order2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                RadioButton rb1 = findViewById(R.id.rb_cod);
                RadioButton rb2 = findViewById(R.id.rb_paytm);
                // GRadioGroup gr = new GRadioGroup(rb1, rb2);
                if (rb1.isChecked())
                {
                    mode = "0";
                }
                if (rb2.isChecked())
                {
                    mode = "1";
                }
                intent.putExtra("payment_mode", mode);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        //OrderItem orderItem = intent.getParcelableExtra("object");
        /*String quantity = intent.getStringExtra("quantity");
        String order_id = intent.getStringExtra("order_id");
        String total_price = intent.getStringExtra("total_price");
        String seller_ID = intent.getStringExtra("seller_ID");
        String status = intent.getStringExtra("status");

        //String sellerID = intent.getStringExtra("sellerID");
        //String orderID = intent.getStringExtra("orderID");

        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(quantity);
        orderItem.setOrderID(order_id);
        orderItem.setPrice(total_price);
        orderItem.setSellerID(seller_ID);
        orderItem.setStatus(status);

        ArrayList<OrderItem> orderItemList = intent.getParcelableArrayListExtra("array");
        //String total_price = intent.getStringExtra("total");
        final FirebaseDatabase database;
        database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference2 = database.getReference("sellers");
        final DatabaseReference databaseReference3 = database.getReference("consumers");

        databaseReference2.child(seller_ID).child("orders").child(order_id)
                .setValue(orderItem);

        for(final OrderItem orderItem1 : orderItemList)
        {
            Random random = new Random();
            int itm_id2 = random.nextInt(10000000);

            HashMap<String, Object> misc = new HashMap<>();
            misc.put("sellerID", orderItem1.getSellerID());
            misc.put("status", "Pending Confirmation");
            misc.put("order_total", total_price);
            intent.putExtra("misc", misc);

            databaseReference3.child(order_id).child("items").child(String.valueOf(itm_id2))
                            .setValue(orderItem1);
                    databaseReference3.child(order_id).updateChildren(misc);
        }*/

    }
}

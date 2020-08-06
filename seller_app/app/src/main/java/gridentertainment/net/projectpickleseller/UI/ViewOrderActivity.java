package gridentertainment.net.projectpickleseller.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gridentertainment.net.projectpickleseller.Adapters.OrderRecAdapter;
import gridentertainment.net.projectpickleseller.Models.OrderItem;
import gridentertainment.net.projectpickleseller.Models.Profile;
import gridentertainment.net.projectpickleseller.R;

public class ViewOrderActivity extends AppCompatActivity {

    private List<OrderItem> orderItemList;
    private OrderRecAdapter orderRecAdapter;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference1;
    private DatabaseReference databaseReference;
    private String orderID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        orderID = getIntent().getExtras().getString("orderID");
        final String userID = getIntent().getExtras().getString("userID");

        database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference("consumers").child(userID).child("previous_orders")
                .child(orderID);

        databaseReference1 = database.getReference("consumers").child(userID);

        final TextView pDetails = findViewById(R.id.tv_person_details);

        final RecyclerView recyclerView = findViewById(R.id.recyclerViewRec);

        databaseReference.child("items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderItemList = new ArrayList<>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    OrderItem orderItem = dataSnapshot1.getValue(OrderItem.class);
                    OrderItem listdata = new OrderItem();

                    String name = orderItem.getName();
                    String price = orderItem.getPrice();
                    String quantity = orderItem.getQuantity();

                    listdata.setName(name);
                    listdata.setPrice(price);
                    listdata.setQuantity(quantity);
                    orderItemList.add(listdata);
                }

                orderRecAdapter = new OrderRecAdapter(orderItemList, ViewOrderActivity.this);
                RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(ViewOrderActivity.this);
                recyclerView.setLayoutManager(layoutmanager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(orderRecAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Profile profile = dataSnapshot.getValue(Profile.class);
                String name = profile.getName();
                String phone = profile.getPhone();
                String address = profile.getAddress();
                pDetails.setText(name + ", " + phone + "\n" + address);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        final RadioButton receive = findViewById(R.id.radio_rec);
        final RadioButton deliver = findViewById(R.id.radio_del);
        final RadioButton cancel = findViewById(R.id.radio_can);

        deliver.setEnabled(false);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.clearCheck();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == receive.getId()) {
                    deliver.setEnabled(true);
                    HashMap<String, Object> status = new HashMap<>();
                    status.put("status", "Order Received!");
                    databaseReference.updateChildren(status);
                }
                if (checkedId == deliver.getId()) {
                    receive.setEnabled(false);
                    Intent intent = new Intent(getApplication(), DeliveryInfoActivity.class);
                    startActivityForResult(intent, 1);
                }
                if (checkedId == cancel.getId()) {
                    receive.setEnabled(false);
                    deliver.setEnabled(false);
                    HashMap<String, Object> status = new HashMap<>();
                    status.put("status", "Cancelled by seller!");
                    databaseReference.updateChildren(status);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                String name = data.getStringExtra("dl_name");
                String phone = data.getStringExtra("dl_phone");
                String vh = data.getStringExtra("dl_vh");

                HashMap<String, Object> delivery_info = new HashMap<>();
                delivery_info.put("name", name);
                delivery_info.put("phone", phone);
                delivery_info.put("vh", vh);

                databaseReference1.child("previous_orders")
                        .child(orderID)
                        .child("delivery_info")
                        .updateChildren(delivery_info);

                HashMap<String, Object> status = new HashMap<>();
                status.put("status", "On the way!");

                databaseReference.updateChildren(status);

            }
        }
    }

    public void onRadioButtonClicked(View view) {
    }
}

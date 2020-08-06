package gridentertainment.net.projectpickle.UI;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import gridentertainment.net.projectpickle.Adapters.CartAdapter;
import gridentertainment.net.projectpickle.Models.OrderItem;
import gridentertainment.net.projectpickle.R;

public class CartActivity extends AppCompatActivity {

    List<OrderItem> orderItemList;
    RecyclerView recyclerView;
    CartAdapter orderAdapter;
    private TextView sub_total;
    private TextView total;
    private DatabaseReference databaseReference2;
    private DatabaseReference databaseReference3;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    private final String[] seller_id = new String[1];
    private String userID;

    public CartActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='#ff5722'>Cart</font>"));

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = currentFirebaseUser.getUid();

        final FirebaseDatabase database;
        database = FirebaseDatabase.getInstance();

        final DatabaseReference databaseReference = database.getReference("consumers").child(userID).child("cart");
        databaseReference2 = database.getReference("sellers");
        databaseReference3 = database.getReference("consumers")
                .child(userID).child("previous_orders");
        //final DatabaseReference databaseReference4 = databaseReference3;
        sub_total = findViewById(R.id.tv_sub);
        total = findViewById(R.id.tv_total);
        final Button order = findViewById(R.id.btn_order);

        //TextView delivery = findViewById(R.id.tv_dc);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CartActivity.this, SelectPaymentActivity.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);

            }
        });

        //TODO : Custom Delivery fee, remove tax
        recyclerView = findViewById(R.id.cartRecyclerView);

        final int[] subtotal = {0};

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderItemList = new ArrayList<>();

                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                    OrderItem orderItem = dataSnapshot1.getValue(OrderItem.class);
                    OrderItem listdata = new OrderItem();

                    String name = orderItem.getName();
                    String price = orderItem.getPrice();
                    String tags = orderItem.getTags();
                    String image = orderItem.getImage();
                    String quantity = orderItem.getQuantity();
                    seller_id[0] = orderItem.getSellerID();

                    subtotal[0] = (subtotal[0] + Integer.parseInt(price)) * Integer.parseInt(quantity);

                    listdata.setName(name);
                    listdata.setPrice(price);
                    listdata.setTags(tags);
                    listdata.setImage(image);
                    listdata.setQuantity(quantity);
                    listdata.setSellerID(seller_id[0]);
                    orderItemList.add(listdata);
                }

                orderAdapter = new CartAdapter(orderItemList, CartActivity.this);
                RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(CartActivity.this);
                recyclerView.setLayoutManager(layoutmanager);
                recyclerView.setItemAnimator( new DefaultItemAnimator());
                recyclerView.setAdapter(orderAdapter);
                sub_total.setText(String.valueOf(subtotal[0]));
                total.setText(String.valueOf(subtotal[0] + 10)); //delivery fee
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                String returnString = data.getStringExtra("payment_mode");

                if(returnString.equals("0"))
                {
                    Random rand = new Random();
                    int itm_id = rand.nextInt(10000000);

                    final String order_id = String.valueOf(itm_id);
                    String total_quanitity = String.valueOf(orderItemList.size());
                    String total_price = total.getText().toString();
                    String status = "Order Received";
                    String seller_ID  = seller_id[0];

                    final OrderItem orderItem = new OrderItem();

                    orderItem.setOrderID(order_id);
                    orderItem.setQuantity(total_quanitity);
                    orderItem.setStatus(status);
                    orderItem.setPrice(total_price);
                    orderItem.setUserID(userID);
                    orderItem.setSellerID(seller_ID);

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

                        databaseReference3.child(order_id).child("items").child(String.valueOf(itm_id2))
                                .setValue(orderItem1);
                        databaseReference3.child(order_id).updateChildren(misc);
                    }
                    // databaseReference.setValue(null);
                    Toast.makeText(CartActivity.this, "Order Placed Successfully!", Toast.LENGTH_SHORT).show();
                }
                if(returnString.equals("1"))
                {
                    Toast.makeText(this, "Paytm not currently supported", Toast.LENGTH_SHORT).show();
                        //paytm.exec()
                }
            }
        }
    }

}

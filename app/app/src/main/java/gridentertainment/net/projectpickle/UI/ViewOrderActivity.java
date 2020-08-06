package gridentertainment.net.projectpickle.UI;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.IndicatorStayLayout;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import gridentertainment.net.projectpickle.Adapters.OrderRecAdapter;
import gridentertainment.net.projectpickle.Models.OrderItem;
import gridentertainment.net.projectpickle.Models.SellerItem;
import gridentertainment.net.projectpickle.R;

public class ViewOrderActivity extends AppCompatActivity implements RatingDialogListener {

    private List<OrderItem> orderItemList;
    private OrderRecAdapter orderRecAdapter;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private HashMap<String, Object> rate;
    private String sellerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='#ff5722'>Cart</font>"));

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = currentFirebaseUser.getUid();
        String orderID = getIntent().getExtras().getString("orderID");
        String total = getIntent().getExtras().getString("total");
        sellerID = getIntent().getExtras().getString("sellerID");
        String status = getIntent().getExtras().getString("status");

        database = FirebaseDatabase.getInstance();
        rate = new HashMap<>();
        final RecyclerView recyclerView = findViewById(R.id.recyclerViewOrRec);
        databaseReference = database.getReference("consumers");
        final TextView tv_name = findViewById(R.id.tv_name);
        final TextView tv_price = findViewById(R.id.view_total);

        tv_price.setText(total);

        DatabaseReference databaseReference1 = database.getReference("consumers")
                .child(userID)
                .child("previous_orders").child(orderID).child("delivery_info");

        DatabaseReference databaseReference2 = database.getReference("sellers").child(sellerID);
        DatabaseReference databaseReferenceStatus = database.getReference("sellers").child(sellerID)
                .child("orders")
                .child(orderID);

        final String[] statusX = new String[1];

        final TextView tv = findViewById(R.id.tv_deliv);

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                String phone = dataSnapshot.child("phone").getValue(String.class);
                String vh = dataSnapshot.child("vh").getValue(String.class);

                tv.setText("Delivered by " + name + ", " + phone + ", " + vh);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        databaseReferenceStatus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                OrderItem orderItem = dataSnapshot.getValue(OrderItem.class);
                statusX[0] = orderItem.getStatus();
                Toast.makeText(ViewOrderActivity.this, statusX[0], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        final String[] name = new String[1];

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SellerItem sellerItem = dataSnapshot.getValue(SellerItem.class);
                name[0] = sellerItem.getName();
                tv_name.setText(name[0]);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        databaseReference.child(userID).child("previous_orders").child(orderID).child("items").addValueEventListener(new ValueEventListener() {
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

        Button rate = findViewById(R.id.btn_rate);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        String str[] = new String[4];
        str[0] = "Pending";
        str[1] = "Order Received";
        str[2] = "On the way!";
        str[3] = "Delivered";
        IndicatorSeekBar indicatorSeekBar = findViewById(R.id.seek);
        indicatorSeekBar.customTickTexts(str);

        if (status.equals("Pending confirmation!"))
        {
            indicatorSeekBar.setProgress(1);
        }
        if (status.equals("Order Received"))
        {
            indicatorSeekBar.setProgress(2);
        }
        if (status.equals("On the way!"))
        {
            indicatorSeekBar.setProgress(3);
        }
        if (status.equals("Delivered!"))
        {
            indicatorSeekBar.setProgress(4);
        }
        //indicatorSeekBar.setEnabled(false);

    }

    private void showDialog() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText(R.string.submit_ord)
                .setNegativeButtonText(R.string.sub_cncl)
                .setNeutralButtonText(R.string.later)
                .setNoteDescriptions(Arrays.asList(getString(R.string.very_bad), getString(R.string.not_good)
                        , getString(R.string.quite), getString(R.string.vgood), getString(R.string.excell)))
                .setDefaultRating(2)
                .setTitle(R.string.rate_ord)
                .setDescription(R.string.wrt_feedback)
                .setCommentInputEnabled(true)
                .setDefaultComment("Great!")
                .setStarColor(R.color.colorAccent)
                .setNoteDescriptionTextColor(R.color.black)
                .setTitleTextColor(R.color.black)
                .setDescriptionTextColor(R.color.black)
                .setHint(R.string.write_cmt)
                .setHintTextColor(R.color.lgray)
                .setCommentTextColor(R.color.black)
                .setCommentBackgroundColor(R.color.colorPrimaryDark)
                .setWindowAnimation(R.style.MyDialogFadeAnimation)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .create(ViewOrderActivity.this)
                .show();
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int i, @NotNull String s) {

        /*rate.put("total_users", "1");
        rate.put("sum_ratings", "5");

        databaseReference.child(sellerID).child("ratings").updateChildren(rate);*/

    }
}

package gridentertainment.net.projectpickle.UI;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import gridentertainment.net.projectpickle.Adapters.ItemAdapter;
import gridentertainment.net.projectpickle.Adapters.SellerAdapter;
import gridentertainment.net.projectpickle.Models.OrderItem;
import gridentertainment.net.projectpickle.Models.SellerItem;
import gridentertainment.net.projectpickle.Models.SellersItems;
import gridentertainment.net.projectpickle.R;

public class ItemsActivity extends AppCompatActivity {

    List<SellersItems> sellersItemList;
    ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ff5722'>Items</font>"));

        final RecyclerView recyclerView;
        final FirebaseDatabase database;
        final DatabaseReference databaseReference;
        database = FirebaseDatabase.getInstance();

        Bundle bundle = getIntent().getExtras();
        final String uid = bundle.getString("UID");
        String name = bundle.getString("name");
        String phone = bundle.getString("phone");
        String address = bundle.getString("address");
        String image = bundle.getString("image");

        ImageView imageView = findViewById(R.id.imageView2);
        Picasso.get().load(image).into(imageView);

        TextView shop_name = findViewById(R.id.shop_name);
        TextView shop_phone = findViewById(R.id.shop_phone);
        TextView shop_address = findViewById(R.id.shop_address);

        shop_name.setText(name);
        shop_address.setText(address);
        shop_phone.setText(phone);

        databaseReference = database.getReference("sellers").child(uid).child("items");

        recyclerView = findViewById(R.id.recyclerViewItems);

        ImageView imageView1 = findViewById(R.id.imageView3);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ItemsActivity.this, CartActivity.class));
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sellersItemList = new ArrayList<>();

                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                    SellersItems sellersItem = dataSnapshot1.getValue(SellersItems.class);
                    SellersItems listdata = new SellersItems();

                    String name = sellersItem.getName();
                    String tag = sellersItem.getTags();
                    String op = sellersItem.getOriginal_price();
                    String price = sellersItem.getPrice();
                    String image = sellersItem.getImage();

                    final String[] quantity = {"0"};

                    listdata.setName(name);
                    listdata.setOriginal_price(op);
                    listdata.setPrice(price);
                    listdata.setImage(image);
                    listdata.setTags(tag);
                    listdata.setItemID(dataSnapshot1.getKey());
                    listdata.setQuantity(quantity[0]);

                    sellersItemList.add(listdata);
                }

                itemAdapter = new ItemAdapter(sellersItemList, ItemsActivity.this, uid);
                RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(ItemsActivity.this);
                recyclerView.setLayoutManager(layoutmanager);
                recyclerView.setItemAnimator( new DefaultItemAnimator());
                recyclerView.setAdapter(itemAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
}

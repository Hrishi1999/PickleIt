package gridentertainment.net.projectpickle.UI;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import gridentertainment.net.projectpickle.Adapters.PreviousOrderAdapter;
import gridentertainment.net.projectpickle.Adapters.SellerAdapter;
import gridentertainment.net.projectpickle.Models.OrderItem;
import gridentertainment.net.projectpickle.Models.SellerItem;
import gridentertainment.net.projectpickle.R;

public class PreviousOrderFragment extends Fragment {

    List<OrderItem> orderItemList;
    RecyclerView recyclerView;
    PreviousOrderAdapter previousOrderAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_previous_order, container, false);

        final FirebaseDatabase database;
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference;
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("consumers").child(userID)
        .child("previous_orders");

        recyclerView = rootView.findViewById(R.id.recyclerViewPr);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderItemList = new ArrayList<>();

                //TODO: Change OrderID with Name

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    //OrderItem orderItem = dataSnapshot1.getValue(OrderItem.class);
                    OrderItem listdata = new OrderItem();
                    String price = dataSnapshot1.child("order_total").getValue(String.class);
                    String quantity = "2";
                    String status = dataSnapshot1.child("status").getValue(String.class);
                    String sellerID = dataSnapshot1.child("sellerID").getValue(String.class);
                    String orderID = dataSnapshot1.getKey();

                    listdata.setQuantity(quantity);
                    listdata.setPrice(price);
                    listdata.setStatus(status);
                    listdata.setName("#"+orderID);
                    listdata.setOrderID(orderID);
                    listdata.setSellerID(sellerID);

                    orderItemList.add(listdata);
                }

                previousOrderAdapter = new PreviousOrderAdapter(orderItemList, getContext());
                RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutmanager);
                recyclerView.setItemAnimator( new DefaultItemAnimator());
                recyclerView.setAdapter(previousOrderAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return rootView;
    }

}

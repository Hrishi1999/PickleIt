package gridentertainment.net.projectpickleseller.UI;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import gridentertainment.net.projectpickleseller.Adapters.OrderAdapter;
import gridentertainment.net.projectpickleseller.Models.OrderItem;
import gridentertainment.net.projectpickleseller.R;

public class OrdersFragment extends Fragment {

    List<OrderItem> orderItemList;
    RecyclerView recyclerView;
    OrderAdapter orderAdapter;

    public OrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_orders, container, false);

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String[] userID_order = new String[1];
        final FirebaseDatabase database;
        DatabaseReference databaseReference;
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("sellers").child(userID).child("orders");

        final RecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewOrders);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderItemList = new ArrayList<>();

                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    OrderItem orderItem = dataSnapshot1.getValue(OrderItem.class);
                    OrderItem listdata = new OrderItem();

                    String id = orderItem.getOrderID();
                    String quantity = orderItem.getQuantity();
                    String price = orderItem.getPrice();
                    String status = orderItem.getStatus();
                    userID_order[0] = orderItem.getuserID();

                    listdata.setOrderID(id);
                    listdata.setQuantity(quantity);
                    listdata.setPrice(price);
                    listdata.setStatus(status);
                    listdata.setuserID(userID_order[0]);

                    orderItemList.add(listdata);
                }

                orderAdapter = new OrderAdapter(orderItemList, getContext());
                RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutmanager);
                recyclerView.setItemAnimator( new DefaultItemAnimator());
                recyclerView.setAdapter(orderAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        return rootView;
    }

}

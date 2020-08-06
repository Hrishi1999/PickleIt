package gridentertainment.net.projectpickleseller.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import gridentertainment.net.projectpickleseller.Adapters.ItemsAdapter;
import gridentertainment.net.projectpickleseller.Models.SellersItems;
import gridentertainment.net.projectpickleseller.R;

public class MainFragment extends Fragment {

    List<SellersItems> sellersItemsList;
    RecyclerView recyclerView;
    ItemsAdapter itemsAdapter;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = currentFirebaseUser.getUid();

        final FirebaseDatabase database;
        DatabaseReference databaseReference;
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("sellers").child(userID).child("items");

        recyclerView = rootView.findViewById(R.id.recylerViewItems);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sellersItemsList = new ArrayList<>();

                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                    SellersItems sellersItems = dataSnapshot1.getValue(SellersItems.class);
                    SellersItems listdata = new SellersItems();

                    String name = sellersItems.getName();
                    String price = sellersItems.getPrice();
                    String op = sellersItems.getOriginal_price();
                    String tag = sellersItems.getTags();
                    String itm_id = sellersItems.getItm_id();

                    listdata.setName(name);
                    listdata.setPrice(price);
                    listdata.setTags(tag);
                    listdata.setOriginal_price(op);
                    listdata.setItm_id(itm_id);
                    Toast.makeText(getContext(), itm_id, Toast.LENGTH_SHORT).show();
                    sellersItemsList.add(listdata);
                }

                itemsAdapter = new ItemsAdapter(sellersItemsList, getContext());
                RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutmanager);
                recyclerView.setItemAnimator( new DefaultItemAnimator());
                recyclerView.setAdapter(itemsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        FloatingActionButton addBtn = rootView.findViewById(R.id.btn_add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddItemActivity.class));
            }
        });
        return rootView;
    }


}

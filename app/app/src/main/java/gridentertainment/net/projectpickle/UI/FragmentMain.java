package gridentertainment.net.projectpickle.UI;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
import java.util.List;

import gridentertainment.net.projectpickle.Adapters.SellerAdapter;
import gridentertainment.net.projectpickle.Models.SellerItem;
import gridentertainment.net.projectpickle.R;


public class FragmentMain extends Fragment {

    List<SellerItem> sellerItemList;
    RecyclerView recyclerView;
    SellerAdapter sellerAdapter;
    private String scity;

    public FragmentMain() {
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
        final String userID = currentFirebaseUser.getUid();

        final FirebaseDatabase database;
        DatabaseReference databaseReference;
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("sellers");
        DatabaseReference databaseReference2 = database.getReference("consumers").child(userID);

        recyclerView = rootView.findViewById(R.id.recyclerView);

        ImageView cart = rootView.findViewById(R.id.imageViewCart);
        TextView welcome = rootView.findViewById(R.id.tv_welcome_itm);
        TextView msg = rootView.findViewById(R.id.tv_msg_itm);

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SellerItem sellerItem = dataSnapshot.getValue(SellerItem.class);
                scity = sellerItem.getCity();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sellerItemList = new ArrayList<>();

                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    SellerItem sellerItem = dataSnapshot1.getValue(SellerItem.class);
                    SellerItem listdata = new SellerItem();

                    String name = sellerItem.getName();
                    String ratings = sellerItem.getRatings();
                    String tags = sellerItem.getTags();
                    String image = sellerItem.getImage();
                    String address = sellerItem.getAddress();
                    String phone = sellerItem.getPhone();
                    String uid = dataSnapshot1.getKey();
                    String dFee = sellerItem.getdFee();
                    String city = sellerItem.getCity();

                    listdata.setName(name);
                    listdata.setRatings(ratings);
                    listdata.setTags(tags);
                    listdata.setImage(image);
                    listdata.setAddress(address);
                    listdata.setPhone(phone);
                    listdata.setUserID(uid);
                    listdata.setdFee(dFee);
                    listdata.setCity(city);

                    if(scity.equals(city))
                    {
                        sellerItemList.add(listdata);
                    }

                }

                sellerAdapter = new SellerAdapter(sellerItemList);
                RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutmanager);
                recyclerView.setItemAnimator( new DefaultItemAnimator());
                recyclerView.setAdapter(sellerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CartActivity.class));
            }
        });

        return rootView;
    }

}

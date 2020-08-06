package gridentertainment.net.projectpickle.UI;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import gridentertainment.net.projectpickle.Adapters.PreviousOrderAdapter;
import gridentertainment.net.projectpickle.Adapters.SearchAdapter;
import gridentertainment.net.projectpickle.Models.OrderItem;
import gridentertainment.net.projectpickle.Models.SearchItem;
import gridentertainment.net.projectpickle.Models.SellerItem;
import gridentertainment.net.projectpickle.R;

public class SearchFragment extends Fragment {

    List<SearchItem> searchItemList;
    RecyclerView recyclerView;
    SearchAdapter searchAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userID = currentFirebaseUser.getUid();
        final FirebaseDatabase database;
        database = FirebaseDatabase.getInstance();
        recyclerView = rootView.findViewById(R.id.recyclerViewSearch);

        EditText searchq = rootView.findViewById(R.id.ed_search);
        final String[] search_query = {searchq.getText().toString()};

        searchq.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)
                    search_query[0] = s.toString();
                DatabaseReference databaseReference = database.getReference("sellers");
                Query query = databaseReference.orderByChild("name").startAt(search_query[0]);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            searchItemList = new ArrayList<>();

                            for (DataSnapshot child: dataSnapshot.getChildren())
                            {
                                SearchItem searchItem = new SearchItem();
                                //String key = child.getKey();
                                String name = child.child("name").getValue(String.class);
                                String tags = child.child("tags").getValue(String.class);
                                String ratings = child.child("ratings").getValue(String.class);
                                String phone = child.child("phone").getValue(String.class);
                                String image = child.child("image").getValue(String.class);
                                String address = child.child("address").getValue(String.class);
                                String sellerID = child.getKey();

                                searchItem.setName(name);
                                searchItem.setRatings(ratings);
                                searchItem.setTags(tags);
                                searchItem.setSellerID(sellerID);
                                searchItem.setPhone(phone);
                                searchItem.setAddress(address);
                                searchItem.setImage(image);
                                searchItemList.add(searchItem);

                                Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                            }
                            searchAdapter = new SearchAdapter(searchItemList);
                            RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutmanager);
                            recyclerView.setItemAnimator( new DefaultItemAnimator());
                            recyclerView.setAdapter(searchAdapter);                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        return rootView;
    }

}

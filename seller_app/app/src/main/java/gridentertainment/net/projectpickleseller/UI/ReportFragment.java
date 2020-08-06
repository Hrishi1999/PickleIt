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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import gridentertainment.net.projectpickleseller.Adapters.OrderAdapter;
import gridentertainment.net.projectpickleseller.Models.OrderItem;
import gridentertainment.net.projectpickleseller.R;

public class ReportFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_report, container, false);

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final FirebaseDatabase database;
        database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("sellers").child(userID).child("orders");
        final int[] children = new int[1];

        final TextView tv = rootView.findViewById(R.id.tv_totalorders);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                children[0] = (int)dataSnapshot.getChildrenCount();
                tv.setText(String.valueOf(children[0]));
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });



        return rootView;
    }


}

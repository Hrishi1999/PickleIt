package gridentertainment.net.projectpickle.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Locale;

import gridentertainment.net.projectpickle.Models.Profile;
import gridentertainment.net.projectpickle.R;

public class ProfileFragment extends Fragment {

    private EditText name;
    private EditText tags;
    private EditText address;
    private Spinner city;
    private Spinner language;
    private EditText phone;
    private EditText email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = currentFirebaseUser.getUid();

        final FirebaseDatabase database;
        DatabaseReference databaseReference;
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("consumers").child(userID);

        name = rootView.findViewById(R.id.pr_name);
        address = rootView.findViewById(R.id.pr_address);
        city = rootView.findViewById(R.id.pr_city);
        language = rootView.findViewById(R.id.pr_language);
        phone = rootView.findViewById(R.id.pr_phone);
        email = rootView.findViewById(R.id.pr_email);

        final SharedPreferences pref = getActivity().getSharedPreferences("pickle", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                String languageToLoad;
                languageToLoad = pref.getString("locale", null);

                if(selectedItem.equals("English"))
                {
                    languageToLoad = "en";
                }
                if(selectedItem.equals("Hindi"))
                {
                    languageToLoad = "hi";
                }
                if(selectedItem.equals("Gujarati"))
                {
                    languageToLoad = "gu";
                }

                editor.putString("locale", languageToLoad);

                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration configuration = new Configuration();
                configuration.setLocale(locale);
                Toast.makeText(getContext(), "spanish is set", Toast.LENGTH_SHORT).show();
                getActivity().getResources().updateConfiguration(configuration, getActivity().getResources().getDisplayMetrics());

            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        FloatingActionButton fab1 = rootView.findViewById(R.id.btn_confirm_profile);
        final DatabaseReference finalDatabaseReference = databaseReference;

        finalDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Profile profile = dataSnapshot.getValue(Profile.class);
                String sname = profile.getName();
                String sphone = profile.getPhone();
                String saddress = profile.getAddress();
                String scity = profile.getCity();
                String semail = profile.getEmail();

                name.setText(sname);
                phone.setText(sphone);
                address.setText(saddress);
                city.setSelection(((ArrayAdapter<String>)city.getAdapter()).getPosition(scity));
                email.setText(semail);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pr_name = name.getText().toString();
                String pr_address = address.getText().toString();
                String pr_city = city.getSelectedItem().toString();
                String pr_phone = phone.getText().toString();
                String pr_email = email.getText().toString();

                HashMap<String, Object> profile = new HashMap<>();
                profile.put("name", pr_name);
                profile.put("address", pr_address);
                profile.put("phone", pr_phone);
                profile.put("email", pr_email);
                profile.put("city", pr_city);

                finalDatabaseReference.updateChildren(profile);

                Toast.makeText(getContext(), getString(R.string.profile_updated), Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

}

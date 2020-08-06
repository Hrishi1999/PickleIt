package gridentertainment.net.projectpickleseller.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;

import gridentertainment.net.projectpickleseller.Models.Profile;
import gridentertainment.net.projectpickleseller.R;

public class ProfileFragment extends Fragment {

    private EditText name;
    private EditText tags;
    private EditText address;
    private Spinner spinner;
    private EditText phone;
    private EditText email;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    private ImageView imageView;
    private String userID;
    private String imageURL = "";

    public ProfileFragment() {
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = currentFirebaseUser.getUid();

        final FirebaseDatabase database;
        DatabaseReference databaseReference;
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("sellers").child(userID);

        name = rootView.findViewById(R.id.pr_name);
        tags = rootView.findViewById(R.id.pr_tags);
        address = rootView.findViewById(R.id.pr_address);
        spinner = rootView.findViewById(R.id.pr_city);
        phone = rootView.findViewById(R.id.pr_phone);
        email = rootView.findViewById(R.id.pr_email);
        imageView = rootView.findViewById(R.id.imageView3);

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
                String stags = profile.getTags();
                String semail = profile.getEmail();

                name.setText(sname);
                phone.setText(sphone);
                address.setText(saddress);
                spinner.setSelection(((ArrayAdapter<String>)spinner.getAdapter()).getPosition(scity));
                //city.setText(scity);
                tags.setText(stags);
                email.setText(semail);

                Picasso.get().load(profile.getImage())
                        .placeholder(R.color.colorAccent)
                        .into(imageView);

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
                String pr_tags = tags.getText().toString();
                String pr_address = address.getText().toString();
                String pr_city = spinner.getSelectedItem().toString();
                String pr_phone = phone.getText().toString();
                String pr_email = email.getText().toString();

                HashMap<String, Object> profile = new HashMap<>();
                profile.put("name", pr_name);
                profile.put("address", pr_address);
                profile.put("phone", pr_phone);
                profile.put("tags", pr_tags);
                profile.put("email", pr_email);
                profile.put("city", pr_city);
                profile.put("image", imageURL);

                finalDatabaseReference.updateChildren(profile);

                Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
        
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

                FirebaseStorage storage;
                StorageReference storageReference;
                storage = FirebaseStorage.getInstance();
                storageReference = storage.getReference();

                if(filePath != null)
                {
                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                    final StorageReference ref = storageReference.child("seller_profiles/"+ userID);
                    ref.putFile(filePath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            progressDialog.hide();
                            return ref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                imageURL = task.getResult().toString();
                                Toast.makeText(getContext(), imageURL, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.hide();
                        }
                    });
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}

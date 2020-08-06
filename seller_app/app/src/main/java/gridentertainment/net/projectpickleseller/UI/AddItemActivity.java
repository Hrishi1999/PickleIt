package gridentertainment.net.projectpickleseller.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Random;

import gridentertainment.net.projectpickleseller.Models.Profile;
import gridentertainment.net.projectpickleseller.Models.SellersItems;
import gridentertainment.net.projectpickleseller.R;

public class AddItemActivity extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    private ImageView imageView;
    private String imageURL = "";
    private String userID;
    private String itm_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = currentFirebaseUser.getUid();
        final FirebaseDatabase database;
        DatabaseReference databaseReference;
        database = FirebaseDatabase.getInstance();

        Random rand = new Random();
        Intent intent = getIntent();
        Toast.makeText(this, intent.getStringExtra("itm_id"), Toast.LENGTH_SHORT).show();

        if (intent == null)
        {
            int i = rand.nextInt(10000000);
            itm_id = String.valueOf(i);
        }
        else
        {
            itm_id =  intent.getStringExtra("itm_id");
        }

        databaseReference = database.getReference("sellers").child(userID).child("items").child(String.valueOf(itm_id));
        final FloatingActionButton fab1 = findViewById(R.id.btn_add2);

        final EditText name = findViewById(R.id.ed_itm_name);
        final EditText tags = findViewById(R.id.ed_item_tags);
        final EditText op = findViewById(R.id.ed_item_op);
        final EditText dp = findViewById(R.id.ed_item_dp);
        imageView = findViewById(R.id.imageViewAdd);
        final DatabaseReference finalDatabaseReference = databaseReference;

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        finalDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SellersItems sellersItems = dataSnapshot.getValue(SellersItems.class);
                String sname = sellersItems.getName();
                String stags = sellersItems.getTags();
                String sop = sellersItems.getOriginal_price();
                String sp = sellersItems.getPrice();

                name.setText(sname);
                tags.setText(stags);
                op.setText(sop);
                dp.setText(sp);

                Picasso.get().load(sellersItems.getImage())
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

                String it_name = name.getText().toString();
                String it_tags = tags.getText().toString();
                String it_op = op.getText().toString();
                String it_dp = dp.getText().toString();

                if(it_name.equals(""))
                {
                    Toast.makeText(AddItemActivity.this, "Name field can not be empty!", Toast.LENGTH_SHORT).show();
                }
                if(it_tags.equals(""))
                {
                    Toast.makeText(AddItemActivity.this, "Tag field can not be empty!", Toast.LENGTH_SHORT).show();
                }
                if(it_dp.equals(""))
                {
                    Toast.makeText(AddItemActivity.this, "Display price can not be empty", Toast.LENGTH_SHORT).show();
                }
                if(it_op.equals(""))
                {
                    Toast.makeText(AddItemActivity.this,
                            "Price field can not be empty! Type price same as display price, if same."
                            , Toast.LENGTH_SHORT).show();
                }

                final SellersItems sellersItems = new SellersItems();

                sellersItems.setName(it_name);
                sellersItems.setOriginal_price(it_op);
                sellersItems.setPrice(it_dp);
                sellersItems.setTags(it_tags);
                sellersItems.setImage(imageURL);
                sellersItems.setItm_id(itm_id);

                finalDatabaseReference
                        .setValue(sellersItems);

                finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

                FirebaseStorage storage;
                StorageReference storageReference;
                storage = FirebaseStorage.getInstance();
                storageReference = storage.getReference();

                if(filePath != null)
                {
                    //final ProgressDialog progressDialog = new ProgressDialog(getContext());
                    //progressDialog.setTitle("Uploading...");
                    //progressDialog.show();
                    final StorageReference ref = storageReference.child("seller_profiles/" + itm_id + "/" + userID);
                    ref.putFile(filePath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return ref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                imageURL = task.getResult().toString();
                            } else {
                                Toast.makeText(getApplicationContext(), "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
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

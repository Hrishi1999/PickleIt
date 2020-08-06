package gridentertainment.net.projectpickle.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import gridentertainment.net.projectpickle.R;
import gridentertainment.net.projectpickle.UI.PhoneVerifyActivity;
import gridentertainment.net.projectpickle.R;

public class PhoneLoginActivity extends AppCompatActivity {

    private EditText editTextMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='#ff5722'>Cart</font>"));

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(PhoneLoginActivity.this, MainActivity.class));
            finish();
        }

        TextView tv = findViewById(R.id.login_em);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PhoneLoginActivity.this, LoginActivity.class));
            }
        });

        editTextMobile = findViewById(R.id.ed_login_phone);
        Button nextButton = findViewById(R.id.btn_phone_next);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile = editTextMobile.getText().toString().trim();

                if(mobile.isEmpty() || mobile.length() < 10){
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;
                }

                Intent intent = new Intent(PhoneLoginActivity.this, PhoneVerifyActivity.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);
            }
        });
    }
}

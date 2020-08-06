package gridentertainment.net.projectpickle.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Locale;

import gridentertainment.net.projectpickle.R;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        SharedPreferences settings = getSharedPreferences("pickle", 0);
        boolean firstRun = settings.getBoolean("firstRun", true);
        String languageToLoad = settings.getString("locale", null );

        if(languageToLoad != null)
        {
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration configuration = new Configuration();
            configuration.setLocale(locale);
            Toast.makeText(this, "spanish is set", Toast.LENGTH_SHORT).show();
            getApplication().getResources().updateConfiguration(configuration, getApplication().getResources().getDisplayMetrics());
        }

        Fragment fragment;

        if (firstRun) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstRun", false);
            editor.apply();
            fragment = new ProfileFragment();
            loadFragment(fragment);
        } else {
            fragment = new FragmentMain();
            loadFragment(fragment);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_name:
                    fragment = new FragmentMain();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_search:
                    fragment = new SearchFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_orders:
                    fragment = new PreviousOrderFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

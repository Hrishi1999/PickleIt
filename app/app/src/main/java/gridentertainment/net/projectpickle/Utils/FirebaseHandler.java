package gridentertainment.net.projectpickle.Utils;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHandler extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
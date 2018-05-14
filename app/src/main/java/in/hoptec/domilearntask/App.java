package in.hoptec.domilearntask;

import android.app.Application;
import android.content.Context;
import android.provider.SyncStateContract;

import com.google.firebase.FirebaseApp;

/**
 * Created by shivesh on 14/5/18.
 */

public class App extends Application {



    private static Context mContextApplication;

    public void onCreate(){

        super.onCreate();
        FirebaseApp.initializeApp(this);
        mContextApplication = getApplicationContext();
    }


    public static Context getAppContext(){
        return mContextApplication;
    }
}

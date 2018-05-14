package in.hoptec.domilearntask;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static in.hoptec.domilearntask.utils.Constants.*;

/**
 * Created by shivesh on 14/5/18.
 */

public abstract class BaseActivity extends AppCompatActivity {


    private FirebaseDatabase database;
    protected DatabaseReference domilearn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database=FirebaseDatabase.getInstance();
        domilearn=database.getReference(DB_BASE_REF);


    }

    protected void animateAVD(ImageView view)
    {
        Drawable d = view.getDrawable();
        if (d instanceof AnimatedVectorDrawable) {
            AnimatedVectorDrawable avd = (AnimatedVectorDrawable) d;
            avd.start();
        } else if (d instanceof AnimatedVectorDrawableCompat) {
            AnimatedVectorDrawableCompat avd = (AnimatedVectorDrawableCompat) d;
            avd.start();
        }
    }



}

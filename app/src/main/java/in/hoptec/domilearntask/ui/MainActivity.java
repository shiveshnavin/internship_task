package in.hoptec.domilearntask.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import in.hoptec.domilearntask.BaseActivity;
import in.hoptec.domilearntask.R;

public class MainActivity extends BaseActivity {

    private DatabaseReference nameReference;

    private ImageView image;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image=(ImageView)findViewById(R.id.image);
        name=(TextView)findViewById(R.id.name);

        nameReference=domilearn.child("name");
        nameReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                animateAVD(image);
                name.setText(dataSnapshot.getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







    }
}

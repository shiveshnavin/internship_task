package in.hoptec.domilearntask.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


import in.hoptec.domilearntask.BaseActivity;
import in.hoptec.domilearntask.R;
import in.hoptec.domilearntask.utils.Constants;

public class MainActivity extends BaseActivity {

    private DatabaseReference nameReference;

    private ImageView image;
    private TextView name;
    private AppCompatButton copy,browse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image=(ImageView)findViewById(R.id.image);
        name=(TextView)findViewById(R.id.name);
        copy=(AppCompatButton)findViewById(R.id.copy);
        browse=(AppCompatButton)findViewById(R.id.browse);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);


        nameReference=domilearn.child("name");
        nameReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.i(""+nameReference.getKey(),""+dataSnapshot);
                animateAVD(image);
                if(dataSnapshot!=null)
                    name.setText(dataSnapshot.getValue().toString());
                else
                    name.setText("No Data !");

                try {
                    if(r!=null&&!r.isPlaying())
                       r.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData=ClipData.newPlainText("Web URL for Cloud Trigger",Constants.WEB_URL);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getApplicationContext(),"Copied to clipboard",Toast.LENGTH_LONG).show();
            }
        });



        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(Intent.ACTION_VIEW);
                it.setData(Uri.parse(Constants.WEB_URL));
                startActivity(it);
            }
        });


    }
}

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


import java.util.List;

import in.hoptec.domilearntask.BaseActivity;
import in.hoptec.domilearntask.R;
import in.hoptec.domilearntask.api.Name;
import in.hoptec.domilearntask.api.NameService;
import in.hoptec.domilearntask.utils.Constants;
import in.hoptec.domilearntask.utils.DbHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        copy.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                testnameGet();
                return false;
            }
        });
        browse.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                testnamePost();
                return false;
            }
        });

    }

    private void dbtest ( ){


        Log.e("db","TESTING");
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://us-central1-test-a0930.cloudfunctions.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NameService nameService=retrofit.create(NameService.class);

        Call<List<String>> nameCall=nameService.getNames();
        nameCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {

                DbHelper db=new DbHelper(MainActivity.this );db.reCreate();

                for(int i=0;i<response.body().size();i++)
                {
                    db.insertName(response.body().get(i) );
                }

                readNamesFromDB();

            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

                Log.e("Db","Failed "+t.getMessage());
            }
        });


    };


    private void testnamePost ( ){

        ;
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://us-central1-test-a0930.cloudfunctions.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NameService service=retrofit.create(NameService.class);
        Call<Name > nameCall=service.postName("2");

        nameCall.enqueue(new Callback<Name>() {
            @Override
            public void onResponse(Call<Name> call, Response<Name> response) {
                DbHelper helper=new DbHelper(MainActivity.this);
                helper.reCreate();
                helper.insertName(response.body().name);
                helper.getNames();
            }

            @Override
            public void onFailure(Call<Name> call, Throwable t) {

            }
        });

    };
    private void testnameGet ( ){

        ;
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://us-central1-test-a0930.cloudfunctions.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NameService service=retrofit.create(NameService.class);
        Call<Name > nameCall=service.getName("2");

        nameCall.enqueue(new Callback<Name>() {
            @Override
            public void onResponse(Call<Name> call, Response<Name> response) {
                DbHelper helper=new DbHelper(MainActivity.this);
                helper.reCreate();
                helper.insertName(response.body().name);
                helper.getNames();
            }

            @Override
            public void onFailure(Call<Name> call, Throwable t) {

            }
        });

    };

    private void readNamesFromDB ( ){

        DbHelper db=new DbHelper(MainActivity.this);
        List<String > names=db.getNames();

        for(int i=0;i<names.size();i++)
        {
            Log.i("readNamesFromDB",names.get(i));
        }


    };

}

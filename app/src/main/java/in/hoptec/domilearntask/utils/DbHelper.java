package in.hoptec.domilearntask.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by shivesh on 17/5/18.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static String DB_NAME = "Names";


    public DbHelper(Context context ) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+DB_NAME+" (id integer primary key,name text);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DB_NAME+"");

    }

    public void insertName(String name)
    {
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("INSERT INTO "+DB_NAME+" (name) VALUES (\""+name+"\"");
        Log.e("DB","Inserted "+name);


    }

    public ArrayList<String > getNames()
    {
        ArrayList<String > names=new ArrayList<>();
        Cursor cr=getReadableDatabase().rawQuery("SELECT * FROM "+DB_NAME,null);
        int i=0;
        cr.moveToFirst();
        while(true)
        {
            Log.e("DB","Read "+cr.getString(i));
            names.add(cr.getString(i++));
            cr.moveToNext();
            if(cr.isLast())
            {
                break;
            }
        }
        return names;
    }





}

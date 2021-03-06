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
    public void reCreate( ) {

        SQLiteDatabase sqLiteDatabase=getWritableDatabase();

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DB_NAME+"");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+DB_NAME+" (id integer primary key,name text);");

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DB_NAME+"");

    }

    public void insertName(String name)
    {
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("INSERT INTO "+DB_NAME+" (name) VALUES ('"+name+"');");
        Log.e("DB","Inserted "+name);


    }

    public ArrayList<String > getNames()
    {
        ArrayList<String > names=new ArrayList<>();
        Cursor cr=getReadableDatabase().rawQuery("SELECT * FROM "+DB_NAME,null);
         while(cr.moveToNext())
        {
            Log.e("DB","Read "+cr.getString(1));
            names.add(cr.getString(1));
            cr.moveToNext();

        }

        return names;
    }





}

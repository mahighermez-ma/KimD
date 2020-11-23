package pro.kimd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class AllContactDb extends SQLiteOpenHelper {
    private static final String DBName="myforcis";
    private static final int DBVersion=1;
    private  final String ID="id";
    private  final String NAME="name";
    private  final String NUMBER="number";
    private  final String TABLENAME="tableallcontact";


    public AllContactDb(@Nullable Context context) {
        super(context, DBName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase mydb) {
        String cQuery="CREATE TABLE "+TABLENAME+"("+ID+" INTEGER PRIMARY KEY,"+NAME+" TEXT,"+NUMBER+" TEXT);";
        mydb.execSQL(cQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void Insertcontact(String name,String number){
        SQLiteDatabase idb = null;
        ContentValues icv = null;
        try {
            idb=this.getWritableDatabase();
            icv=new ContentValues();
            icv.put(NAME,name);
            icv.put(NUMBER,number);
            idb.insert(TABLENAME,null,icv);
        }catch (Exception e){
            Log.e("Ex343", e.toString() );
        }finally {
            if (idb!=null){
                idb.close();
            }if (icv!=null){
                icv.clear();
            }
        }

    }
    public ArrayList<String> getcontacts(){
        ArrayList<String> conta = null;
        SQLiteDatabase gdb = null;
        Cursor gCur = null;
        String gQuery;
        try {
            conta=new ArrayList<String>();
            gdb=this.getReadableDatabase();
            gQuery="SELECT * FROM tableallcontact";
            gCur=gdb.rawQuery(gQuery,null);
            if (gCur.moveToFirst()){
                while (gCur.moveToNext()){
                    conta.add(gCur.getString(1)+",,"+gCur.getString(2));

                }}
        }catch (Exception e){
            Log.e("Ex3434", e.toString() );
        }finally {
            if (gdb!=null){
                gdb.close();
            }if (gCur!=null){
                gCur.close();
            }
        }

        return conta;
    }
    public ArrayList<String> getcontactname(){
        ArrayList<String> conta = null;
        SQLiteDatabase gdb = null;
        Cursor gCur = null;
        String gQuery;
        try {
            conta=new ArrayList<String>();
            gdb=this.getReadableDatabase();
            gQuery="SELECT * FROM tableallcontact";
            gCur=gdb.rawQuery(gQuery,null);
            if (gCur.moveToFirst()){
                while (gCur.moveToNext()){
                    conta.add(gCur.getString(1));

                }}
        }catch (Exception e){
            Log.e("Ex3434", e.toString() );
        }finally {
            if (gdb!=null){
                gdb.close();
            }if (gCur!=null){
                gCur.close();
            }
        }

        return conta;
    }}

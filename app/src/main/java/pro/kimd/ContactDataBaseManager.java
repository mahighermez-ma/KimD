package pro.kimd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class ContactDataBaseManager extends SQLiteOpenHelper {
    private static final String DBName="myfo";
    private static final int DBVersion=1;
    private  final String ID="id";
    private  final String NAME="name";
    private  final String NUMBER="number";
    private  final String TABLENAME="tablecontact";


    public ContactDataBaseManager(@Nullable Context context) {
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
        }catch (Exception e){}finally {
            if (idb!=null){
                idb.close();
            }if (icv!=null){
                icv.clear();
            }
        }

    }
    public ArrayList<String> getcontact(){
        ArrayList<String> conta = null;
        SQLiteDatabase gdb = null;
        Cursor gCur = null;
        String gQuery;
        try {
            conta=new ArrayList<String>();
            gdb=this.getReadableDatabase();
            gQuery="SELECT * FROM tablecontact";
            gCur=gdb.rawQuery(gQuery,null);
            if (gCur.moveToFirst()){
                while (gCur.moveToNext()){
                    conta.add(gCur.getString(1));

                }}
        }catch (Exception e){}finally {
           if (gdb!=null){
                gdb.close();
            }if (gCur!=null){
                gCur.close();
            }
        }

        return conta;
}}

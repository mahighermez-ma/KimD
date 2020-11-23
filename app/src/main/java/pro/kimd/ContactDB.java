package pro.kimd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class ContactDB extends SQLiteOpenHelper {
    private static final String DBName="myfodf";
    private static final int DBVersion=1;
    private  final String ID="id";
    private  final String NAME="name";
    private  final String NUMBER="number";
    private  final String TABLENAME="tablecontactserver";


    public ContactDB(@Nullable Context context) {
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
    public String getcontact(String numbers){
        String conta = null;
        SQLiteDatabase gdb = null;
        Cursor gCur = null;
        String gQuery;
        try {
            gdb=this.getReadableDatabase();
            gQuery="SELECT * FROM "+TABLENAME+" where NUMBER=?";
            gCur=gdb.rawQuery(gQuery,new String[] {numbers});
            if (gCur.moveToFirst()){
                    conta=gCur.getString(1);

                }
        }catch (Exception e){
            Log.e("select3245", e.toString() );
        }finally {
            if (gdb!=null){
                gdb.close();
            }if (gCur!=null){
                gCur.close();
            }
        }

        return conta;
    }
    public int getsize(){
        int conta = 0;
        SQLiteDatabase gdb = null;
        Cursor gCur = null;
        String gQuery;
        try {
            gdb=this.getReadableDatabase();
            gQuery="SELECT * FROM tablecontactserver";
            gCur=gdb.rawQuery(gQuery,null);
            conta=gCur.getCount();
        }catch (Exception e){}finally {
            if (gdb!=null){
                gdb.close();
            }if (gCur!=null){
                gCur.close();
            }
        }

        return conta;
    }
    public boolean deleteData(){
        SQLiteDatabase db = null;
        long result;
        try {
            db=this.getWritableDatabase();
            result=db.delete(TABLENAME,ID+"=?",new String[] {"1"});
            if(result==0)
                return false;
            else
                return true;
        }catch (Exception e){}finally {
            if (db!=null){
                db.close();
            }
        }
        return false;
    }
}

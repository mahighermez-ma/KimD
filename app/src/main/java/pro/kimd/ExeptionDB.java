package pro.kimd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class ExeptionDB extends SQLiteOpenHelper {
    private static final String DBName="myfo.db";
    private static final int DBVersion=1;
    private  final String ID="id";
    private  final String NAME="name";
    private  final String STATUS="status";
    private  final String TABLENAME="tableexeption";

    public ExeptionDB(@Nullable Context context) {
        super(context, DBName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase mydb) {
        String cQuery="CREATE TABLE "+TABLENAME+"("+ID+" INTEGER PRIMARY KEY,"+NAME+" TEXT,"+STATUS+" TEXT);";
        mydb.execSQL(cQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void InserExeption(String name,String status){
        ContentValues icv = null;
        SQLiteDatabase idb = null;
        try {
            idb=this.getWritableDatabase();
            icv=new ContentValues();
            icv.put(NAME,name);
            icv.put(STATUS,status);
            idb.insert(TABLENAME,null,icv);
        }catch (Exception e){Log.e("coding",e.toString() );}finally {

            if (idb!=null){
                idb.close();
            }if (icv!=null){
                icv.clear();
            }

        }

    }
    public ArrayList<String> getexeption(){
        ArrayList<String> exeption = null;
        SQLiteDatabase gdb = null;
        String gQuery=null;
        Cursor gCur = null;
        try {
            exeption=new ArrayList<String>();
            gdb=this.getReadableDatabase();
            gQuery="SELECT * FROM tableexeption";
            gCur=gdb.rawQuery(gQuery,null);

            while (gCur.moveToNext()){
                exeption.add(gCur.getString(1)+",,"+gCur.getString(2));

            }
        }catch (Exception e){
            Log.e("coding",e.toString() );
        }finally {
            if (gdb!=null){
                gdb.close();
            }if (gCur!=null){
                gCur.close();
            }

        }
        return exeption;
    }
    public void Updatelock(String name,String status){
        SQLiteDatabase udb = null;
        ContentValues ucv = null;
        try {
            udb=this.getWritableDatabase();
            ucv=new ContentValues();
            ucv.put(STATUS,status);

            udb.update(TABLENAME,ucv,NAME+"=?",new String[]{name});
        }catch (Exception e){}finally {
            if (udb!=null){
                udb.close();
            }if (ucv!=null){
                ucv.clear();
            }
        }


    }
    public boolean deleteExeption(String KEY){
        SQLiteDatabase db = null;
        long result;
        try {
            db=this.getWritableDatabase();
            result=db.delete(TABLENAME,NAME+"=?",new String[] {KEY});
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

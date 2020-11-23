package pro.kimd;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class getContact {
    ArrayList<String> contactname=new ArrayList<String>();

    ArrayList<String> calls=new ArrayList<String>();
    ArrayList<String> coname=new ArrayList<String>();
    Cursor phone=null;
    public ArrayList<String> getContact(Context context,String first,String last){
        //Cursor phone2=context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER},null,null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        //Toast.makeText(context, String.valueOf(phone2.getCount()), Toast.LENGTH_SHORT).show();
        phone=context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER},null,null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"+" limit "+"200"+" offset "+last+" ");
        try {
            setcontacty(phone,context);}catch (Exception e){
            Log.e("tagcall", e.toString());
        }
        return contactname;
    }
    public void setcontacty(Cursor cursor,Context context) {
        cursor.moveToFirst();
        int i=0;
       // Toast.makeText(context, String.valueOf(cursor.getCount()), Toast.LENGTH_SHORT).show();
        while (i<cursor.getCount()) {

            if (cursor.getColumnName(0) != null) {
                try {
                    if (!coname.contains(cursor.getString(0))){
                        coname.add(cursor.getString(0));
                        contactname.add(cursor.getString(0)+";"+cursor.getString(1));}
                } catch (Exception e) {
                    Log.d("sendcontac", e.getMessage());
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
//            }

            cursor.moveToNext();
            i++;
        }

        cursor.close();


    }
}

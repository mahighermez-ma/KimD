package pro.kimd;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchActivity extends AppCompatActivity {
    ArrayList<String> call=new ArrayList<String>();
    ArrayList<String> viz=new ArrayList<String>();
    ArrayList<String> name=new ArrayList<String>();
    ArrayList<String> number=new ArrayList<String>();
    private static final int PICK_CONTACT_REQUEST = 102;
    EditText edtphonesearch;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//            Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
//            pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
//            startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);

        Intent intent=getIntent();
        type=intent.getStringExtra("type");
        edtphonesearch=(EditText)findViewById(R.id.edtphonesearch);
        edtphonesearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (type.equals("1")){
                    setRecyclercontact(editable.toString());
                }else {
                    setRecyclall(editable.toString());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using CursorLoader to perform the query.
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);
               // Toast.makeText(this, number, Toast.LENGTH_SHORT).show();

                // Do something with the phone number...
            }
        }
    }

    public ArrayList<String> setRecyclercontact(String text) {
        call = new ArrayList<>();
        name = new ArrayList<>();
        number=new ArrayList<>();
        if (text.equals("")) {
        } else {


        String contactName = "";
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.PhoneLookup.DISPLAY_NAME + " LIKE ? ", new String[]{"%" + text + "%"}, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                number.add(cursor.getString(cursor.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER)));
                if (name.contains(cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)))) {
                } else {
                    name.add(cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));

                    call.add(cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) + ";" + cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER)));

                }

            }
            cursor.close();
        }
        Cursor cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.NUMBER + " LIKE ? ", new String[]{"%" + text + "%"}, null);

        if (cursor2 != null) {
            while (cursor2.moveToNext()) {
                number.add(cursor2.getString(cursor2.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER)));
                if (name.contains(cursor2.getString(cursor2.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)))) {
                } else {
                    name.add(cursor2.getString(cursor2.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));

                    call.add(cursor2.getString(cursor2.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) + ";" + cursor2.getString(cursor2.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    Log.e("setRecycler32", cursor2.getString(cursor2.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) + ";" + cursor2.getString(cursor2.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER)));
                }

            }
            cursor2.close();
        }
    }
        final RecyclerView recyclerViewgetcv=findViewById(R.id.recyclercall);
        final AdaptercardviewContact adapter = new AdaptercardviewContact(call, getApplicationContext());
        recyclerViewgetcv.setAdapter(adapter);
        LayoutAnimationController animation =
                AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_animation_fall_down);
        recyclerViewgetcv.setLayoutAnimation(animation);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewgetcv.setLayoutManager(layoutManager);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewgetcv.setLayoutManager(linearLayoutManager);
        if (number.size()==0){
            number.add(text);
        }

        return number;
    }
    public void setRecyclall(String text){
        ArrayList<String> numbers=new ArrayList<>();
        numbers=setRecyclercontact(text);
        call = new ArrayList<>();
        name = new ArrayList<>();
        Log.e("Ex56444", String.valueOf(numbers.size()) );
        int i=0;
        while (i<numbers.size()){
            if (text.equals("")) {} else {
            Uri uri = Uri.parse(String.valueOf(CallLog.Calls.CONTENT_URI));
            Cursor c = getContentResolver().query(uri, null, CallLog.Calls.NUMBER + " LIKE ? ", new String[]{"%" + numbers.get(i) + "%"}, null);
                Log.e("cursursize", String.valueOf(c.getCount())+":"+numbers.get(i)+":");
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

//                    calls.add(formateDate);

                                call.add(getContact(getApplicationContext(), phNumber) + ",," + dir + ",," + formateDate + ",," + callDuration + ",," + formateDate);
                                viz.add("0");

                        }catch (Exception e){

                        }

                    }

                }catch (Exception e){
                    Log.e("tagcall", e.toString() );

                }finally {
                    c.close();
                }
        } i++;
        }
        RecyclerView recyclerViewgetcv=(RecyclerView)findViewById(R.id.recyclercall);
        final AdapterCardviewCall adapter = new AdapterCardviewCall(call,getApplicationContext(),recyclerViewgetcv,viz);
        recyclerViewgetcv.setAdapter(adapter);
        LayoutAnimationController animation =
                AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_animation_fall_down);
        recyclerViewgetcv.setLayoutAnimation(animation);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewgetcv.setLayoutManager(layoutManager);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewgetcv.setLayoutManager(linearLayoutManager);
        }



    public String getContact(Context context, String number){
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
}

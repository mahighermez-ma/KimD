package pro.kimd;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;

public class SetContactslist {
    private ArrayList<String> contact;

    public ArrayList<String> setit(Context context,String first,String last){

        contact=new ArrayList<String>();
        getContact getContact=new getContact();
        contact=getContact.getContact(context,first,last);

        final ArrayList<String> finalContact = contact;
        int forground=0;
        int i=0;
        while (i< finalContact.size()){
            ContactDataBaseManager contactDataBaseManager=new ContactDataBaseManager(context);
            ArrayList<String> dbcontact=new ArrayList<String>();
            dbcontact=contactDataBaseManager.getcontact();
            if (dbcontact.contains(finalContact.get(i).split(";")[0])){}else {
//                try {
//                    ShowContact(context,finalContact.get(i).split(";")[0]);
//                } catch (JSONException e) {
//                    Log.e("taging23", e.toString() );
//                    e.printStackTrace();
//                }
            }
            i++;}
            return contact;
    }
    public void ShowContact(Context context,String name2) throws JSONException {

        ContentResolver cr = context.getContentResolver();

        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME +" = ?",new String[]{name2}, null);
        try {



        if (cur.getCount() > 0) {



            JSONArray ARRAY = new JSONArray();
            while (cur.moveToNext()) {
                JSONObject json = new JSONObject();
                JSONArray WEBarray = new JSONArray();
                JSONArray EMAILarray = new JSONArray();
                JSONArray ADDRESSarray = new JSONArray();
                JSONArray NUMBERarray = new JSONArray();


                String id = cur.getString(cur
                        .getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur
                        .getString(cur
                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                ContactDataBaseManager contactDataBaseManager = new ContactDataBaseManager(context);
                if (contactDataBaseManager.getcontact().contains(name)) {
                } else {

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
                    contactDataBaseManager.Insertcontact(name, "");
                }}

            SendContactToServer sendContactToServer1=new SendContactToServer();
            sendContactToServer1.Sent(context,ARRAY.toString());
        } }catch (Exception e){
            Log.e("taging23", e.toString());
        }
    }
}

package pro.kimd;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Handler;

public class SendContact {
    JSONObject contactjson = null;
    JSONArray contactarray = null;
    ArrayList<String> number1 = null;
    ArrayList<String> name1 = null;
    int contactdbnumber = 0;


    ContactDataBaseManager contactDataBaseManager;
    Cursor phone = null;

    String name = null, number = null;

    int total = 0, daste = 0, baghee = 0, bi = 0, ie = 0, sad = 0, c = 0;
    String conta = null;
    private ArrayList<String> nameList,phoneList,emailList;

    public void sendcontac(Context context) {

//        phone = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, null, null, null);
//        try {
//            ShowContact(context);
//
//// Now query for all the PHONES on the device (no need to query the Contacts table at all)
//
////            JSONObject jsonObject = new JSONObject();
////            JSONArray jsonArray=new JSONArray();
////            while (phone.moveToNext()){
////                String mimeType = phone.getString(2);
////                String data = phone.getString(3);
////                jsonArray.put(mimeType+":"+data);
////            }
////            jsonObject.put("query",phone);
////            Log.e("sendcontac", String.valueOf(cur2Json(phone)) );
////            number1 = new ArrayList<String>();
////            name1 = new ArrayList<String>();
////            contactarray = new JSONArray();
////            while (phone.moveToNext()) {
////
////                name = phone.getString(phone.getColumnIndex(
////                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
////                number = phone.getString(phone.getColumnIndex(
////                        ContactsContract.CommonDataKinds.Phone.NUMBER));
////
////
////            }
//
//        } catch (Exception e) {
//            Log.e("sendcontac", e.toString() );
//        }
//    }
//    public JSONArray cur2Json(Cursor cursor) {
//
//        JSONArray resultSet = new JSONArray();
//        cursor.moveToFirst();
//        while (cursor.isAfterLast() == false) {
//            int totalColumn = cursor.getColumnCount();
//            JSONObject rowObject = new JSONObject();
//            for (int i = 0; i < totalColumn; i++) {
//                if (cursor.getColumnName(i) != null) {
//                    try {
//                        rowObject.put(cursor.getColumnName(i),
//                                cursor.getString(i));
//                    } catch (Exception e) {
//                        Log.d("sendcontac", e.getMessage());
//                    }
//                }
//            }
//            resultSet.put(rowObject);
//            cursor.moveToNext();
//        }
//
//        cursor.close();
//        return resultSet;
//
//    }
//    public ArrayList<String> ShowContact(Context context) throws JSONException {
//
//        nameList = new ArrayList<String>();
//        phoneList = new ArrayList<String>();
//        emailList = new ArrayList<String>();
//        int i=0,maghsomonalayh=50,sadgan=0;
//
//        ContentResolver cr = context.getContentResolver();
//        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
//                null, null, null);
//
//        if (cur.getCount() > 0) {
//            ContactDataBaseManager contactDataBaseManager3=new ContactDataBaseManager(context);
//            contactdbnumber=contactDataBaseManager3.getcontact().size();
//            SendContactToServer sendContactToServer=new SendContactToServer();
//            JSONArray ARRAY=new JSONArray();
//            while (cur.moveToNext()) {
//                JSONObject json=new JSONObject();
//                JSONArray WEBarray=new JSONArray();
//                JSONArray EMAILarray=new JSONArray();
//                JSONArray ADDRESSarray=new JSONArray();
//                JSONArray NUMBERarray=new JSONArray();
//
//
//                String id = cur.getString(cur
//                        .getColumnIndex(ContactsContract.Contacts._ID));
//                String name = cur
//                        .getString(cur
//                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                ContactDataBaseManager contactDataBaseManager=new ContactDataBaseManager(context);
//                if (contactDataBaseManager.getcontact().contains(name)){}else {
//
//                if (Integer
//                        .parseInt(cur.getString(cur
//                                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
//                    // Query phone here. Covered next
//
//                    Cursor pCur = cr.query(
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                            null,
//                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
//                                    + " = ?", new String[] { id }, null);
//
//                    while (pCur.moveToNext()) {
//
//
//
//
//
//                        // Do something with phones
//                        String phoneNo = pCur
//                                .getString(pCur
//                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        NUMBERarray.put(phoneNo);
////                        JSON.put("name",name);
//
//
//
//
//                        nameList.add(name); // Here you can list of contact.
//                        phoneList.add(phoneNo); // Here you will get list of phone number.
//
//                    }
//                    pCur.close();
//                        Cursor emailCur = cr.query(
//                                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
//                                null,
//                                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
//                                new String[]{id}, null);
//                        while (emailCur.moveToNext()) {
//
//                            String email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
//                            EMAILarray.put(email);
////                            Log.d("sendcontac", email);
//                            emailList.add(email); // Here you will get list of email
//
//                        }
//                        final String[] projection = new String[] {
//                                ContactsContract.CommonDataKinds.Website.URL,
//                                ContactsContract.CommonDataKinds.Website.TYPE
//                        };
//                        String selection = ContactsContract.Data.CONTACT_ID + " = " + id + " AND " + ContactsContract.Contacts.Data.MIMETYPE + " = '" + ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE + "'";
//
//                        final Cursor webData = cr.query(ContactsContract.Data.CONTENT_URI, projection, selection, null, null);
//                        while (webData.moveToNext()){
//                            int urlColumnIndex = webData.getColumnIndex(ContactsContract.CommonDataKinds.Website.URL);
//                            String url = webData.getString(urlColumnIndex);
//                            String urlType = webData.getString(webData.getColumnIndex(ContactsContract.CommonDataKinds.Website.TYPE));
//                            WEBarray.put(url);
//                        }
//                        Cursor address_cursror = cr.query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI, null, ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID + " = ?", new String[] { id }, null);
//                        while (address_cursror.moveToNext()){
//                            String adressname = address_cursror.getString(address_cursror.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.DISPLAY_NAME));
//                            String street = address_cursror.getString(address_cursror.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
//                            String state = address_cursror.getString(address_cursror.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
//                            String zip = address_cursror.getString(address_cursror.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
//                            String city = address_cursror.getString(address_cursror.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
//                            JSONObject jsonadress=new JSONObject();
//                            jsonadress.put("name",adressname);
//                            jsonadress.put("street",street);
//                            jsonadress.put("state",state);
//                            jsonadress.put("zip",zip);
//                            jsonadress.put("city",city);
//                            ADDRESSarray.put(jsonadress);
//
//                        }
//
//                        emailCur.close();
//
//                }
//                json.put("name",name);
//                json.put("number",NUMBERarray);
//                json.put("email",EMAILarray);
//                json.put("website",WEBarray);
//                json.put("adress",ADDRESSarray);
////                Log.d("sendcontac3", json.toString());
//                ARRAY.put(json);
//                i++;
//
//                int sadtaee=(cur.getCount()-contactdbnumber)/maghsomonalayh;
//                int sadi=(cur.getCount()-contactdbnumber)/maghsomonalayh;
//                int a=sadtaee * maghsomonalayh,b=(sadgan * maghsomonalayh) + i;
//                if (i % maghsomonalayh == 0) {
////                    Toast.makeText(context, ARRAY.toString(), Toast.LENGTH_SHORT).show();
////                    Log.d("sendcontac4", ARRAY.toString());
//
//                    sendContactToServer.Sent(context,ARRAY.toString(),sadgan,sadi);
//                    ARRAY=new JSONArray();
//                    i = 0;
//                    sadgan++;
//                }
//                if (b > a) {
//                    if ((cur.getCount()-contactdbnumber) > maghsomonalayh) {
//                        int baghimande = (cur.getCount()-contactdbnumber) % (sadtaee * maghsomonalayh);
//                        if (i == baghimande) {
////                            Toast.makeText(context, ARRAY.toString(), Toast.LENGTH_SHORT).show();
////                            Log.d("sendcontac4", ARRAY.toString());
//                            sendContactToServer.Sent(context,ARRAY.toString(),sadgan,sadi);
//                        }
//                    } else {
//                        if (i == (cur.getCount()-contactdbnumber)) {
//                            //send to server
////                            Toast.makeText(context, ARRAY.toString(), Toast.LENGTH_SHORT).show();
////                            Log.d("sendcontac4", ARRAY.toString());
//                            sendContactToServer.Sent(context,ARRAY.toString(),sadgan,sadi);
//                        }
//                    }
//                }
//
//
//                contactDataBaseManager.Insertcontact(name,"");
//
//            }
//            }
////            Log.d("sendcontac4", ARRAY.toString());
//
//
//        }
//
//        return nameList; // here you can return whatever you want.
    }

}

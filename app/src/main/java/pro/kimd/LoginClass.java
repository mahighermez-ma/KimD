package pro.kimd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

import androidx.core.content.ContextCompat;

public class LoginClass {
    ArrayList<String> call,contact;
    public void LOGIN(final Context context, final String nationalNumber, final String countrycode, final String password){
        Log.e("LOGIN", countrycode+":"+nationalNumber+":"+password );
        String url=Url.set()+"login";
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonlogin=new JSONObject(response);
                            String status=jsonlogin.getString("status");
                            String message=jsonlogin.getString("message");
                            switch (status) {
                                case "ok":
                                    final ProgressDialog dialog = ProgressDialog.show(context, "please wait", "loading......", true);

                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                    String owner = jsonlogin.getString("token");
                                    Log.e("tolek",owner);
                                    OwnerDataBaseManager own = new OwnerDataBaseManager(context);
                                    own.Insertowner(owner);
                                    CtokenDataBaseManager ctokenDataBaseManager = new CtokenDataBaseManager(context);
                                    ctokenDataBaseManager.Insertctoken("+" + countrycode.substring(1) + nationalNumber);
                                    Intent intent = new Intent(context, ProgramActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);

//
//
//                                    contact = new ArrayList<String>();
//                                    getContact getContact = new getContact();
//                                    contact = getContact.getContact(context);
//
//                                    final ArrayList<String> finalContact = contact;
//                                    int forground = 0;
//                                    int i = 0;
//                                    while (i < finalContact.size()) {
//                                        ContactDataBaseManager contactDataBaseManager = new ContactDataBaseManager(context);
//                                        ArrayList<String> dbcontact = new ArrayList<String>();
//                                        dbcontact = contactDataBaseManager.getcontact();
//                                        if (dbcontact.contains(finalContact.get(i).split(";")[0])) {
//                                        } else {
//                                            forground++;
//                                            if (forground == 1) {
//                                                Log.e("contacterr", "onCreateView: ");
                                                ContextCompat.startForegroundService(context, new Intent(context, CoService2.class));
//                                            }
//                                        }
//                                        i++;
//                                    }
//
//
//                                    android.os.Handler handler3 = new android.os.Handler();
//                                    call = new ArrayList<String>();
//                                    //contact work
//                                    handler3.postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Setcalls setcalls = new Setcalls();
//                                            call = setcalls.set(context);
//                                            //call work
//                                        }
//                                    }, 1000);
//                                    android.os.Handler handler1 = new android.os.Handler();
//                                    handler1.postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Intent intent = new Intent(context, ProgramActivity.class);
//                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                            Bundle args = new Bundle();
//                                            args.putSerializable("CALL", (Serializable) call);
//                                            args.putSerializable("CONTACT", (Serializable) contact);
//                                            intent.putExtra("BUNDLE", args);
//                                            context.startActivity(intent);
//                                            dialog.dismiss();
//                                        }
//                                    }, 3000);

                                    break;
                                default:
                                    Alert.shows(context, "", message, "ok", "");
                                    SendEror.sender(context, message);
                                    break;
//                            }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            SendEror.sender(context,e.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Alert.shows(context,"","please check the connection","ok","");
                SendEror.sender(context,error.toString());
            }

        })
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("mobile", nationalNumber);
                params.put("country_code", String.valueOf(countrycode));
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

}

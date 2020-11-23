package pro.kimd;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import pro.kimd.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.core.app.ActivityCompat;

public class RegisterClass {
    public void sendRegis(final Context context,final String phone, final String CountryCode){
        String url="https://kimd.cf/api/register";
                StringRequest stringRequest=new StringRequest(Request.Method.POST,url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {Log.e("onErrorResponse32", phone+CountryCode);
                                    JSONObject jsonlogin=new JSONObject(response);
                                    String status=jsonlogin.getString("status");
                                    String message=jsonlogin.getString("message");
                                    switch (status){
                                        case "ok":
                                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                            final LinearLayout layout = new LinearLayout(context);
                                            layout.setOrientation(LinearLayout.VERTICAL);
                                            layout.setPadding(50,10,50,10);
                                            final EditText edtcode = new EditText(context);
                                            final EditText edtPass = new EditText(context);
                                            final EditText edtPassag = new EditText(context);
                                            edtcode.setHint("Verify Code");
                                            edtPass.setHint("Your Password");
                                            edtPassag.setHint("Confirm Passwrod");
                                            alert.setMessage("We have sent an SMS with an activation Code to your phone\n"+phone);
                                            alert.setTitle("");
                                            layout.addView(edtcode);
                                            layout.addView(edtPass);
                                            layout.addView(edtPassag);
                                            alert.setView(layout);

                                            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                    //What ever you want to do with the value
                                                    //OR
                                                    String s = edtcode.getText().toString();
                                                    if (s.equals("")||!edtPass.getText().toString().equals(edtPassag.getText().toString())){
                                                        Animation a = AnimationUtils.loadAnimation(context,R.anim.shake);
                                                        a.reset();
                                                        layout.clearAnimation();
                                                        layout.startAnimation(a);
                                                        Toast.makeText(context, "Please fill in the fields", Toast.LENGTH_LONG).show();
                                                    }else {
                                                        VerifyClass verifyClass=new VerifyClass();
                                                        verifyClass.verifycode(context,phone,CountryCode,edtPass.getText().toString(),edtcode.getText().toString());
                                                    }
                                                }
                                            });

                                            alert.setNegativeButton("Edit number", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                    // what ever you want to do with No option.
                                                }
                                            }).show();

                                            break;
                                        default:
                                            SendEror.sender(context,message);
                                            break;
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    SendEror.sender(context,e.toString());
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                        SendEror.sender(context,error.toString());
                        Log.e("onErrorResponse32", CountryCode+phone);
                    }

                })
                {
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String,String> params=new HashMap<String, String>();
                        params.put("mobile_number", phone);
                        params.put("country_code", CountryCode);
                        params.put("mobile_model", Build. MODEL);
                        params.put("name", "");
                        params.put("imei", setImei(context));
                        return params;
                    }
                };
                RequestQueue requestQueue=Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
    }
    public String setImei(Context context){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            SendEror sendEror=new SendEror();
            sendEror.sender(context,"request permition for phone");
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        final String imei;
        if (Build.VERSION.SDK_INT<=Build.VERSION_CODES.P){
            imei = telephonyManager.getDeviceId();}else {
            imei=Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
        }
        return imei;
    }
}

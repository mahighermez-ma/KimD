package pro.kimd;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
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

import java.util.HashMap;
import java.util.Map;

import androidx.core.app.ActivityCompat;

public class SendEror {
    public static void sender(final Context context, final String message){
        String url=Url.set()+"error_log";
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("errrrrrrrrrrror", "onResponse: " );
                        JSONObject jsonlogin= null;
                        try {
                            jsonlogin = new JSONObject(response);
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                        String status= null;
                        try {
                            status = jsonlogin.getString("status");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        })
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("error_message",message);
                params.put("imei",setImei(context));
                params.put("android_version",String.valueOf(Build.VERSION.SDK_INT));
                params.put("phone_model",Build. MODEL);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
    public static String setImei(Context context){
        final TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "please check the permissions", Toast.LENGTH_SHORT).show();
            SendEror sendEror=new SendEror();
            sendEror.sender(context,"imei login:"+"please check the permissions");
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        return telephonyManager.getDeviceId();
    }
}
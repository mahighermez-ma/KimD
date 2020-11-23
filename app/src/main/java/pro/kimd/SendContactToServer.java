package pro.kimd;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
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

import androidx.core.content.ContextCompat;

public class SendContactToServer {
    public void Sent(final Context context, final String contact){

                String url=Url.set()+"contacts_save";
                StringRequest stringRequest=new StringRequest(Request.Method.POST,url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject jsonlogin= null;
                                try {
                                    jsonlogin = new JSONObject(response);
                                } catch (JSONException e) {
                                    SendEror.sender(context,e.toString());
                                    e.printStackTrace();
                                }
                                String status= null;

                                try {
                                    status = jsonlogin.getString("status");
                                    Log.e("consere", contact );


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
                        params.put("contacts", contact); // encryptedContacts
                        params.put("token", getToken(context));
                        return params;
                    }
                };
                RequestQueue requestQueue=Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);


    }
    public String getToken(Context context){
        OwnerDataBaseManager ownerDataBaseManager=new OwnerDataBaseManager(context);
        return ownerDataBaseManager.getowner();
    }
}

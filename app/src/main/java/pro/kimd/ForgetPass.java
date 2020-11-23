package pro.kimd;

import android.app.ProgressDialog;
import android.content.Context;

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

public class ForgetPass {
    public void sendforget(final Context context, final String code, final String number){
        final ProgressDialog dialog = ProgressDialog.show(context, "please wait", "connecting to server...", true);

        String url=Url.set()+"forget_pass";
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
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
                            if (status.equals("ok")){
                                Alert.shows(context,"","Your password will be sent to your mobile number","OK","");
                            }
                            else {
                                Alert.shows(context,"",jsonlogin.getString("message"),"OK","");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }

        })
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("mobile", number);
                params.put("country_code", code);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}

package pro.kimd;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SetContactServer {
    String number2="",numberr,codee;

    public String setcontact(final Context context, final String number){
         number2=number;
         if (number.substring(0,1).equals("+")){
             numberr=number.substring(3);
             codee=number.substring(0,3);
         }else {
             numberr=number.substring(1,number.length()-1);
             codee="+98";
         }
        Log.e("setcontact47", numberr );
        Log.e("setcontact47", codee );
        String url="https://kimd.cf/api/call_query";
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonlogin= null;
                        try {
                            jsonlogin = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String status= null;
                        try {
                            status = jsonlogin.getString("status");
                            if (status.equals("ok")){
                                JSONArray jsonArray=jsonlogin.getJSONArray("answer");
                                String name=jsonArray.getJSONObject(0).getString("name");
                                number2=name;
                                Log.e("setcontact36", number2 );
                            }
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
                params.put("number",numberr);
                params.put("country_code",codee);
                params.put("token",gettoken(context));
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        Log.e("setcontact36", number2 );
        return number2;
    }
    public String gettoken(Context context){
        OwnerDataBaseManager ownerDataBaseManager=new OwnerDataBaseManager(context);
        return ownerDataBaseManager.getowner();
    }
}

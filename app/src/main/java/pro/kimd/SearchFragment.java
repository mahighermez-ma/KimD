package pro.kimd;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import pro.kimd.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    EditText edtphone,edtcountrycode;
    TextView txt;
    Button btnsearch;
    String code="null";
    ProgressDialog dialog = null;
    private RecyclerView recyclerViewsearch;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search, container, false);
        edtphone=(EditText)view.findViewById(R.id.edtphone);
        edtcountrycode=(EditText)view.findViewById(R.id.edtcountrycode);
        txt=(TextView)view.findViewById(R.id.txtcountry);
        recyclerViewsearch=(RecyclerView)view.findViewById(R.id.recyclersearch);
        final SetcountryName setcountryName=new SetcountryName();
        edtcountrycode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                code="+"+edtcountrycode.getText().toString().substring(1);
                if (setcountryName.Countrycode(code).equals("null")){
                    txt.setText("country code invalid");


                }else {
                    txt.setText(setcountryName.Countrycode(code));
                }
            }
        });
        btnsearch=(Button)view.findViewById(R.id.btnsearch);
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                dialog = ProgressDialog.show((ProgramActivity)getActivity(), "please wait", "connecting to server...", true);

                if (!code.equals("null")&&!edtphone.getText().toString().equals("")){
                    String url="https://kimd.cf/api/num_query";
                    StringRequest stringRequest=new StringRequest(Request.Method.POST,url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    ArrayList<String> name=new ArrayList<String>();
                                    ArrayList<String> number=new ArrayList<String>();
                                    dialog.dismiss();
                                    Log.e("tagit", response);
                                    JSONObject jsonlogin= null;
                                    try {
                                        jsonlogin = new JSONObject(response);
                                    } catch (JSONException e) {
                                        SendEror.sender((ProgramActivity)getActivity(),e.toString());
                                        e.printStackTrace();
                                    }
                                    String status= null;
                                    try {
                                        status = jsonlogin.getString("status");
                                        if (status.equals("ok")){
                                        JSONArray dataarray=jsonlogin.getJSONArray("answer");
                                        int i=0;
                                        while (i<dataarray.length()){
                                            JSONObject jsondata=dataarray.getJSONObject(i);
                                            name.add(jsondata.getString("name"));
                                            number.add(jsondata.getString("number"));
                                            Log.e("tagit", jsondata.getString("name"));
                                            i++;
                                        }

                                            AdapterCardviewsearch adapter = new AdapterCardviewsearch(name,number,(ProgramActivity)getActivity());
                                            recyclerViewsearch.setAdapter(adapter);
                                            LayoutAnimationController animation =
                                                    AnimationUtils.loadLayoutAnimation((ProgramActivity)getActivity(), R.anim.layout_animation_fall_down);
                                            recyclerViewsearch.setLayoutAnimation(animation);
                                            LinearLayoutManager layoutManager = new LinearLayoutManager((ProgramActivity)getActivity());
                                            recyclerViewsearch.setLayoutManager(layoutManager);
                                        Alert.shows((ProgramActivity)getActivity(),"","Your limitation for search is : "+jsonlogin.getString("userLimit"),"OK","");
                                    }else {

                                            Toast.makeText((ProgramActivity)getActivity(), jsonlogin.getString("message"), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        dialog.dismiss();
                                        e.printStackTrace();
                                    }




                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();
                            Log.e("tagit", error.toString());
                        }

                    })
                    {
                        @Override
                        protected Map<String, String> getParams(){
                            Map<String,String> params=new HashMap<String, String>();
                            params.put("number",edtphone.getText().toString());
                            params.put("country_code",code);
                            params.put("token",getToken((ProgramActivity)getActivity()));
                            return params;
                        }
                    };
                    stringRequest.setRetryPolicy(new RetryPolicy() {
                        @Override
                        public int getCurrentTimeout() {
                            return 100000;
                        }

                        @Override
                        public int getCurrentRetryCount() {
                            return 100000;
                        }

                        @Override
                        public void retry(VolleyError error) throws VolleyError {

                        }
                    });
                    RequestQueue requestQueue=Volley.newRequestQueue((ProgramActivity)getActivity());
                    requestQueue.add(stringRequest);
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    public String getToken(Context context){
        OwnerDataBaseManager ownerDataBaseManager=new OwnerDataBaseManager(context);
        return ownerDataBaseManager.getowner();
    }

}

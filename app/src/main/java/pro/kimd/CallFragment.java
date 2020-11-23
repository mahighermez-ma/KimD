package pro.kimd;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import pro.kimd.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CallFragment extends Fragment {
    ArrayList<String> call,totalcall,viz;
    AdapterCardviewCall adapter;
    private String number;
    private String number2;
    private String numberr;
    private String codee;
    private String callireturn;
    FloatingActionButton fab2;
    TextView txtmark;
    View view;
    private RecyclerView recyclerViewgetcv;

    public CallFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_call, container, false);
        try {
            totalcall=new ArrayList<String>();
//            Setcalls setcalls=new Setcalls();
//            totalcall=setcalls.set(getActivity());

            call=new ArrayList<String>();
            final Setcalls setcalls=new Setcalls();
            call=setcalls.set(getActivity(),"200","0");
            viz=new ArrayList<String>();
            int io=0;
            while (io<call.size()){
                viz.add("0");
                io++;
            }
            recyclerViewgetcv=(RecyclerView)view.findViewById(R.id.recyclercall);
            new ItemTouchHelper(itemtouch).attachToRecyclerView(recyclerViewgetcv);
            adapter = new AdapterCardviewCall(call,(ProgramActivity)getActivity(),recyclerViewgetcv,viz);
            recyclerViewgetcv.setAdapter(adapter);
            LayoutAnimationController animation =
                    AnimationUtils.loadLayoutAnimation((ProgramActivity)getActivity(), R.anim.layout_animation_fall_down);
            recyclerViewgetcv.setLayoutAnimation(animation);
            LinearLayoutManager layoutManager = new LinearLayoutManager((ProgramActivity)getActivity());
            recyclerViewgetcv.setLayoutManager(layoutManager);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerViewgetcv.setLayoutManager(linearLayoutManager);
            recyclerViewgetcv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);

//                    if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    int position = getCurrentItem();
                    if (position==0){
                        fab2.setVisibility(View.GONE);
                    }else {
                        fab2.setVisibility(View.VISIBLE);
                    }
//                    Log.e("tyryhhehee", String.valueOf(position) );
//                    Toast.makeText(getActivity(), String.valueOf(position) , Toast.LENGTH_SHORT).show();
                    //onPageChanged(position);
//                    }
                }
            });
            fab2= view.findViewById(R.id.fab2);
            fab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //setAnim(fab2);
                    recyclerViewgetcv.scrollToPosition(0);
                }
            });
            recyclerViewgetcv.setOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
                @Override
                public void onLoadMore(int current_page) {
                    // do something...
                    int size=call.size();
                    Setcalls setcalls1=new Setcalls();
                    ArrayList<String> lists=setcalls1.set(getActivity(),"200",String.valueOf(size));
                    fab2.setVisibility(View.VISIBLE);
                    int i=0;
                    while (i<lists.size()){
                        call.add(lists.get(i));
                        viz.add("0");
                        i++;
                    }

                    adapter.notifyDataSetChanged();

                }
            });
        }catch (Exception e){
            SendEror sendEror=new SendEror();
            sendEror.sender((ProgramActivity)getActivity(),e.toString());
        }

        // Inflate the layout for this fragment
        return view;

    }
    public ArrayList<String> getCall(int size,int init){
        Setcalls setcalls=new Setcalls();
        totalcall=setcalls.set(getActivity(),String.valueOf(init),String.valueOf(size));
        ArrayList<String> mylist=new ArrayList<String>();
        int i=0;
        while (i<size&&i<totalcall.size()){
            if (i>=init){
                mylist.add(totalcall.get(i));}
            i++;
        }
        return mylist;
    }
    ItemTouchHelper.SimpleCallback itemtouch=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            String s=call.get(viewHolder.getAdapterPosition()).split(",,")[0];
            if (s.substring(0,1).equals("0")||s.substring(0,1).equals("9")||s.substring(0,1).equals("8")||s.substring(0,1).equals("7")||s.substring(0,1).equals("6")||s.substring(0,1).equals("5")||s.substring(0,1).equals("4")||s.substring(0,1).equals("3")||s.substring(0,1).equals("2")||s.substring(0,1).equals("1")||s.substring(0,1).equals("+")){
                ContactDB contactDB=new ContactDB(getActivity());
                Log.e("erfss", call.get(viewHolder.getAdapterPosition()).split(",,")[0]);
                if (contactDB.getcontact(call.get(viewHolder.getAdapterPosition()).split(",,")[0])==null){
                    Log.e("erfss", "not in db");
                    setcontctfromserver(getActivity(),call.get(viewHolder.getAdapterPosition()),viewHolder);


                }else {
                    call.set(viewHolder.getAdapterPosition(),contactDB.getcontact(call.get(viewHolder.getAdapterPosition()).split(",,")[0] )+ ",," +call.get(viewHolder.getAdapterPosition()).split(",,")[1] + ",," +call.get(viewHolder.getAdapterPosition()).split(",,")[2] + ",," +call.get(viewHolder.getAdapterPosition()).split(",,")[3] + ",," +call.get(viewHolder.getAdapterPosition()).split(",,")[4]);
                    viz.set(viewHolder.getAdapterPosition(),"1");
                    adapter.notifyDataSetChanged();
                }}else {
                adapter.notifyDataSetChanged();
            }
            //Log.e("erfss", call.get(viewHolder.getAdapterPosition()).split(",,")[0]);

        }
    };

    public void setcontctfromserver(final Context context, final String call2, final RecyclerView.ViewHolder viewHolder){
        final ProgressDialog pd;
        pd = SetProgress.createProgressDialog(getActivity());
        //pd.setMessage("Logging in...");
        pd.setCancelable(false);
        pd.show();


        number=call2.split(",,")[0];
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

                        pd.dismiss();
                        JSONObject jsonlogin= null;
                        try {
                            jsonlogin = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String status= null;
                        try {
                            callireturn=number2;
                            status = jsonlogin.getString("status");
                            if (status.equals("ok")){
//                                txtmark.setVisibility(View.VISIBLE);
                                JSONArray jsonArray=jsonlogin.getJSONArray("answer");
                                String name=jsonArray.getJSONObject(0).getString("name");
                                number2=name;
                                SetContactServer setContactServer=new SetContactServer();
                                ContactDB contactDB=new ContactDB(context);
                                contactDB.Insertcontact(name,number);
                                if (contactDB.getsize()>500){
                                    contactDB.deleteData();
                                }

                                callireturn=number2 + ",," +call2.split(",,")[1] + ",," +call2.split(",,")[2] + ",," +call2.split(",,")[3] + ",," +call2.split(",,")[4];
                                call.set(viewHolder.getAdapterPosition(),callireturn);
                                viz.set(viewHolder.getAdapterPosition(),"1");
                                adapter.notifyDataSetChanged();
                                Log.e("setcontact36", number2 );
                            }else {
                                callireturn=number2 + ",," +call2.split(",,")[1] + ",," +call2.split(",,")[2] + ",," +call2.split(",,")[3] + ",," +call2.split(",,")[4];
                                call.set(viewHolder.getAdapterPosition(),callireturn);
                                adapter.notifyDataSetChanged();
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
        RequestQueue requestQueue=Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
//                adapter.notifyItemChanged(i);

    }
    public String gettoken(Context context){
        OwnerDataBaseManager ownerDataBaseManager=new OwnerDataBaseManager(context);
        return ownerDataBaseManager.getowner();
    }
    public void setAnim(FloatingActionButton txt){
        Animation a = AnimationUtils.loadAnimation(getActivity(),R.anim.animzoom);
        a.reset();
        txt.clearAnimation();
        txt.startAnimation(a);
    }
    private int getCurrentItem(){
        return ((LinearLayoutManager)recyclerViewgetcv.getLayoutManager())
                .findFirstVisibleItemPosition();
    }
}

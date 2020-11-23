package pro.kimd;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.PowerManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import androidx.cardview.widget.CardView;
import pro.kimd.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.core.content.ContextCompat;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

// Extend the class from BroadcastReceiver to listen when there is a incoming call
public class CallBarring extends BroadcastReceiver implements SurfaceHolder.Callback
{
    // This String will hold the incoming phone number
    private String number;
    int ic=0;
    boolean calling=false;
    Context context2;
    private WindowManager windowManager;
    private SurfaceView surfaceView;
    LinearLayout layout;
    WindowManager.LayoutParams layoutParams;
    String mLastState;
    Intent inten;
    View myview;

    KeyguardManager myKM;
    @SuppressLint("ResourceType")
    @Override
    public void onReceive(final Context context, Intent intent)
    {

        context2=context;
        number=null;
        ic=0;
        windowManager=null;
        layout=null;
        layoutParams=null;
        inten=null;
        myview=null;
        myKM = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        // If, the received action is not a type of "Phone_State", ignore it
//        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL))
//            return;
//
//            // Else, try to do some action
//        else
//        {
            // Fetch the number of incoming call


            number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            if (number!=null){
                ExeptionDB exeptionDB=new ExeptionDB(context);
                final ArrayList<String> Name=new ArrayList<String>();
                ArrayList<String> Status=new ArrayList<String>();

                int i=0;
                while (i<exeptionDB.getexeption().size()){
                    Name.add(exeptionDB.getexeption().get(i).split(",,")[0]);
                    Status.add(exeptionDB.getexeption().get(i).split(",,")[1]);
                    i++;
                }
                if (Name.contains(number)&&Status.get(Name.indexOf(number)).equals("true")){disconnectPhoneItelephony(context);}
                windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

                if (Build.VERSION.SDK_INT>21){
                    if (Build.VERSION.SDK_INT>25){

                        layoutParams = new WindowManager.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                                PixelFormat.TRANSLUCENT
                        );

                    }else {
                        layoutParams = new WindowManager.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                                WindowManager.LayoutParams.TYPE_PHONE,
                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                                PixelFormat.TRANSLUCENT
                        );

                    }
                }else {
                    layoutParams = new WindowManager.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                            WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                            PixelFormat.TRANSLUCENT
                    );
                }
                WindowManager.LayoutParams Params = new WindowManager.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                );
                final WindowManager.LayoutParams btnparams=new WindowManager.LayoutParams();
                btnparams.width=100;
                btnparams.height=100;
                final WindowManager.LayoutParams txtparams=new WindowManager.LayoutParams();
                txtparams.verticalMargin=200;
                layoutParams.gravity = Gravity.CENTER;
                layoutParams.horizontalMargin=30;
                final RelativeLayout layout = new RelativeLayout(context);
                layout.setLayoutParams(Params);
                final TextView txtName = new TextView(context);
                LayoutInflater li = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                myview = li.inflate(R.layout.dialog, null);

//                windowManager.removeViewImmediate(myview);
                setMargins(myview,30,0,30,0);
                final TextView txtfchar=(TextView)myview.findViewById(R.id.txtfchar);
                String fchar=number.substring(0,1);
                if (fchar.equals("+")||fchar.equals("0")||fchar.equals("1")||fchar.equals("2")||fchar.equals("3")||fchar.equals("4")||fchar.equals("5")||fchar.equals("6")||fchar.equals("7")||fchar.equals("8")||fchar.equals("9")){
                    txtfchar.setText("");
                    txtfchar.setBackground(ContextCompat.getDrawable(context,R.drawable.sape4));
                }else {
                    txtfchar.setText(fchar);
                }
                final TextView txtnumber=(TextView)myview.findViewById(R.id.txtnumber);
                final TextView txtnumber2=(TextView)myview.findViewById(R.id.txtnumber2);
                final TextView txtstate=(TextView)myview.findViewById(R.id.txtstate);
                final TextView txtstate2=(TextView)myview.findViewById(R.id.txtstate2);
                txtstate.setText("Iran");
                txtstate2.setText("Iran");
                Button btncancel=(Button)myview.findViewById(R.id.btncancell);
//                Button btnpluss=(Button)myview.findViewById(R.id.btnpluss);
//                btnpluss.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        windowManager.removeViewImmediate(myview);
//                        context.startActivity(new Intent(context,plussActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                    }
//                });
                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        windowManager.removeView(myview);
                    }
                });
                txtnumber.setText(number);
                Log.e("ringing234654", number );
                txtnumber2.setText(intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER));
                ContactDB contactDB=new ContactDB(context);
                if (contactDB.getcontact(number)==null){
                String url="https://kimd.cf/api/call_query";
                StringRequest stringRequest=new StringRequest(Request.Method.POST,url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject jsonlogin = null;
                                try {
                                    jsonlogin = new JSONObject(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                String status = null;
                                try {
                                    status = jsonlogin.getString("status");
                                    if (status.equals("ok")) {
                                        JSONArray jsonArray = jsonlogin.getJSONArray("answer");
                                        txtnumber.setText(jsonArray.getJSONObject(0).getString("name"));
                                        ContactDB contactDB = new ContactDB(context);
                                        contactDB.Insertcontact(jsonArray.getJSONObject(0).getString("name"), number);
                                        if (contactDB.getsize() > 500) {
                                            contactDB.deleteData();
                                        }
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
                        params.put("number",number.substring(4));
                        params.put("country_code",number.substring(0,4));
                        params.put("token",gettoken(context));
                        return params;
                    }
                };
                RequestQueue requestQueue=Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
                    Log.e("tolek", gettoken(context) );}else {
                    txtnumber.setText(contactDB.getcontact(number));
                }
                txtName.setGravity(Gravity.CENTER);
                txtName.setTextSize(24);
                txtName.setLayoutParams(txtparams);
                Button btncancell=new Button(context);
                btncancell.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_cancel_black_24dp));
                btncancell.setLayoutParams(btnparams);
                btncancell.setGravity(Gravity.RIGHT);
                btncancell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        windowManager.removeView(layout);
                    }
                });
//                    txtName.setTextColor(context.getResources().getColor(Color.argb(225,172,27,216)));
                layout.addView(txtName);
                layout.addView(btncancell);
                layout.setBackground(ContextCompat.getDrawable(context,R.drawable.backgradiant));


                Log.e("ringing2346", "onReceive: ");
                calling=true;
//                PhoneStateChangeListener pscl = new PhoneStateChangeListener();
//                TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
//                tm.listen(pscl, PhoneStateListener.LISTEN_CALL_STATE);
                String str = intent.getAction();
                if ("android.intent.action.PHONE_STATE".equals(str)){
                    inComing(context, intent);}else

                if ("android.intent.action.NEW_OUTGOING_CALL".equals(str)){
                    trueCallerOutgoing(context, intent);}

//            surfaceView.getHolder().addCallback(CallBarring.this);
                return;
//            }
        }
    }

    // Method to disconnect phone automatically and programmatically
    // Keep this method as it is

    private void disconnectPhoneItelephony(Context context)
    {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        Class c = null;
        try {
            c = Class.forName(tm.getClass().getName());

            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            Object telephonyService = m.invoke(tm);
            c = Class.forName(telephonyService.getClass().getName());
            m = c.getDeclaredMethod("endCall");
            m.setAccessible(true);
            m.invoke(telephonyService); } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {


    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
    private class PhoneStateChangeListene extends PhoneStateListener {

        public boolean wasRinging;
        String LOG_TAG = "PhoneListener";
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch(state){
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.e("ringing234", "CALL_STATE_RINGING " );
                    if (!myview.isShown()&&calling){
                        calling=false;
                    windowManager.addView(myview, layoutParams);
                       // context2.startActivity(new Intent(context2,VoicerecorderActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (!myview.isShown()){
                        calling=false;
                        windowManager.addView(myview, layoutParams);
                        Log.e("ringing234", "CALL_STATE_OFFHOOK " );
                       // context2.startActivity(new Intent(context2,VoicerecorderActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                    //Do-NOTHING

                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    try {
                        if( !myKM.inKeyguardRestrictedInputMode()) {

                        windowManager.removeViewImmediate(myview);}
                    }catch (IllegalArgumentException E){

                    }

                        Log.e("ringing234", "CALL_STATE_IDLE");
                    //Do-NOTHING

                    break;

//                    if (!myview.isShown()){
//                        windowManager.addView(myview, layoutParams);}


            }
//            if(inten.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
//                Log.i("ringing234", "CALL");
//                if (!myview.isShown()){
//
//                   // windowManager.addView(myview, layoutParams);
//                }
//                //Do-NOTHING
//            }
        }
    }
    public String gettoken(Context context){
        OwnerDataBaseManager ownerDataBaseManager=new OwnerDataBaseManager(context);
        return ownerDataBaseManager.getowner();
    }
    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
    public void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
    private void inComing(Context context, Intent intent){
        String callState = intent.getStringExtra("state");
        if ("RINGING".equals(callState)){
            Log.i("TAG2342", "RINGING SENDS BUSY");
            Log.e("ringing234", "CALL_STATE_RINGING " );
            if (!myview.isShown()&&calling){
                calling=false;
                windowManager.addView(myview, layoutParams);
                // context2.startActivity(new Intent(context2,VoicerecorderActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }else if ("OFFHOOK".equals(callState)){
            Log.i("TAG2342", "OFFHOOK SENDS BUSY");
            if (!myview.isShown()){
                calling=false;
                windowManager.addView(myview, layoutParams);
                Log.e("ringing234", "CALL_STATE_OFFHOOK " );
                // context2.startActivity(new Intent(context2,VoicerecorderActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }else if("IDLE".equals(callState)){
            Log.i("TAG2342", "IDLE SENDS AVAILABLE");
            try {
                if( !myKM.inKeyguardRestrictedInputMode()) {

                    windowManager.removeViewImmediate(myview);}
            }catch (IllegalArgumentException E){

            }

            Log.e("ringing234", "CALL_STATE_IDLE");
        }
    }

    private void trueCallerOutgoing(Context context, Intent intent)
    {
        String callState = intent.getStringExtra("state");
        if ("RINGING".equals(callState)){
            Log.i("TAG2342", "RINGING SENDS BUSY");
            Log.e("ringing234", "CALL_STATE_RINGING " );
            if (!myview.isShown()&&calling){
                calling=false;
                windowManager.addView(myview, layoutParams);
                // context2.startActivity(new Intent(context2,VoicerecorderActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }else if ("OFFHOOK".equals(callState)){
            Log.i("TAG2342", "OFFHOOK SENDS BUSY");
            if (!myview.isShown()){
                calling=false;
                windowManager.addView(myview, layoutParams);
                Log.e("ringing234", "CALL_STATE_OFFHOOK " );
                // context2.startActivity(new Intent(context2,VoicerecorderActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }else if("IDLE".equals(callState)){
            Log.i("TAG2342", "IDLE SENDS AVAILABLE");
            try {
                if( !myKM.inKeyguardRestrictedInputMode()) {

                    windowManager.removeViewImmediate(myview);}
            }catch (IllegalArgumentException E){

            }

            Log.e("ringing234", "CALL_STATE_IDLE");
        }
    }
}

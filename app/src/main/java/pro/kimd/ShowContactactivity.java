package pro.kimd;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import pro.kimd.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShowContactactivity extends AppCompatActivity {
    TextView txtname,txtcallnum;
    private String contactnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contactactivity);
        Intent intent=getIntent();
        String contactname=intent.getStringExtra("contact").split(";")[0];
        contactnumber=intent.getStringExtra("contact").split(";")[1];
        txtname=(TextView)findViewById(R.id.txtname);
        txtcallnum=(TextView)findViewById(R.id.txtcallnum);
        txtname.setText(contactname);
        txtcallnum.setText("   "+contactnumber);
        ArrayList<String> calls=new ArrayList<String>();
        ArrayList<String> callcon=new ArrayList<String>();
        ArrayList<String> viz=new ArrayList<String>();
        Setcalls setcalls=new Setcalls();
//        calls=setcalls.set(getApplicationContext());
//        int i=0;
//        while (i<calls.size()){
//            if (calls.get(i).split(",,")[0].equals(contactname)){
//                callcon.add(calls.get(i));
//            }
//            i++;
//        }
        RecyclerView recyclerViewgetcv=(RecyclerView)findViewById(R.id.recyclercall);
        AdapterCardviewCall adapter = new AdapterCardviewCall(callcon,getApplicationContext(),recyclerViewgetcv,viz);
        recyclerViewgetcv.setAdapter(adapter);
        LayoutAnimationController animation =
                AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_animation_fall_down);
        recyclerViewgetcv.setLayoutAnimation(animation);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewgetcv.setLayoutManager(layoutManager);



    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void btncall(View view){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", contactnumber, null));
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }
    public void btnsms(View view){
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", contactnumber);
        smsIntent.putExtra("sms_body","");
        startActivity(smsIntent);
    }
    public void btnblock(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(ShowContactactivity.this);
        LinearLayout layout = new LinearLayout(ShowContactactivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50,10,50,10);
        final EditText edtuser = new EditText(ShowContactactivity.this);
        edtuser.setHint("Your country code(like:+31)");
        final EditText edtpass = new EditText(ShowContactactivity.this);
        edtpass.setHint("Mobile Number");
        alert.setTitle("");
        layout.addView(edtuser);
        layout.addView(edtpass);
        alert.setView(layout);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                //OR
                String code="";
                String Number="";
                if (edtuser.getText().toString().substring(0,1).equals("+")){
                    code=edtuser.getText().toString();
                }else if (edtuser.getText().toString().substring(0,2).equals("00")){
                    code="+"+edtuser.getText().toString().substring(2);
                }else {
                    code="+"+edtuser.getText().toString();
                }
                if (edtpass.getText().toString().substring(0,1).equals("0")){
                    Number=edtpass.getText().toString().substring(1);
                }else {
                    Number=edtpass.getText().toString();
                }
                ExeptionDB exeptionDB1=new ExeptionDB(ShowContactactivity.this);
                exeptionDB1.InserExeption(code+Number,"true");
                final ArrayList<String> Name=new ArrayList<String>();
                ArrayList<String> Status=new ArrayList<String>();
                final ExeptionDB exeptionDB=new ExeptionDB(ShowContactactivity.this);
                int i=0;
                while (i<exeptionDB.getexeption().size()){
                    Name.add(exeptionDB.getexeption().get(i).split(",,")[0]);
                    Status.add(exeptionDB.getexeption().get(i).split(",,")[1]);
                    i++;
                }
                RecyclerView recyclerexeption=(RecyclerView)findViewById(R.id.recyclerexeption);
                AdapterCardViewExeption adapter = new AdapterCardViewExeption(Name,Status,ShowContactactivity.this,recyclerexeption);
                recyclerexeption.setAdapter(adapter);
                LayoutAnimationController animation =
                        AnimationUtils.loadLayoutAnimation(ShowContactactivity.this, R.anim.layout_animation_fall_down);
                recyclerexeption.setLayoutAnimation(animation);
                LinearLayoutManager layoutManager = new LinearLayoutManager(ShowContactactivity.this);
                recyclerexeption.setLayoutManager(layoutManager);
            }
        });

        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }
}

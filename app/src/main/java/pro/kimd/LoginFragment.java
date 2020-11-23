package pro.kimd;


import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.telecom.TelecomManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    LinearLayout linearLayout;
    EditText edtphone,edtcountrycode,edtpass;
    Button btnregister;
    TextView txt;
    String code="null";
    ArrayList<String> call,contact;
    ArrayList<String> listPermissionsNeeded;
    private ProgressDialog dialog;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_login, container, false);
        // Inflate the layout for this fragment
        final androidx.appcompat.app.AlertDialog.Builder alertClose=new androidx.appcompat.app.AlertDialog.Builder(getActivity());

        OwnerDataBaseManager ownerDataBaseManager=new OwnerDataBaseManager(getActivity());
        if (ownerDataBaseManager.getowner()==null){


            if (getActivity().getIntent().getBooleanExtra("EXIT",false)){
                getActivity().finish();
                System.exit(0);
            }
            Intent intent2 = new Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
                    .putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, getActivity().getPackageName());
            if (intent2.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(intent2, 162);
            } else {
                Log.w(getActivity().getLocalClassName(), "No Intent available to handle action");
            }
            final LinearLayout regisline=(LinearLayout)view.findViewById(R.id.regisline);
            Animation a = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.item_animation_fall_down);
            a.reset();
            regisline.clearAnimation();
            regisline.startAnimation(a);
            if (Build.VERSION.SDK_INT>=23){
//                if (Settings.canDrawOverlays(getActivity())) {
//                    // permission granted...
//
//                }else{
////                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
////                            Uri.parse("package:" + getActivity().getPackageName()));
////                    startActivityForResult(intent, 1);
//                }

            }


            CtokenDataBaseManager ctokenDataBaseManager=new CtokenDataBaseManager(getActivity().getApplicationContext());
            ctokenDataBaseManager.Insertctoken("+"+"989924779013");
            btnregister=(Button)view.findViewById(R.id.btnregister);
            edtphone=(EditText)view.findViewById(R.id.edtphone);
            edtpass=(EditText)view.findViewById(R.id.edtpass);
            edtcountrycode=(EditText)view.findViewById(R.id.edtcountrycode);
            txt=(TextView)view.findViewById(R.id.txtcountry);
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
                    try {




                        code=getcode(edtcountrycode.getText().toString());
                        if (setcountryName.Countrycode(code).equals("null")){
                            txt.setText("country code invalid");


                        }else {
                            txt.setText(setcountryName.Countrycode(code));
                        }}catch (Exception e){


                    }
                }
            });
            btnregister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertClose.setTitle("Privacy Policy")
                            .setMessage("We have SERIOUSLY Privacy Policy,Please Attention to it.")
                            .setPositiveButton("STUDYING PRIVACY POLICY", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    goToUrl("https://kimd.cfprivacy-policy#");
                                }
                            }).setNegativeButton("I HAVE STUDIED", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Animation a = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.animzoomin);
                            a.reset();
                            btnregister.clearAnimation();
                            btnregister.startAnimation(a);
                            LoginClass loginClass=new LoginClass();
                            loginClass.LOGIN(getActivity(),edtphone.getText().toString(),code,edtpass.getText().toString());
                        }
                    });
                    alertClose.show();
                }
            });


        }
        Button btnpassforget=(Button)view.findViewById(R.id.btnpassforget);
        btnpassforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(50,10,50,10);
                final EditText edtuser = new EditText(getActivity());
                edtuser.setHint("Your country code(like:+31)");
                final EditText edtpass = new EditText(getActivity());
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
                        ForgetPass forgetPass=new ForgetPass();
                        forgetPass.sendforget(getActivity(),code,Number);
                    }
                });

                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();
            }
        });
        return view;

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void offerReplacingDefaultDialer() {
        if (getActivity().getSystemService(TelecomManager.class).getDefaultDialerPackage() != getActivity().getPackageName()) {
            Intent ChangeDialer = new Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER);
            ChangeDialer.putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, getActivity().getPackageName());
            startActivity(ChangeDialer);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall();
            } else {
                Toast.makeText(getActivity(), "calling permission denied", Toast.LENGTH_LONG).show();
            }
            //return;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==162&&resultCode==RESULT_OK){
        }else {
//            androidx.appcompat.app.AlertDialog.Builder alertClose=new androidx.appcompat.app.AlertDialog.Builder(getActivity());
//            alertClose.setMessage("To show your Call history and Contact,KimD needs access to your call and contact and to show who is calling,KimD needs Telephone permission \n(Security note:KimD does not source its data from contacts in your phonebook)")
//                    .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            listPermissionsNeeded=new ArrayList<String>();
//
//                            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                                listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
//                            }
//                            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                                listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
//                            }
//                            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//                                listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);
//                            }
//                            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
//                                listPermissionsNeeded.add(Manifest.permission.READ_CALL_LOG);
//                            }
//
//                            if (!listPermissionsNeeded.isEmpty())
//                            {
//                                ActivityCompat.requestPermissions(getActivity(),listPermissionsNeeded.toArray
//                                        (new String[listPermissionsNeeded.size()]),101);
//
//                            }
//                        }
//                    }).show();

        }
    }

    private void makeCall() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Uri uri = Uri.parse("tel:"+"2299858021");
            Intent Call = new Intent(Intent.ACTION_CALL, uri);
            //Toast.makeText(this, "Entered makeCall()", Toast.LENGTH_SHORT).show();
            Log.i("debinf Dialer", "Entered makeCall()");
            startActivity(Call);

        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
    }


    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
    public String getcode(String s){
        String code = null;
        if (s.substring(0,1).equals("+")){
            code=s;
        }else if (s.substring(0,2).equals("00")){
            code="+"+s.substring(2);
        }else {
            code="+"+s;
        }
        return code;
    }

}
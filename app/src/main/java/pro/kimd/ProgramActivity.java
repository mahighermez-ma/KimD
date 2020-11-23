package pro.kimd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pro.kimd.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

public class ProgramActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, TabLayout.BaseOnTabSelectedListener,NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView txtblock,txtcontact,txtcall;
    Button btnOpenDrawer,btnsearches;
    ArrayList<String> call=new ArrayList<String>();
    ArrayList<String> name=new ArrayList<String>();
    ArrayList<String> number=new ArrayList<String>();
    ArrayList<String> calling;
    ArrayList<String> contact;
    ImageButton imgleftdra;
    String text;
    int id=1;
    private ArrayList<String> viz;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);
        viewPager = (ViewPager) findViewById(R.id.pager);
        btnOpenDrawer=(Button)findViewById(R.id.btnOpenDrawer);
        btnsearches=(Button)findViewById(R.id.btnsearches);
        btnOpenDrawer.setVisibility(View.VISIBLE);
        btnsearches.setVisibility(View.GONE);
        final RecyclerView recyclerViewgetcv=findViewById(R.id.recyclercall);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarNavBar);
//        setSupportActionBar(toolbar);
//        startActivity(new Intent(getApplicationContext(),DialerActivity.class));
        imgleftdra=(ImageButton)findViewById(R.id.imgleftdra);
        final EditText edtphonesearch=(EditText)findViewById(R.id.edtphonesearch);
        edtphonesearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                Drawable img = getResources().getDrawable(R.drawable.ic_close);
                img.setBounds(0, 0, 60, 60);
                imgleftdra.setImageDrawable(img);
                text=editable.toString();
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (editable.toString().equals(text)){
                            if (viewPager.getCurrentItem()==1){
//                    btnOpenDrawer.setVisibility(View.GONE);
                                setrescontact(edtphonesearch.getText().toString());
//                    btnsearches.setVisibility(View.VISIBLE);
//                    setRecyclercontact(editable.toString());
                                viewPager.setVisibility(View.GONE);
                            }else {

//                    btnOpenDrawer.setVisibility(View.GONE);
//                    btnsearches.setVisibility(View.VISIBLE);
                                setreccall(edtphonesearch.getText().toString());
                                viewPager.setVisibility(View.GONE);
                            }
                            if (editable.toString().equals("")){
//                    btnOpenDrawer.setVisibility(View.VISIBLE);
//                    btnsearches.setVisibility(View.GONE);
                                Drawable img = getResources().getDrawable(R.drawable.ic_search_black_24dp);
                                img.setBounds(0, 0, 60, 60);
                                imgleftdra.setImageDrawable(img);
                                viewPager.setVisibility(View.VISIBLE);
//                    viewPager.setVisibility(View.GONE);
//                    ViewGroup.LayoutParams params=recyclerViewgetcv.getLayoutParams();
//                    params.height=ViewGroup.LayoutParams.WRAP_CONTENT;
//                    recyclerViewgetcv.setLayoutParams(params);
                            }
                        }
                    }
                },300);

            }
        });
        imgleftdra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtphonesearch.setText("");
            }
        });
        btnsearches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem()==1){
                    setrescontact(edtphonesearch.getText().toString());
                    viewPager.setVisibility(View.GONE);
                    ViewGroup.LayoutParams params=recyclerViewgetcv.getLayoutParams();
                    params.height=ViewGroup.LayoutParams.MATCH_PARENT;
                    recyclerViewgetcv.setLayoutParams(params);
                }else {

                    setreccall(edtphonesearch.getText().toString());
                    viewPager.setVisibility(View.GONE);
                    viewPager.setVisibility(View.GONE);
                    ViewGroup.LayoutParams params=recyclerViewgetcv.getLayoutParams();
                    params.height=ViewGroup.LayoutParams.MATCH_PARENT;
                    recyclerViewgetcv.setLayoutParams(params);
                }
            }
        });
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        txtblock=(TextView)findViewById(R.id.txtblock);
        txtcontact=(TextView)findViewById(R.id.txtcontact);
        txtcall=(TextView)findViewById(R.id.txtcall);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        View headerView = navigationView.getHeaderView(0);
        TextView txtremind = (TextView) headerView.findViewById(R.id.txtremind);
        CtokenDataBaseManager ctokenDataBaseManager=new CtokenDataBaseManager(getApplicationContext());
        txtremind.setText(ctokenDataBaseManager.getctoken());
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
//        tabLayout.addTab(tabLayout.newTab());


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager


        //Creating our pager adapter
        pager adapter = new pager(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Toast.makeText(ProgramActivity.this, "hi", Toast.LENGTH_SHORT).show();
//                return false;
//
//            }
//        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
               // Toast.makeText(ProgramActivity.this, "onPageScrolled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageSelected(int position) {
              //  Toast.makeText(ProgramActivity.this, "onPageSelected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
               // Toast.makeText(ProgramActivity.this, String.valueOf(viewPager.getCurrentItem()), Toast.LENGTH_SHORT).show();
                switch (viewPager.getCurrentItem()){
                    case 0:
                        txtblock.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_blockwhite, 0, 0);
                        txtblock.setTextColor(getResources().getColor(R.color.black));
                        txtcontact.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_contact_phone_black_24dp, 0, 0);
                        txtcontact.setTextColor(getResources().getColor(R.color.black));
                        txtcall.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_phoneblue, 0, 0);
                        txtcall.setTextColor(getResources().getColor(R.color.colot));
                        break;
                    case 1:
                        txtblock.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_blockwhite, 0, 0);
                        txtblock.setTextColor(getResources().getColor(R.color.black));
                        txtcontact.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_contactblue, 0, 0);
                        txtcontact.setTextColor(getResources().getColor(R.color.colot));
                        txtcall.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_phone_black_24dp, 0, 0);
                        txtcall.setTextColor(getResources().getColor(R.color.black));
                        break;
                    case 2:
                        txtblock.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_blockblue, 0, 0);
                        txtblock.setTextColor(getResources().getColor(R.color.colot));
                        txtcontact.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_contact_phone_black_24dp, 0, 0);
                        txtcontact.setTextColor(getResources().getColor(R.color.black));
                        txtcall.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_phone_black_24dp, 0, 0);
                        txtcall.setTextColor(getResources().getColor(R.color.black));
                        break;
                }
            }
        });


        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.calls:
                setCurrentItem(0,true);
                drawer.closeDrawer(Gravity.LEFT);

                break;
            case R.id.contact:
                setCurrentItem(1,true);
                drawer.closeDrawer(Gravity.LEFT);
                break;
            case R.id.exeption:
                setCurrentItem(2,true);
                drawer.closeDrawer(Gravity.LEFT);
                break;
            case R.id.searchnumber:
                setCurrentItem(3,true);
                drawer.closeDrawer(Gravity.LEFT);
                break;
            case R.id.logout:
                OwnerDataBaseManager ownerDataBaseManager=new OwnerDataBaseManager(getApplicationContext());
                ownerDataBaseManager.delall();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;

        }
        return false;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    public void setCurrentItem (int item, boolean smoothScroll) {
        viewPager.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void onBackPressed() {


        this.moveTaskToBack(true);
    }
    public ArrayList<String> getContacts(){
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<String> object = (ArrayList<String>) args.getSerializable("CONTACT");
        return object;
    }
    public ArrayList<String> getCall(){
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<String> object = (ArrayList<String>) args.getSerializable("CALL");
        return object;
    }
    public void btnblock(View view){setCurrentItem(2,true);

    setAnim(txtblock);
        txtblock.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_blockblue, 0, 0);
        txtblock.setTextColor(getResources().getColor(R.color.colot));
        txtcontact.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_contact_phone_black_24dp, 0, 0);
        txtcontact.setTextColor(getResources().getColor(R.color.black));
        txtcall.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_phone_black_24dp, 0, 0);
        txtcall.setTextColor(getResources().getColor(R.color.black));}
    public void btncon(View view){setCurrentItem(1,true);

        setAnim(txtcontact);
        txtblock.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_blockwhite, 0, 0);
        txtblock.setTextColor(getResources().getColor(R.color.black));
        txtcontact.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_contactblue, 0, 0);
        txtcontact.setTextColor(getResources().getColor(R.color.colot));
        txtcall.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_phone_black_24dp, 0, 0);
        txtcall.setTextColor(getResources().getColor(R.color.black));}
    public void btncall(View view){setCurrentItem(0,true);

        setAnim(txtcall);
        txtblock.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_blockwhite, 0, 0);
        txtblock.setTextColor(getResources().getColor(R.color.black));
        txtcontact.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_contact_phone_black_24dp, 0, 0);
        txtcontact.setTextColor(getResources().getColor(R.color.black));
        txtcall.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_phoneblue, 0, 0);
        txtcall.setTextColor(getResources().getColor(R.color.colot));}
        public void setAnim(TextView txt){
            Animation a = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animzoom);
            a.reset();
            txt.clearAnimation();
            txt.startAnimation(a);
        }
    public void btndrawer(View view){
        Animation a = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animzoomin);
        a.reset();
        view.clearAnimation();
        view.startAnimation(a);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawer(Gravity.LEFT);
                }else {
                    drawer.openDrawer(Gravity.LEFT);
                }
            }
        },300);

    }
    public ArrayList<String> setRecyclercontact(String text,boolean conrecycler) {
        call = new ArrayList<>();
        name = new ArrayList<>();
        number=new ArrayList<>();
        if (text.equals("")) {
        } else {

            String contactName = "";
            Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER}, ContactsContract.PhoneLookup.DISPLAY_NAME + " LIKE ? ", new String[]{"%" + text + "%"}, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    number.add(cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    if (name.contains(cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)))) {
                    } else {
                        name.add(cursor.getString(cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));

                        call.add(cursor.getString(cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) + ";" + cursor.getString(cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    }

                }
                cursor.close();
            }
            Cursor cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER}, ContactsContract.CommonDataKinds.Phone.NUMBER + " LIKE ? ", new String[]{"%" + text + "%"}, null);

            if (cursor2 != null) {
                while (cursor2.moveToNext()) {
                    number.add(cursor2.getString(cursor2.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    if (name.contains(cursor2.getString(cursor2.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)))) {
                    } else {
                        name.add(cursor2.getString(cursor2.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));

                        call.add(cursor2.getString(cursor2.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) + ";" + cursor2.getString(cursor2.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER)));
                        Log.e("setRecycler32", cursor2.getString(cursor2.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) + ";" + cursor2.getString(cursor2.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    }

                }
                cursor2.close();
            }
        }
        if (conrecycler){}
//        if (number.size()==0){
            number.add(text);
//        }

        return number;
    }
    public ArrayList<String> setRecyclercontact2(String text,boolean conrecycler,int dafe) {
        call = new ArrayList<>();
        name = new ArrayList<>();
        number=new ArrayList<>();
        if (text.equals("")) {
        } else {
            String limit="13";
            String offset= String.valueOf(Integer.parseInt(limit)*dafe);
            String contactName = "";
            Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER}, ContactsContract.PhoneLookup.DISPLAY_NAME + " LIKE ? ", new String[]{"%" + text + "%"}, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"+" limit "+"13"+" offset "+offset+" ");

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    number.add(cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    if (name.contains(cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)))) {
                    } else {
                        name.add(cursor.getString(cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));

                        call.add(cursor.getString(cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) + ";" + cursor.getString(cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    }

                }
                cursor.close();
            }
            Cursor cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER}, ContactsContract.CommonDataKinds.Phone.NUMBER + " LIKE ? ", new String[]{"%" + text + "%"}, null);

            if (cursor2 != null) {
                while (cursor2.moveToNext()) {
                    number.add(cursor2.getString(cursor2.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    if (name.contains(cursor2.getString(cursor2.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)))) {
                    } else {
                        name.add(cursor2.getString(cursor2.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));

                        call.add(cursor2.getString(cursor2.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) + ";" + cursor2.getString(cursor2.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER)));
                        Log.e("setRecycler32", cursor2.getString(cursor2.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) + ";" + cursor2.getString(cursor2.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    }

                }
                cursor2.close();
            }
        }
        if (conrecycler){}
        if (number.size()==0){
            number.add(text);
        }

        return call;
    }
    public void setreccall(final String text){
        id=0;
        RecyclerView recyclerViewgetcv=(RecyclerView)findViewById(R.id.recyclercall);
        calling=new ArrayList<String>();
        calling=setRecyclall(text, id,"8");
        viz=new ArrayList<String>();
        int io=0;
        while (io<call.size()){
            viz.add("0");
            io++;
        }
        Log.i("ringing234", String.valueOf(calling.size()) );
        final AdapterCardviewCall adapter = new AdapterCardviewCall(calling,getApplicationContext(),recyclerViewgetcv,viz);
        recyclerViewgetcv.setAdapter(adapter);
        LayoutAnimationController animation =
                AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_animation_fall_down);
        recyclerViewgetcv.setLayoutAnimation(animation);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewgetcv.setLayoutManager(layoutManager);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewgetcv.setLayoutManager(linearLayoutManager);
        recyclerViewgetcv.setOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                id++;
                ArrayList<String> lists=new ArrayList<String>();
                lists=setRecyclall(text,id,"5");
               // lists.add("+989,,null,,29/05/2020  ,,0,,29/05/2020  ");
                int i=0;
                while (i<lists.size()){
                    Log.i("Ex56447", lists.get(i));
                    calling.add(lists.get(i));
                    viz.add("0");
                    i++;
                }adapter.notifyDataSetChanged();





            }
        });
    }
    public void setrescontact(final String cotext){
        id=0;
        final RecyclerView recyclerViewgetcv=findViewById(R.id.recyclercall);
        contact=new ArrayList<String>();
        contact=setRecyclercontact2(cotext,true,id);
        final AdaptercardviewContact adapter = new AdaptercardviewContact(contact, getApplicationContext());
        recyclerViewgetcv.setAdapter(adapter);
        LayoutAnimationController animation =
                AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_animation_fall_down);
        recyclerViewgetcv.setLayoutAnimation(animation);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewgetcv.setLayoutManager(layoutManager);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewgetcv.setLayoutManager(linearLayoutManager);
        recyclerViewgetcv.setOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {

                id++;
                ArrayList<String> lists=new ArrayList<String>();
                lists=setRecyclercontact2(cotext,true,id);
                // lists.add("+989,,null,,29/05/2020  ,,0,,29/05/2020  ");
                int i=0;
                while (i<lists.size()){
                    Log.i("Ex56447", lists.get(i));
                    contact.add(lists.get(i));
                    i++;
                }
                adapter.notifyDataSetChanged();





            }
        });
    }
    public ArrayList<String> setRecyclall(String text,int dafe,String limit){
        ArrayList<String> numbers=new ArrayList<>();
//        numbers=setRecyclercontact(text,false);
        numbers.add(text);
        call = new ArrayList<>();
        name = new ArrayList<>();
        Log.e("Ex56444", String.valueOf(numbers.size()) );
        int i=0;
        String offset= String.valueOf(Integer.parseInt(limit)*dafe);
        Log.e("Ex56445", limit+":"+ offset);
        while (i<numbers.size()){
            if (text.equals("")) {} else {
                String sort1=" limit "+limit+" offset "+offset;
                String sort2=android.provider.CallLog.Calls.DATE + " DESC";
                Uri uri = Uri.parse(String.valueOf(CallLog.Calls.CONTENT_URI));
                Cursor c = getContentResolver().query(uri, new String[]{CallLog.Calls.NUMBER,CallLog.Calls.TYPE,CallLog.Calls.DATE,CallLog.Calls.DURATION}, CallLog.Calls.NUMBER + " LIKE ? ", new String[]{"%" + numbers.get(i) + "%"}, sort2 + sort1);
                Log.e("cursursize", String.valueOf(c.getCount())+":"+numbers.get(i)+":");
                try {

                    int number = c.getColumnIndex(CallLog.Calls.NUMBER);
                    int type = c.getColumnIndex(CallLog.Calls.TYPE);
                    int date = c.getColumnIndex(CallLog.Calls.DATE);
                    int duration = c.getColumnIndex(CallLog.Calls.DURATION);
                    String phNumber="",numbering="",callType="",callDate="",callDuration="";

                    while (c.moveToNext()) {
                        try {


                            phNumber = c.getString(number);
                            numbering = c.getString(number);
                            callType = c.getString(type);
                            callDate = c.getString(date);
                            Date callDayTime = new Date(Long.valueOf(callDate));
                            callDuration = c.getString(duration);
                            String dir = null;
                            int dircode = Integer.parseInt(callType);
                            switch (dircode) {
                                case CallLog.Calls.OUTGOING_TYPE:
                                    dir = "OUTGOING";
                                    break;
                                case CallLog.Calls.INCOMING_TYPE:
                                    dir = "INCOMING";
                                    break;
                                case CallLog.Calls.MISSED_TYPE:
                                    dir = "MISSED";
                                    break;
                            }
//                SimpleDateFormat spf=new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
//                Date newDate=spf.parse(String.valueOf(callDayTime).substring(4));
//                spf= new SimpleDateFormat("dd MMM yyyy");
//                String s = spf.format(newDate);
                            String formateDate = new SimpleDateFormat("dd/MM/yyyy  ").format(callDayTime);
                            Log.e("Exdfskdfjsod", formateDate );

//                    calls.add(formateDate);
                            Log.i("Ex56448", getContact(getApplicationContext(), phNumber) + ",," + dir + ",," + formateDate + ",," + callDuration + ",," + formateDate);
                            call.add(getContact(getApplicationContext(), phNumber) + ",," + dir + ",," + formateDate + ",," + callDuration + ",," + formateDate);
                        }catch (Exception e){

                        }

                    }

                }catch (Exception e){
                    Log.e("tagcall", e.toString() );

                }finally {
                    c.close();
                }
            } i++;
        }
        Log.i("Ex56449", String.valueOf(call.size()));
        return call;

    }


    public String getContact(Context context, String number){
        Log.i("contactnmeae", number);
        Uri uri=Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(number));

        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};

        String contactName=number;
        Cursor cursor=context.getContentResolver().query(uri,projection,null,null,null);

        if (cursor != null) {
            if(cursor.moveToFirst()) {
                contactName=cursor.getString(0);
            }
            cursor.close();
        }

        return contactName;
    }
}

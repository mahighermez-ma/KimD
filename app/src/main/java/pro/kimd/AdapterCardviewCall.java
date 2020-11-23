package pro.kimd;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import pro.kimd.R;

import java.util.ArrayList;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterCardviewCall extends RecyclerView.Adapter<AdapterCardviewCall.ViewHolder> {

    private ArrayList<String> calls;
    Context context;
    RecyclerView recyclerView;
    ArrayList<String> viz;

    public AdapterCardviewCall(ArrayList<String> calls, Context context, RecyclerView recyclerView,ArrayList<String> viz) {
        this.calls = calls;
        this.context = context;
        this.recyclerView = recyclerView;
        this.viz=viz;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardViieeww;

        public ViewHolder(CardView v) {
            super(v);
            cardViieeww = v;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(context).inflate(R.layout.callcardview, parent, false);

        return new ViewHolder(cv);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardView cardView = holder.cardViieeww;
        if (position != recyclerView.NO_POSITION) {


        Button btncall = (Button) cardView.findViewById(R.id.btncall);
        Button btnsms = (Button) cardView.findViewById(R.id.btnsms);
        TextView txtmark=(TextView)cardView.findViewById(R.id.txtmark);
        if (viz.get(position).equals("1")){
            Log.e("feerere", "1" );
            txtmark.setVisibility(View.VISIBLE);
        }else {
            txtmark.setVisibility(View.GONE);
        }

        btncall.setVisibility(View.VISIBLE);
        btnsms.setVisibility(View.VISIBLE);
            String name="",dir="",date="",dur="",numbering="";
            Log.e("stree", calls.get(position) );
                try {
                String[] callitem = calls.get(position).split(",,");
                name = callitem[0];
                dir = callitem[1];
                date = callitem[2];
                dur = callitem[3];
                numbering = callitem[4];

                }catch (Exception e){

                }

        TextView txtcallnumber = (TextView) cardView.findViewById(R.id.txtcallnumber);
        txtcallnumber.setText(name);
        ImageView imgdirection = (ImageView) cardView.findViewById(R.id.imgdirection);
        if (dir.equals("OUTGOING")) {
            imgdirection.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_call_made_black_24dp));
        } else if (dir.equals("INCOMING")) {
            imgdirection.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_call_received_black_24dp));
        } else {
            imgdirection.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_call_missed_black_24dp));
        }
        TextView txtdate = (TextView) cardView.findViewById(R.id.txtdate);
        txtdate.setText(date);

            final String finalNumbering = numbering;
            btncall.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", finalNumbering, null));
                if (context.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                }
                context.startActivity(intent);
            }
        });

            final String finalNumbering1 = numbering;
            btnsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", finalNumbering1);
                smsIntent.putExtra("sms_body", "");
                context.startActivity(smsIntent);


            }
        });
            TextView txtfirstchar=(TextView)cardView.findViewById(R.id.txtfirstchar);
            if (name.substring(0,1).equals("+")||name.substring(0,1).equals("0")||name.substring(0,1).equals("1")||name.substring(0,1).equals("2")||name.substring(0,1).equals("3")||name.substring(0,1).equals("4")||name.substring(0,1).equals("5")||name.substring(0,1).equals("6")||name.substring(0,1).equals("7")||name.substring(0,1).equals("8")||name.substring(0,1).equals("9")){
                txtfirstchar.setText("");

                switch (position%5){
                    case 0:
                        txtfirstchar.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_user_1));
                        break;
                    case 1:
                        txtfirstchar.setBackground(ContextCompat.getDrawable(context,R.drawable.sape1));
                        break;
                    case 2:
                        txtfirstchar.setBackground(ContextCompat.getDrawable(context,R.drawable.sape2));
                        break;
                    case 3:
                        txtfirstchar.setBackground(ContextCompat.getDrawable(context,R.drawable.sape4));
                        break;
                        default:
                            txtfirstchar.setBackground(ContextCompat.getDrawable(context,R.drawable.sape1));
                            }
            }else {
            txtfirstchar.setText(name.substring(0,1));
            switch (position%10){
                case 0:
                    txtfirstchar.setBackground(ContextCompat.getDrawable(context,R.drawable.shape10));
                    txtfirstchar.setTextColor(R.color.shape1);
                    break;
                case 1:
                    txtfirstchar.setBackground(ContextCompat.getDrawable(context,R.drawable.shape1));
                    txtfirstchar.setTextColor(R.color.shape1);
                    break;
                case 2:
                    txtfirstchar.setBackground(ContextCompat.getDrawable(context,R.drawable.shape2));
                    txtfirstchar.setTextColor(R.color.shape2);
                    break;
                case 3:
                    txtfirstchar.setBackground(ContextCompat.getDrawable(context,R.drawable.shape3));
                    txtfirstchar.setTextColor(R.color.shape3);
                    break;
                case 4:
                    txtfirstchar.setBackground(ContextCompat.getDrawable(context,R.drawable.shape4));
                    txtfirstchar.setTextColor(R.color.shape4);
                    break;
                case 5:
                    txtfirstchar.setBackground(ContextCompat.getDrawable(context,R.drawable.shape1));
                    txtfirstchar.setTextColor(R.color.shape1);
                    break;
                case 6:
                    txtfirstchar.setBackground(ContextCompat.getDrawable(context,R.drawable.shape6));
                    txtfirstchar.setTextColor(R.color.shape6);
                    break;
                case 7:
                    txtfirstchar.setBackground(ContextCompat.getDrawable(context,R.drawable.shape7));
                    txtfirstchar.setTextColor(R.color.shape7);
                    break;
                case 8:
                    txtfirstchar.setBackground(ContextCompat.getDrawable(context,R.drawable.shape8));
                    txtfirstchar.setTextColor(R.color.shape8);
                    break;
                case 9:
                    txtfirstchar.setBackground(ContextCompat.getDrawable(context,R.drawable.shape9));
                    txtfirstchar.setTextColor(R.color.shape9);
                    break;


            }}
    }
    }


//    }

    @Override
    public int getItemCount() {
        return calls.size();
    }

    public String getContact(Context context,String name){
        Cursor phone = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        try {
            while (phone.moveToNext()){
                if (phone.getString(phone.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)).equals(name)){

                    return phone.getString(phone.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
            }}catch (Exception e){
            Log.e("tagcall", e.toString());
        }
        return "";
    }
}

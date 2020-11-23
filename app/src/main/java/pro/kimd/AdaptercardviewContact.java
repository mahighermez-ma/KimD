package pro.kimd;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import pro.kimd.R;

import java.util.ArrayList;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptercardviewContact extends RecyclerView.Adapter<AdaptercardviewContact.ViewHolder> {

    private ArrayList<String> contact;
    private Context context;


    public AdaptercardviewContact(ArrayList<String> contact, Context context) {
        this.contact = contact;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardViieeww;
        public ViewHolder (CardView v){
            super(v);
            cardViieeww = v;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv;
//        if (viewType==1560){
//            cv = (CardView) LayoutInflater.from(context).inflate(R.layout.alphabetcardview, parent , false);
//        }else {
        cv = (CardView) LayoutInflater.from(context).inflate(R.layout.contactcardview, parent , false);
    //}
        return new ViewHolder(cv);
    }

    @Override
    public int getItemViewType(int position) {
        if (position==3){
            return 1560;
        }else {
        return super.getItemViewType(position);}
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardView cardView = holder.cardViieeww;
//        if (position==3){}else {
            TextView txtfirstchar=(TextView)cardView.findViewById(R.id.txtfirstchar);
            txtfirstchar.setText(contact.get(position).split(";")[0].substring(0,1));
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


            }
        TextView txtcontactname=(TextView)cardView.findViewById(R.id.txtcontactname);
        txtcontactname.setText(contact.get(position).split(";")[0]);
        Button btncall=(Button)cardView.findViewById(R.id.btncall);
        Button btnsms=(Button)cardView.findViewById(R.id.btnsms);
        btncall.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", contact.get(position).split(";")[1], null));
                if (context.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
                context.startActivity(intent);
            }
        });

        btnsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", contact.get(position).split(";")[1]);
                smsIntent.putExtra("sms_body","");
                context.startActivity(smsIntent);
            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,ShowContactactivity.class).putExtra("contact",contact.get(position)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

    }

    //}

    @Override
    public int getItemCount() {
        return contact.size();
    }}
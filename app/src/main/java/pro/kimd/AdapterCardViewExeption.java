package pro.kimd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import pro.kimd.R;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterCardViewExeption extends RecyclerView.Adapter<AdapterCardViewExeption.ViewHolder> {

    private ArrayList<String> Name;
    private ArrayList<String> Status;
    Context context;
    RecyclerView recyclerView;

    public AdapterCardViewExeption(ArrayList<String> name, ArrayList<String> status, Context context, RecyclerView recyclerView) {
        Name = name;
        Status = status;
        this.context = context;
        this.recyclerView = recyclerView;
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
        CardView cv = (CardView) LayoutInflater.from(context).inflate(R.layout.exeptioncardview, parent , false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardView cardView = holder.cardViieeww;
        TextView txtphone=cardView.findViewById(R.id.txtphone);
        final Switch switchstatus=cardView.findViewById(R.id.switchstatus);
        Button btntrash=cardView.findViewById(R.id.btntrash);
        txtphone.setText(Name.get(position));
        if (Status.get(position).equals("true")){
            switchstatus.setChecked(true);
        }
        switchstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExeptionDB exeptionDB=new ExeptionDB(context);
                if (switchstatus.isChecked()){
                exeptionDB.Updatelock(Name.get(position),"true");}else {
                    exeptionDB.Updatelock(Name.get(position),"false");
                }
            }
        });
        btntrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExeptionDB exeptionDB=new ExeptionDB(context);
                exeptionDB.deleteExeption(Name.get(position));
                //recycle refresh
                ArrayList<String> Name=new ArrayList<String>();
                ArrayList<String> Status=new ArrayList<String>();
                exeptionDB=new ExeptionDB(context);
                int i=0;
                while (i<exeptionDB.getexeption().size()){
                    Name.add(exeptionDB.getexeption().get(i).split(",,")[0]);
                    Status.add(exeptionDB.getexeption().get(i).split(",,")[1]);
                    i++;
                }
                AdapterCardViewExeption adapter = new AdapterCardViewExeption(Name,Status,context,recyclerView);
                recyclerView.setAdapter(adapter);
                LayoutAnimationController animation =
                        AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
                recyclerView.setLayoutAnimation(animation);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(layoutManager);
            }
        });

    }

    @Override
    public int getItemCount() {
        return Name.size();
    }}
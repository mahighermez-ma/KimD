package pro.kimd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import pro.kimd.R;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterCardviewsearch extends RecyclerView.Adapter<AdapterCardviewsearch.ViewHolder> {

    private ArrayList<String> namee;
    private ArrayList<String> number;
    Context context;

    public AdapterCardviewsearch(ArrayList<String> namee, ArrayList<String> number, Context context) {
        this.namee = namee;
        this.number = number;
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
        CardView cv = (CardView) LayoutInflater.from(context).inflate(R.layout.seachcardview, parent , false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardView cardView = holder.cardViieeww;
        TextView txtname=(TextView)cardView.findViewById(R.id.txtname);
        TextView txtnumber=cardView.findViewById(R.id.txtnumber);
        txtname.setText(namee.get(position));
        txtnumber.setText(number.get(position));


    }

    @Override
    public int getItemCount() {
        return namee.size();
    }}


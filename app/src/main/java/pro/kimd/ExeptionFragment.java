package pro.kimd;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.LinearLayout;

import pro.kimd.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExeptionFragment extends Fragment {
View view;

    public ExeptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_exeption, container, false);
        final ArrayList<String> Name=new ArrayList<String>();
        ArrayList<String> Status=new ArrayList<String>();
        final ExeptionDB exeptionDB=new ExeptionDB((ProgramActivity)getActivity());
        int i=0;
        while (i<exeptionDB.getexeption().size()){
            Name.add(exeptionDB.getexeption().get(i).split(",,")[0]);
            Status.add(exeptionDB.getexeption().get(i).split(",,")[1]);
            i++;
        }
        RecyclerView recyclerexeption=(RecyclerView)view.findViewById(R.id.recyclerexeption);
        AdapterCardViewExeption adapter = new AdapterCardViewExeption(Name,Status,(ProgramActivity)getActivity(),recyclerexeption);
        recyclerexeption.setAdapter(adapter);
        LayoutAnimationController animation =
                AnimationUtils.loadLayoutAnimation((ProgramActivity)getActivity(), R.anim.layout_animation_fall_down);
        recyclerexeption.setLayoutAnimation(animation);
        LinearLayoutManager layoutManager = new LinearLayoutManager((ProgramActivity)getActivity());
        recyclerexeption.setLayoutManager(layoutManager);
        FloatingActionButton fab=(FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View ew) {
                AlertDialog.Builder alert = new AlertDialog.Builder((ProgramActivity)getActivity());
                LinearLayout layout = new LinearLayout((ProgramActivity)getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(50,10,50,10);
                final EditText edtuser = new EditText((ProgramActivity)getActivity());
                edtuser.setHint("Your country code(like:+31)");
                final EditText edtpass = new EditText((ProgramActivity)getActivity());
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
                        ExeptionDB exeptionDB1=new ExeptionDB((ProgramActivity)getActivity());
                        exeptionDB1.InserExeption(code+Number,"true");
                        final ArrayList<String> Name=new ArrayList<String>();
                        ArrayList<String> Status=new ArrayList<String>();
                        final ExeptionDB exeptionDB=new ExeptionDB((ProgramActivity)getActivity());
                        int i=0;
                        while (i<exeptionDB.getexeption().size()){
                            Name.add(exeptionDB.getexeption().get(i).split(",,")[0]);
                            Status.add(exeptionDB.getexeption().get(i).split(",,")[1]);
                            i++;
                        }
                        RecyclerView recyclerexeption=(RecyclerView)view.findViewById(R.id.recyclerexeption);
                        AdapterCardViewExeption adapter = new AdapterCardViewExeption(Name,Status,(ProgramActivity)getActivity(),recyclerexeption);
                        recyclerexeption.setAdapter(adapter);
                        LayoutAnimationController animation =
                                AnimationUtils.loadLayoutAnimation((ProgramActivity)getActivity(), R.anim.layout_animation_fall_down);
                        recyclerexeption.setLayoutAnimation(animation);
                        LinearLayoutManager layoutManager = new LinearLayoutManager((ProgramActivity)getActivity());
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
        });
        // Inflate the layout for this fragment
        return view;
    }

}

package pro.kimd;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import pro.kimd.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.acra.config.ToastConfiguration;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {
    View view;
    ArrayList<String> contact,totalcontact;
    private FloatingActionButton fab3;
    private RecyclerView recyclerViewgetcv;

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_contact, container, false);
        try {
            totalcontact=new ArrayList<String>();
            SetContactslist setContactslist=new SetContactslist();
//            totalcontact=setContactslist.setit(getActivity());
            contact=new ArrayList<String>();
            contact=setContactslist.setit(getActivity(),"","1");
            recyclerViewgetcv= view.findViewById(R.id.recyclercontact);
            final AdaptercardviewContact adapter = new AdaptercardviewContact(contact, getActivity());
            recyclerViewgetcv.setAdapter(adapter);
            LayoutAnimationController animation =
                    AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_fall_down);
            recyclerViewgetcv.setLayoutAnimation(animation);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
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
                            fab3.setVisibility(View.GONE);
                        }else {
                            fab3.setVisibility(View.VISIBLE);
                        }
//                        Log.e("tyryhhehee", String.valueOf(position) );
//                        Toast.makeText(getActivity(), String.valueOf(position) , Toast.LENGTH_SHORT).show();
                        //onPageChanged(position);
//                    }
                }
            });
            Log.e("tyryhhehee", String.valueOf(recyclerViewgetcv.getVerticalScrollbarPosition()));
            fab3= view.findViewById(R.id.fab3);
            fab3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //setAnim(fab3);
                    recyclerViewgetcv.scrollToPosition(0);

                }
            });
            recyclerViewgetcv.setOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
                @Override
                public void onLoadMore(int current_page) {
                   
//                    fab3.setVisibility(View.VISIBLE);
                    // do something...
                    int size=contact.size();
//                    Toast.makeText(getActivity(), String.valueOf(size), Toast.LENGTH_SHORT).show();
                    SetContactslist setContactslist1=new SetContactslist();
                    Log.i("taging1245", String.valueOf(size));
                    ArrayList<String> lists=setContactslist1.setit(getActivity(),String.valueOf(size),String.valueOf(size));
                    int i=0;
                    while (i<lists.size()){
                        if (!contact.contains(lists.get(i))){
                            contact.add(lists.get(i));}
                        i++;
                    }
                    adapter.notifyDataSetChanged();

                }

            });
//            VerticalRecyclerViewFastScroller fastScroller = (VerticalRecyclerViewFastScroller) view.findViewById(R.id.fast_scroller);
//
//            // Connect the recycler to the scroller (to let the scroller scroll the list)
//            fastScroller.setRecyclerView(recyclerViewgetcv);
//
//            // Connect the scroller to the recycler (to let the recycler scroll the scroller's handle)
//            recyclerViewgetcv.setOnScrollListener(fastScroller.getOnScrollListener());

            FloatingActionButton fab2= view.findViewById(R.id.fab2);
            fab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addAsContactConfirmed(getActivity());
                }
            });

        }catch (Exception e){
            SendEror.sender(getActivity(),e.toString());
        }
        // Inflate the layout for this fragment
        return view;
    }
    public static void addAsContactConfirmed(final Context context) {

        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);


        context.startActivity(intent);

    }
    public ArrayList<String> getContacts(int size,int init){
        ArrayList<String> mylist=new ArrayList<String>();
        int i=0;
        while (i<size&&i<totalcontact.size()){
            if (i>=init){
                mylist.add(totalcontact.get(i));}
            i++;
        }
        return mylist;
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

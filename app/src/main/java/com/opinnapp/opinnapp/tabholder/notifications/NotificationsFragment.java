package com.opinnapp.opinnapp.tabholder.notifications;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opinnapp.opinnapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vzaffalon on 11/05/17.
 */

public class NotificationsFragment extends Fragment {

    private RecyclerView recyclerView;
    private Context context;
    private View view;
    private List<Notification> notifications;

    // newInstance constructor for creating fragment with arguments
    public static NotificationsFragment newInstance() {
        NotificationsFragment fragment = new NotificationsFragment();
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_notifications_recycler);
        generateNotifications();
        mountRecycler();
        return view;
    }

    private void mountRecycler() {
        if (notifications != null) {
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(new NotificationAdapter(notifications, context,new NotificationAdapter.OnItemClickListener() {
                @Override public void onItemClick(Notification item) {
                    /*FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, PerfilFragment.newInstance());
                    transaction.commit();*/
                }
            }));
        }
    }

    private void generateNotifications(){
        notifications = new ArrayList<>();
        notifications.add(new Notification("Sua pergunta expirou, confira o resultado.","2min","https://scontent-gru.xx.fbcdn.net/v/t1.0-9/602740_2839717690184_951506583_n.jpg?oh=9e9cdc3af65826e2db5a4384993a0f60&oe=599FA490",1));
        notifications.add(new Notification("Cayke começou a seguir você.","24min","https://scontent-gru.xx.fbcdn.net/v/t1.0-9/17308748_1279495765466119_5902661537556998459_n.jpg?oh=91cd66e6173a2312be22ac33d3ed4445&oe=59A3777D",2));
        notifications.add(new Notification("Victor começou a seguir você","45min","https://scontent-gru.xx.fbcdn.net/v/t1.0-9/1907867_967496356601123_5817813594957135474_n.jpg?oh=cecab31754466c2419254d03b1933dac&oe=59A63C29",3));
        notifications.add(new Notification("Sua pergunta expirou, confira o resultado.","60min","https://scontent-gru.xx.fbcdn.net/v/t1.0-9/602740_2839717690184_951506583_n.jpg?oh=9e9cdc3af65826e2db5a4384993a0f60&oe=599FA490",1));
    }
}

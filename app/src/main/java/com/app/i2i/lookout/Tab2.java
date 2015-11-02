package com.app.i2i.lookout;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class Tab2 extends Fragment {
    Context c;
    ListView listView;//List View object declaration
    ArrayAdapter<String> adapter;
    String[] text = {"WATER OUTAGE - 9.30", "POWER OUTAGE - 11.15", "ROAD WORK - 12.11", "TSTT SERVICE - 12.10"};
    Integer[] imageId = {R.drawable.ic_add, R.drawable.ic_add,
            R.drawable.ic_add, R.drawable.ic_add};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_2, container, false);
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fabAddNotice);
        fab.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                //Write here anything that you wish to do on click of FAB
                Toast.makeText(getActivity(), "hi", Toast.LENGTH_LONG).show();
            }


        });


        listView = (ListView) v.findViewById(R.id.listViewNotices);
        CustomNoticeAdapter adapter = new CustomNoticeAdapter(getActivity(), text, imageId);
        listView.setAdapter(adapter);


        return v;
    }
}
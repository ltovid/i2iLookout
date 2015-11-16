package com.app.i2i.lookout;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Tab3 extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_3, container, false);
        fabClicked(v);

        return v;
    }

    public void fabClicked(View v) {
        // write your code here ..
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fabCalendar);
        fab.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                //Write here anything that you wish to do on click of FAB
                Toast.makeText(getActivity(), "hi", Toast.LENGTH_LONG).show();
            }


        });
    }
}




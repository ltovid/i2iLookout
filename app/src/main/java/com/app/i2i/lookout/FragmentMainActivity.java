package com.app.i2i.lookout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentMainActivity extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v, c;
        v = inflater.inflate(R.layout.fragment_main, container, false);


        return v;
    }


}
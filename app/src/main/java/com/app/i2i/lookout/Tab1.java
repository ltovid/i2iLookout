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

public class Tab1 extends Fragment {

    Context c;
    ListView listView;//List View object declaration
    ArrayAdapter<String> adapter;
    String[] text = {"House of Whispers", "Hot Lunch", "Number of the Beast", "Killers", "Android Introduction", "Android Setup/Installation", "Android Hello World", "House of Whispers", "Hot Lunch", "Number of the Beast", "Killers"};
    Integer[] imageId = {R.drawable.ic_add, R.drawable.ic_add,
            R.drawable.ic_add, R.drawable.ic_add, R.drawable.ic_add,
            R.drawable.ic_add, R.drawable.ic_add, R.drawable.ic_add,
            R.drawable.ic_add, R.drawable.ic_add, R.drawable.ic_add};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_1, container, false);


        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fabAddNews);
        fab.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                //Write here anything that you wish to do on click of FAB
                Toast.makeText(getActivity(), "hi", Toast.LENGTH_LONG).show();
            }


        });


        listView = (ListView) v.findViewById(R.id.listView1);
        CustomAdapter adapter = new CustomAdapter(getActivity(), text, imageId);
        listView.setAdapter(adapter);


        return v;
    }


}

/*
public class Tab1 extends ListFragment {
    String[] web={
            "Cup Cake","Donut","Eclair",
            "Froyo","Gingerbread","Honey Comb",
            "Ice Cream Sandwich",
            "Jelly Beans","Kitkat",
            "Lolipop","Froyo","Gingerbread","Honey Comb",
            "Ice Cream Sandwich",
            "Jelly Beans"};

    Context c;
    Integer imageId[] = {R.drawable.ic_add, R.drawable.ic_add, R.drawable.ic_add, R.drawable.ic_add, R.drawable.ic_add, R.drawable.ic_add};
    ListView listView;//List View object declaration
    ArrayAdapter<String> adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.tab_1, container, false);


        return v;
       // return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onListItemClick(ListView list, View v, int position, long id) {

        Toast.makeText(getActivity(), getListView().getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
    }

}
*/

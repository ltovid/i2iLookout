package com.app.i2i.lookout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class FragmentMainActivity extends Fragment {

    Integer images[] = {R.drawable.missing, R.drawable.missing1, R.drawable.missing};
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v, c;
        v = inflater.inflate(R.layout.fragment_main, container, false);


        addImagesToThegallery(v);

        return v;
    }

    private void addImagesToThegallery(View c) {
        LinearLayout imageGallery = (LinearLayout) c.findViewById(R.id.imageGallery);
        for (Integer image : images) {
            imageGallery.addView(getImageView(image));

            imageGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "item clicked", Toast.LENGTH_LONG).show();
                }
            });

        }


    }


    private View getImageView(Integer image) {
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        lp.gravity = 0;
        lp.weight = 1;
        imageView.setLayoutParams(lp);
        imageView.setImageResource(image);
        return imageView;
    }

}
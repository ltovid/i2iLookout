package com.app.i2i.lookout;


import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.getbase.floatingactionbutton.FloatingActionsMenu;


public class FragmentMainActivity extends Fragment {
    public static final String EXTRA_NAME = null;
    public static final String EXTRA_EMAIL = null;
    FloatingActionsMenu fabMenu;

    Integer images[] = {R.drawable.missing1, R.drawable.missing, R.drawable.missing2};
    View.OnTouchListener ot;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v, c;
        v = inflater.inflate(R.layout.fragment_main, container, false);


        addFabMenu(v);
        addImagesToThegallery(v);


        return v;
    }

    public void addFabMenu(View f) {
        final FrameLayout frameLayout = (FrameLayout) f.findViewById(R.id.frame_layout);
        frameLayout.getBackground().setAlpha(0);

        fabMenu = (FloatingActionsMenu) f.findViewById(R.id.fab_menu);


        fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {


                frameLayout.getBackground().setAlpha(240);
                frameLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        fabMenu.collapse();
                        return true;
                    }
                });


            }

            @Override
            public void onMenuCollapsed() {
                frameLayout.getBackground().setAlpha(0);
                frameLayout.setOnTouchListener(null);
            }
        });

        reportAccident(f);
        reportCrime(f);
        reportCarTheft(f);
    }

    public void reportCrime(View crime) {
        final com.getbase.floatingactionbutton.FloatingActionButton fabCrime = (com.getbase.floatingactionbutton.FloatingActionButton) crime.findViewById(R.id.fab_robbery);
        fabCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fabMenu.collapse();
            }
        });
    }

    public void reportAccident(View accident) {
        final com.getbase.floatingactionbutton.FloatingActionButton fabAccident = (com.getbase.floatingactionbutton.FloatingActionButton) accident.findViewById(R.id.fab_crash);
        fabAccident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.file_report);


                dialog.show();


                fabMenu.collapse();
            }
        });
    }

    public void reportCarTheft(View carTheft) {
        final com.getbase.floatingactionbutton.FloatingActionButton fabCarTheft = (com.getbase.floatingactionbutton.FloatingActionButton) carTheft.findViewById(R.id.fab_car_theft);
        fabCarTheft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.file_report_car_theft);


                dialog.show();


                fabMenu.collapse();
            }
        });
    }
    private void addImagesToThegallery(View c) {
        final RelativeLayout imageGallery = (RelativeLayout) c.findViewById(R.id.imageGallery);
        for (final Integer image : images) {
            imageGallery.addView(getImageView(image));


        }
        // Instantiate a Builder object.
        //NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext());

        imageGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getContext(), bulletinDetailsActivity.class);

// Use TaskStackBuilder to build the back stack and get the PendingIntent
                PendingIntent pendingIntent =
                        TaskStackBuilder.create(getContext())
                                // add all of DetailsActivity's parents to the stack,
                                // followed by DetailsActivity itself
                                .addNextIntentWithParentStack(myIntent)
                                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext());
                builder.setContentIntent(pendingIntent);

                String ETName = "LL";
                String ETEmail = "HH";
                myIntent.putExtra(EXTRA_NAME, ETName);
                myIntent.putExtra(EXTRA_EMAIL, ETEmail);
                startActivity(myIntent);
            }
        });


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
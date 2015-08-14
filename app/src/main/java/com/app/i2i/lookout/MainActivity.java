package com.app.i2i.lookout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Stack;

public class MainActivity extends LoginActivity {
    public final int CATEGORY_ID = 0;
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Stack<Fragment> fragmentStack;
    Dialog dialog;
    /** Called when the activity is first created. */

    String[] android_versions = {
            "Community", "Services", "Events"};
    GridView grid;
    String[] web = {
            "Google",
            "Github",
            "Instagram",
            "Facebook",
            "Flickr",
            "Pinterest",

    };
    int[] imageId = {
            R.drawable.car_theft,
            R.drawable.burglary,
            R.drawable.robbery,
            R.drawable.flooding,
            R.drawable.shooting,
            R.drawable.pickpocket,
            R.drawable.warning};
    ListView listView;//List View object declaration
    ArrayAdapter<String> adapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentStack = new Stack<Fragment> ();

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Write here anything that you wish to do on click of FAB
                // Code to Add an item with default animation
                // Snackbar.make(v, "Hello Snackbar", Snackbar.LENGTH_LONG).show();

                // TODO Auto-generated method stub
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Alert Dialog");
                builder.setMessage("This is Example of Alert Dialog with three Buttons");

                CustomGrid adapter = new CustomGrid(MainActivity.this, web, imageId);
                //  grid=(GridView)findViewById(R.id.grid);
                grid.setAdapter(adapter);
                grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Toast.makeText(MainActivity.this, "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();

                    }
                });


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        Toast.makeText(getApplicationContext(), "Ok is clicked", Toast.LENGTH_LONG).show();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        Toast.makeText(getApplicationContext(), "Cancel is clicked", Toast.LENGTH_LONG).show();

                    }
                });
                builder.setNeutralButton("Close", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        Toast.makeText(getApplicationContext(), "Close is clicked", Toast.LENGTH_LONG).show();

                    }
                });
                builder.show(); //To show the AlertDialog


            }
        });


        listView = (ListView) findViewById(R.id.id_list_view);// object initialization

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android_versions);

        listView.setAdapter(adapter);//connect the adapter to list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            ArrayAdapter<String> adapter;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " is Selected", Toast.LENGTH_LONG).show();
            }
        });




        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.inbox:
                        Toast.makeText(getApplicationContext(), "Inbox Selected", Toast.LENGTH_SHORT).show();
                        ContentFragment fragment = new ContentFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.starred:
                        Toast.makeText(getApplicationContext(), "Stared Selected", Toast.LENGTH_SHORT).show();
                        return true;
                   /* case R.id.sent_mail:
                        Toast.makeText(getApplicationContext(), "Send Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.drafts:
                        Toast.makeText(getApplicationContext(), "Drafts Selected", Toast.LENGTH_SHORT).show();
                        return true;*/
                    case R.id.police:
                        Intent police = new Intent(Intent.ACTION_CALL, Uri.parse("tel:0000000000"));
                        try{
                        startActivity(police);
                    }
                        catch (android.content.ActivityNotFoundException ex){
                            Toast.makeText(getApplicationContext(),"yourActivity is not founded",Toast.LENGTH_SHORT).show();
                        }


                        return true;
                    case R.id.fire:
                        Intent fire = new Intent(Intent.ACTION_CALL, Uri.parse("tel:0000000000"));
                        try{
                            startActivity(fire);
                        }
                        catch (android.content.ActivityNotFoundException ex){
                            Toast.makeText(getApplicationContext(),"yourActivity is not founded",Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    case R.id.hospital:
                        Intent hospital = new Intent(Intent.ACTION_CALL, Uri.parse("tel:0000000000"));
                        try{
                            startActivity(hospital);
                        }
                        catch (android.content.ActivityNotFoundException ex){
                            Toast.makeText(getApplicationContext(),"yourActivity is not founded",Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}

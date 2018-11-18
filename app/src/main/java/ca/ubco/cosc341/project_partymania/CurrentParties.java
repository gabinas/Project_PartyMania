package ca.ubco.cosc341.project_partymania;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class CurrentParties extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    ArrayList<Button> buttons;
    LinearLayout linearLayout;

    private View.OnClickListener buttonClickListener = new View.OnClickListener(){
        public void onClick(View v){
            Object tag = v.getTag();
            ViewParty(tag);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_party);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        linearLayout = findViewById(R.id.linear_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        String name = menuItem.getTitle().toString();

                        switch (name){
                            case "Home":
                                finish();
                                break;
                            case "Current Parties":
                                Toast.makeText(CurrentParties.this,"No current parties", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                }
        );

        // create buttons for each party that is available
        try {
            FileInputStream fis= openFileInput("partyTitles.txt");
            InputStreamReader isr= new InputStreamReader(fis);
            BufferedReader br= new BufferedReader(isr);
            String line = br.readLine();

            // initialize length of array list
            buttons = new ArrayList<>();
            int i = 0;

            while (line != null) {
                line = br.readLine();
                buttons.add(new Button(this));
                buttons.get(i).setText(line);
                buttons.get(i).setText(line);
                buttons.get(i).setText(line);
                buttons.get(i).setText(line);
                buttons.get(i).setOnClickListener(buttonClickListener);
                linearLayout.addView(buttons.get(i));
            }
            br.close();
        } catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "No current parties. Create a new party!", Toast.LENGTH_LONG).show();
        }
    }

    private void ViewParty(Object tag){
        Toast.makeText(getApplicationContext(), "Hello. Nice try", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}

package ca.ubco.cosc341.project_partymania;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileOutputStream;

public class NewParty extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    EditText title;
    EditText location;
    Spinner day;
    Spinner mon;
    Spinner yea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_party);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        createSprinners();

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
                                Intent intent = new Intent(NewParty.this, CurrentParties.class);
                                finish();  //Kill the activity from which you will go to next activity
                                startActivity(intent);
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

    }

    public void createSprinners(){
        day = findViewById(R.id.day);
        mon = findViewById(R.id.month);
        yea = findViewById(R.id.year);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.day, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.month, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.year, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        day.setAdapter(adapter1);
        mon.setAdapter(adapter2);
        yea.setAdapter(adapter3);
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

    public void confirm(View view){
        //Getting the Title
        title = findViewById(R.id.title);
        String partyTitle = title.getText().toString();

        //Getting the Location
        location = findViewById(R.id.location);
        String partyLocation = location.getText().toString();

        //Getting the Date
        day = findViewById(R.id.day);
        mon = findViewById(R.id.month);
        yea = findViewById(R.id.year);

        String pday = day.getSelectedItem().toString();
        String pmonth = mon.getSelectedItem().toString();
        String pyear = yea.getSelectedItem().toString();

    }

    public void newParty(View view){
        String titlesFile = "partyTitles.txt";

        //Getting the title
        title = findViewById(R.id.title);
        String partyTitle = title.getText().toString();
        String fileContents = partyTitle+" \n"; //File Contents for the Titles File
        String fileName = partyTitle.replaceAll("\\s+","")+".txt";  //Name of the details file

        //Getting the Location
        location = findViewById(R.id.location);
        String partyLocation = location.getText().toString();

        //Getting the Date
        day = findViewById(R.id.day);
        mon = findViewById(R.id.month);
        yea = findViewById(R.id.year);

        String pday = day.getSelectedItem().toString();
        String pmonth = mon.getSelectedItem().toString();
        String pyear = yea.getSelectedItem().toString();


        FileOutputStream outputStream; //allow a file to be opened for writing
        try {
            outputStream= openFileOutput(titlesFile, Context.MODE_APPEND);
            outputStream.write(fileContents.getBytes());
            outputStream.close();

            fileContents = partyLocation+" \n "+pday+" \n "+pmonth+" \n "+pyear+" \n";
            outputStream= openFileOutput(fileName, Context.MODE_APPEND);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
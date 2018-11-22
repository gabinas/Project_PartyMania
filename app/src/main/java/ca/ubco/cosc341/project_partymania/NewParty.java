package ca.ubco.cosc341.project_partymania;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.util.Calendar;

public class NewParty extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    EditText title;
    EditText location;
    String date;
    private static final String TAG = "NewParty";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_party);

        mDrawerLayout = findViewById(R.id.drawer_layout);

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

        //DatePicker
        mDisplayDate = (TextView) findViewById(R.id.time);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        NewParty.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                date = day + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };
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


        if(partyTitle.length() > 0 && partyLocation.length()>0 && date.length()>0){
            newParty(view);
        } else{
            Toast.makeText(getApplicationContext(), "You must fill all fields", Toast.LENGTH_LONG).show();
        }

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
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                date = day + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };


        FileOutputStream outputStream; //allow a file to be opened for writing
        try {
            outputStream= openFileOutput(titlesFile, Context.MODE_APPEND);
            outputStream.write(fileContents.getBytes());
            outputStream.close();

            fileContents = partyTitle+" \n"+partyLocation+" \n"+date+" \n";
            outputStream= openFileOutput(fileName, Context.MODE_APPEND);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        finish();


    }


}
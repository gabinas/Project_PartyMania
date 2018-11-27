package ca.ubco.cosc341.project_partymania;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.FileOutputStream;

public class SendInvites2 extends AppCompatActivity {
    boolean timedate, location, potluck, message;
    String msg, partyName;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_invites2);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        Bundle extras = getIntent().getExtras();
        timedate = extras.getBoolean("timedate");
        location = extras.getBoolean("location");
        potluck = extras.getBoolean("potluck");
        message = extras.getBoolean("message");

        if(message)
            msg = extras.getString("msg");
        else
            msg = "";

        partyName = extras.getString("partyName");


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
                        Intent intent;
                        switch (name) {
                            case "Home":
                                intent = new Intent(SendInvites2.this, MainActivity.class);
                                finish();  //Kill the activity from which you will go to next activity
                                startActivity(intent);
                                break;
                            case "Current Parties":
                                intent = new Intent(SendInvites2.this, CurrentParties.class);
                                finish();  //Kill the activity from which you will go to next activity
                                startActivity(intent);
                                break;
                            case "Create Party":
                                intent = new Intent(SendInvites2.this, NewParty.class);
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

    public void Next(View view){
        Intent intent = new Intent(this, SendInvites3.class);

        EditText messagetext = findViewById(R.id.emailList);
        String emailList = messagetext.getText().toString();
        /**
        String fileContents = emailList.replaceAll("\\s+"," \n");
        String fileName = partyName.replaceAll("\\s+","")+".txt";

        FileOutputStream outputStream; //allow a file to be opened for writing
        try {
            outputStream= openFileOutput(fileName, Context.MODE_APPEND);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        **/
        Bundle bundle = new Bundle();
        bundle.putBoolean("timedate",timedate);
        bundle.putBoolean("location",location);
        bundle.putBoolean("potluck", potluck);
        bundle.putBoolean("message",message);
        bundle.putString("msg", msg);
        bundle.putString("emailList",emailList);
        bundle.putString("partyName",partyName);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void Previous(View view){

        finish();
    }

}

package ca.ubco.cosc341.project_partymania;

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
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SendInvites3 extends AppCompatActivity {
    boolean timedate, location, potluck, message;
    String msg, emailList, partyName, when, where, pot, messageExtra;
    private DrawerLayout mDrawerLayout;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_invites3);
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
        emailList = extras.getString("emailList");
        partyName = extras.getString("partyName");


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        ShowPreview();
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
                                intent = new Intent(SendInvites3.this, MainActivity.class);
                                finish();  //Kill the activity from which you will go to next activity
                                startActivity(intent);
                                break;
                            case "Current Parties":
                                intent = new Intent(SendInvites3.this, CurrentParties.class);
                                finish();  //Kill the activity from which you will go to next activity
                                startActivity(intent);
                                break;
                            case "Create Party":
                                intent = new Intent(SendInvites3.this, NewParty.class);
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
        String[] recipients = emailList.split(",");
        String partyNameInvite = "Invitation to "+partyName;

        Intent intent = new Intent(Intent.ACTION_SEND);

        EditText messagetext = findViewById(R.id.messagetext);
        String emailList = messagetext.getText().toString();
        Bundle bundle = new Bundle();

        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        startActivity(intent);

    }

    public void Previous(View view){
        finish();
    }

    public void ShowPreview(){
        TextView PTitle = findViewById(R.id.PTitle);
        PTitle.setText(partyName);
        String fileName = partyName.replaceAll("\\s+","")+".txt";
        try {
            FileInputStream fis= openFileInput(fileName);
            InputStreamReader isr= new InputStreamReader(fis);
            BufferedReader br= new BufferedReader(isr);
            String line = "";
            Toast.makeText(getApplicationContext(), "Attempting to load "+fileName, Toast.LENGTH_SHORT).show();

            String title = br.readLine();
            String where = br.readLine();
            String when = br.readLine() + " at "+br.readLine();
            // iterate through each line

            if (location) {
                TextView Where = findViewById(R.id.Where);
                Where.setText(where);
            }

            if(timedate) {
                TextView When = findViewById(R.id.Where);
                When.setText(when);
            }

            br.close();
        } catch (IOException e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        if(potluck){
            msg = msg + "\n Bring your own dish! Let's make this party a delicious one!";
        }
        if(message){
            TextView optionalmessage = findViewById(R.id.Message);
            optionalmessage.setText(msg);
        }
    }
}

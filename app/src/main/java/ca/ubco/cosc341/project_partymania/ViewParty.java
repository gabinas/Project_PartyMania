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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewParty extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    LinearLayout linearLayout;

    TextView name;
    TextView location;
    TextView date;
    TextView details;


    String filename;
    Button delete;
    Button invite;

   // ArrayList<String> data= new ArrayList<>();
    int j = 0;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_party);

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

                        switch (name) {
                            case "Home":
                                finish();
                                break;
                            case "Current Parties":
                                Intent intent = new Intent(ViewParty.this, CurrentParties.class);
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


        Intent intent = getIntent();
        String partyTitle = intent.getStringExtra("partyName");
        filename = partyTitle.replaceAll("\\s+","")+".txt";  //Name of the details file



        name = (TextView)findViewById(R.id.textName);
        location = (TextView)findViewById(R.id.textLocation);
        date = (TextView)findViewById(R.id.textDate);
        details = (TextView)findViewById(R.id.textDetails);

        invite = (Button)findViewById(R.id.sendInvites);
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSendInvites();
            }
        });

        try{
            FileInputStream fis= openFileInput(filename);
            InputStreamReader isr= new InputStreamReader(fis);
            BufferedReader br= new BufferedReader(isr);
            String line = br.readLine();
            list = new ArrayList<>();

            while (line != null) {
                list.add(line);
                Toast.makeText(getApplicationContext(), line, Toast.LENGTH_LONG).show();
                line = br.readLine();
            }
            br.close();

            name.setText(list.get(0));
            location.setText(list.get(1));
            date.setText(list.get(2));
//            details.setText(list.get(3));

        } catch (IOException e){
            e.printStackTrace();
        }

    }
    public void deleteParty(View view){
        File file = new File(getFilesDir(),filename+".txt");
        deleteFile(filename+".txt");
        Toast.makeText(getApplicationContext(),"File deleted",Toast.LENGTH_SHORT);
    }

    public void openSendInvites(){

        Intent intent = new Intent(this,SendInvites.class);
        Bundle bundle = new Bundle();
        bundle.putString("partyName",filename);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}

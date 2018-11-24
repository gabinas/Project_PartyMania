package ca.ubco.cosc341.project_partymania;

import android.content.Context;
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
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class CurrentParties extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_parties);

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
                            case "Create Party":
                                Intent intent = new Intent(CurrentParties.this, NewParty.class);
                                finish();
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

//        // dummy code to read in party titles
//        String fileContents= "MyParty\nMySecondPArty\nMyTurdParty";
//        FileOutputStream outputStream; //allow a file to be opened for writing
//        try {outputStream= openFileOutput("partyTitles.txt", Context.MODE_APPEND);
//            outputStream.write(fileContents.getBytes());
//            outputStream.close();
//            this.finish();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(getApplicationContext(), "Exception",Toast.LENGTH_SHORT).show();
//        }
        // create buttons for each party that is available
        try {
            FileInputStream fis= openFileInput("partyTitles.txt");
            InputStreamReader isr= new InputStreamReader(fis);
            BufferedReader br= new BufferedReader(isr);
            String line = br.readLine();

            int i = 0;


            // dynamically add buttons for parties
            while (line != null) {
                Button button = new Button(this);
               button.setText(line);
                button.setId(i);
                final int id_ = button.getId();
                final String partyName = button.getText().toString();
                LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                linearLayout.addView(button, lp);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        showParty(partyName);
                    }
                });
                i++;
                line = br.readLine();
            }
            br.close();
        } catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "No current parties. Create a new party!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CurrentParties.this, NewParty.class);
            finish();
            startActivity(intent);
        }
    }

    private void showParty(String partyName){
//        Toast.makeText(getApplicationContext(), "Going to " + partyName, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(CurrentParties.this , ViewParty.class);
        intent.putExtra("partyName", partyName);
        finish();
        startActivity(intent);
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

package ca.ubco.cosc341.project_partymania;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class SendInvites extends AppCompatActivity {
    boolean timedate, location, potluck, message;
    String partyName;
    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_invites);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        partyName =  bundle.getString("partyName");


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
                                intent = new Intent(SendInvites.this, MainActivity.class);
                                finish();  //Kill the activity from which you will go to next activity
                                startActivity(intent);
                                break;

                            case "Current Parties":
                                intent = new Intent(SendInvites.this, CurrentParties.class);
                                finish();  //Kill the activity from which you will go to next activity
                                startActivity(intent);
                                break;
                            case "Create Party":
                                intent = new Intent(SendInvites.this, NewParty.class);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Cancel(View view){
        finish();
    }

    public void Next(View view){

        Intent intent = new Intent(this, SendInvites2.class);
        EditText messagetext = null;
        messagetext = findViewById(R.id.messagetext);
        String msg = messagetext.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putBoolean("timedate",timedate);
        bundle.putBoolean("location",location);
        bundle.putBoolean("potluck", potluck);
        bundle.putBoolean("message",message);
        bundle.putString("msg", msg);
        bundle.putString("partyName", partyName);

        intent.putExtras(bundle);
        startActivity(intent);

    }

    //Checkbox implementation
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.timedate:
                if (checked)
                    timedate = true;
                else
                    timedate = false;
                break;
            case R.id.location:
                if (checked)
                    location = true;
                // Cheese me
                else
                    location = false;
                // I'm lactose intolerant
                break;

            case R.id.potluck:
                if(checked)
                    potluck = true;
                else
                    potluck = false;
                break;
            case R.id.message:
                if(checked)
                    message = true;
                else
                    message = false;
                break;

        }
    }


}

package ca.ubco.cosc341.project_partymania;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createParty(View view){
        Intent intent = new Intent(MainActivity.this, NewParty.class);
        startActivity(intent);
    }

    public void newParty(View view){
        Intent intent = new Intent(MainActivity.this, CurrentParties.class);
        startActivity(intent);
    }
}

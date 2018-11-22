package ca.ubco.cosc341.project_partymania;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    TextView name;
    TextView location;
    TextView date;
    TextView details;

    String text1;
    String text2;
    String text3;
    String text4;

    String filename;

    Button delete;
    Button invite;

    ArrayList<String> data= new ArrayList<>();
    int j = 0;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_party);

        Intent intent = getIntent();
        String filename = intent.getStringExtra("partyName");

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
            FileInputStream fis= openFileInput(filename + ".txt");
            InputStreamReader isr= new InputStreamReader(fis);
            BufferedReader br= new BufferedReader(isr);
            String line = br.readLine();
            while (line != null) {
                data.add(line);
                line = br.readLine();
            }
            br.close();
            list = new ArrayList<String>(Arrays.asList(data.get(j).split(",")));
            text1 = list.get(0);
            text2 = list.get(1);
            text3 = list.get(2);
            text4 = list.get(3);

            name.setText(text1);
            location.setText(text2);
            date.setText(text3);
            details.setText(text4);

        } catch (IOException e){
            e.printStackTrace();
        }

    }
    public void deleteParty(View view){
        File file = new File(getFilesDir(),filename+".txt");
        deleteFile(filename+".txt");
        Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT);
    }

    public void openSendInvites(){
        Intent intent = new Intent(this,SendInvites.class);
        startActivity(intent);
    }

}

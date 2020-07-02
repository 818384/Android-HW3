package edu.hcmus.hw3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Activity2 extends Activity {

    private TextView txtUsername;
    private TextView txtPassword;
    private TextView txtBirthdate;
    private TextView txtGender;
    private TextView txtHobbies;
    private Button btExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtPassword = (TextView) findViewById(R.id.txtPassword);
        txtBirthdate = (TextView) findViewById(R.id.txtBirthdate);
        txtGender = (TextView) findViewById(R.id.txtGender);
        txtHobbies = (TextView) findViewById(R.id.txtHobbies);
        btExit = (Button) findViewById(R.id.btExit);

        // Get data from Activity1.
        Intent myCallerIntent = getIntent();
        Bundle myBundle = myCallerIntent.getExtras();
        String username = myBundle.getString("username");
        String password = myBundle.getString("password");
        String gender = myBundle.getString("gender");
        String birthdate = myBundle.getString("birthdate");
        String hobbies[] = myBundle.getString("hobbies").split(",");
        String hobby = "";
        for (int i = 0; i < hobbies.length; i++){
            hobby += hobbies[i];
            if (i < hobbies.length - 1)
                hobby += ",";
        }

        // Appending to textview.
        txtUsername.append(username);
        txtPassword.append(password);
        txtBirthdate.append(birthdate);
        txtGender.append(gender);
        txtHobbies.append(hobby);

        // Exit Activity2.
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity1.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
            }
        });
    }
}

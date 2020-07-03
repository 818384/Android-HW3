package edu.hcmus.hw3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.xml.validation.Validator;

public class Activity1 extends Activity {

    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtRetype;
    private EditText edtBirthDate;
    private RadioGroup rbgGender;
    private RadioButton rbGender;
    private CheckBox cbTennis;
    private CheckBox cbFutbal;
    private CheckBox cbOthers;
    private Button btReset;
    private Button btSelect;
    private Button btSignup;

    final Calendar calendar = Calendar.getInstance();

    private void UpdateCalendar() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtBirthDate.setText(sdf.format(calendar.getTime()));
    }

    private int ValidatorData() {
        if (edtUsername.getText().toString().equals(null) || edtUsername.getText().toString().equals("")){
            return 2;
        }
        if (edtPassword.getText().toString().equals(edtRetype.getText().toString()) == false) {
            return 3;
        }
        if (edtPassword.getText().toString().isEmpty()){
            return 4;
        }
        if (edtRetype.getText().toString().isEmpty()){
            return 5;
        }
        return 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().getBooleanExtra("EXIT", false))
        {
            finish();
        }

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtRetype = (EditText) findViewById(R.id.edtRetype);
        edtBirthDate = (EditText) findViewById(R.id.edtBirthDate);
        rbgGender = (RadioGroup) findViewById(R.id.rbgGender);
        cbTennis = (CheckBox) findViewById(R.id.cbTennis);
        cbFutbal = (CheckBox) findViewById(R.id.cbFutbal);
        cbOthers = (CheckBox) findViewById(R.id.cbOthers);
        btReset = (Button) findViewById(R.id.btReset);
        btSelect = (Button) findViewById(R.id.btSelect);
        btSignup = (Button) findViewById(R.id.btSignup);



        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtUsername.getText().clear();
                edtPassword.getText().clear();
                edtRetype.getText().clear();
                edtBirthDate.getText().clear();
                int selectedIdRb = rbgGender.getCheckedRadioButtonId();
                rbGender = (RadioButton) findViewById(selectedIdRb);
                if (rbGender != null)
                    rbGender.setChecked(false);
                if (cbTennis.isChecked())
                    cbTennis.setChecked(false);
                if (cbFutbal.isChecked())
                    cbFutbal.setChecked(false);
                if (cbOthers.isChecked())
                    cbOthers.setChecked(false);
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                UpdateCalendar();
            }
        };
        btSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Activity1.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int resault = ValidatorData();
                if (resault == 2){
                    Toast.makeText(getApplicationContext(), "Username is empty", Toast.LENGTH_SHORT).show();
                }
                if (resault == 3){
                    Toast.makeText(getApplicationContext(), "Retype password is wrong", Toast.LENGTH_SHORT).show();
                }
                if (resault == 4){
                    Toast.makeText(getApplicationContext(), "Password is empty", Toast.LENGTH_SHORT).show();
                }
                if (resault == 5){
                    Toast.makeText(getApplicationContext(), "Retype is empty", Toast.LENGTH_SHORT).show();
                }
                if (resault == 1)
                {
                    // Get all data.
                    String username = edtUsername.getText().toString();
                    String password = edtPassword.getText().toString();
                    String birthdate = edtBirthDate.getText().toString();
                    int selectedIdRb = rbgGender.getCheckedRadioButtonId();
                    rbGender = (RadioButton) findViewById(selectedIdRb);
                    String gender = "";
                    if (rbGender != null)
                        gender = rbGender.getText().toString();
                    String hobbies = "";
                    if (cbTennis.isChecked())
                        hobbies += cbTennis.getText().toString() + ",";
                    if (cbFutbal.isChecked())
                        hobbies += cbFutbal.getText().toString() + ",";
                    if (cbOthers.isChecked())
                        hobbies += cbOthers.getText().toString();

                    // Send data to Activity2.
                    Intent myIntentA1A2 = new Intent (Activity1.this, Activity2.class);
                    Bundle myBundle1 = new Bundle();
                    myBundle1.putString ("username", username);
                    myBundle1.putString("password", password);
                    myBundle1.putString("birthdate", birthdate);
                    myBundle1.putString("gender", gender);
                    myBundle1.putString("hobbies", hobbies);
                    myIntentA1A2.putExtras(myBundle1);
                    startActivityForResult(myIntentA1A2, 1122);
                }
            }
        });
    }
}
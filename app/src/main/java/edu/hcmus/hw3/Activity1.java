package edu.hcmus.hw3;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Activity1 extends Activity implements View.OnClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().getBooleanExtra("EXIT", false)) {
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

        btReset.setOnClickListener(this);
        btSelect.setOnClickListener(this);
        btSignup.setOnClickListener(this);
    }

    private void updateCalendar() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtBirthDate.setText(sdf.format(calendar.getTime()));
    }

    private String validateData() {
        List<String> errors = new ArrayList<>();
        if (TextUtils.isEmpty(edtUsername.getText())) {
            errors.add("Username is empty");
        }
        if (TextUtils.isEmpty(edtPassword.getText())) {
            errors.add("Password is empty");
        }
        if (TextUtils.isEmpty(edtRetype.getText())) {
            errors.add("Retype is empty");
        }
        if (!edtPassword.getText().toString().equals(edtRetype.getText().toString())) {
            errors.add("Retype password is wrong");
        }
        if (errors.isEmpty()) {
            return "";
        }
        String result = "";
        for (int i = 0; i < errors.size(); i++) {
            result += errors.get(i);
            if (i < errors.size() - 1)
                result += ",";
        }

        return result;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btReset.getId()) {
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
        if (v.getId() == btSelect.getId()) {
            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateCalendar();
                }
            };

            new DatePickerDialog(Activity1.this, date, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();

        }
        if (v.getId() == btSignup.getId()) {
            String result = validateData();
            if (!result.isEmpty()) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            } else {
                // Get all data.
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
                Intent myIntentA1A2 = new Intent(Activity1.this, Activity2.class);
                Bundle myBundle1 = new Bundle();
                myBundle1.putString("username", edtUsername.getText().toString());
                myBundle1.putString("password", edtPassword.getText().toString());
                myBundle1.putString("birthdate", edtBirthDate.getText().toString());
                myBundle1.putString("gender", gender);
                myBundle1.putString("hobbies", hobbies);
                myIntentA1A2.putExtras(myBundle1);
                startActivityForResult(myIntentA1A2, 1122);
            }
        }
    }
}
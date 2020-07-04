package edu.hcmus.hw3;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Activity1 extends Activity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtRetype;
    private EditText edtBirthDate;
    private RadioButton rbMale;
    private RadioButton rbFeMale;
    private CheckBox cbTennis;
    private CheckBox cbFutbal;
    private CheckBox cbOthers;
    private Button btReset;
    private Button btSelect;
    private Button btSignup;
    private final String BIRTHDATE_FORMAT = "dd/MM/yyyy";
    private final Calendar calendar = Calendar.getInstance();

    private DateFormat df = new SimpleDateFormat(BIRTHDATE_FORMAT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        df.setLenient(false);
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtRetype = (EditText) findViewById(R.id.edtRetype);
        edtBirthDate = (EditText) findViewById(R.id.edtBirthDate);
        edtBirthDate.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
        rbMale = (RadioButton) findViewById(R.id.rbMale);
        rbFeMale = (RadioButton) findViewById(R.id.rbFeMale);
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
        if (TextUtils.isEmpty(edtBirthDate.getText())) {
            errors.add("Birthdate is empty");
        } else {
            try {
                df.parse(edtBirthDate.getText().toString()).toString();
            } catch (ParseException e) {
                errors.add("Please input the right birthdate format " + BIRTHDATE_FORMAT);
            }

        }
        if(!rbFeMale.isChecked() && !rbMale.isChecked()){
            errors.add("Please pick a gender");
        }
        return TextUtils.join(System.lineSeparator(), errors);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btSelect.getId()) {
            new DatePickerDialog(Activity1.this, this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH))
                    .show();
        }

        List<EditText> editTexts = Arrays.asList(edtUsername, edtPassword, edtRetype, edtBirthDate);
        List<RadioButton> radioButtons = Arrays.asList(rbMale, rbFeMale);
        List<CheckBox> checkBoxes = Arrays.asList(cbFutbal, cbOthers, cbTennis);

        if (v.getId() == btReset.getId()) {
            for (EditText e : editTexts) {
                e.getText().clear();
            }
            for (RadioButton r : radioButtons) {
                r.setChecked(false);
            }
            for (CheckBox c : checkBoxes) {
                c.setChecked(false);
            }

        }

        if (v.getId() == btSignup.getId()) {
            String result = validateData();
            if (!result.isEmpty()) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            } else {
                // Get all data.
                Bundle bundle = new Bundle();
                bundle.putString("username", edtUsername.getText().toString());
                bundle.putString("password", edtPassword.getText().toString());
                bundle.putString("birthdate", edtBirthDate.getText().toString());

                for (RadioButton r : radioButtons) {
                    if (r.isChecked()) {
                        bundle.putString("gender", r.getText().toString());
                    }
                }
                List<String> checkedHobbies = new ArrayList<>();
                for (CheckBox c : checkBoxes) {
                    if (c.isChecked()) {
                        checkedHobbies.add(c.getText().toString());
                    }
                }
                bundle.putString("hobbies", TextUtils.join(",", checkedHobbies));

                // Send data to Activity2.
                Intent myIntentA1A2 = new Intent(Activity1.this, Activity2.class);
                myIntentA1A2.putExtras(bundle);
                startActivityForResult(myIntentA1A2, 1122);
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        edtBirthDate.setText(df.format(calendar.getTime()));
    }
}
package com.saha.sahamvp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class RegisterActivity extends Activity {

    // TODO: datepicker mask
    // TODO: populate country list
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    public void registrationFailed(String errorMessage)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setMessage(errorMessage)
                .setNegativeButton("إعادة المحاولة", null)
                .create()
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        new AppEULA(this).show();

        final EditText etFirstName = (EditText) findViewById(R.id.etFirstName);
        final EditText etLastName = (EditText) findViewById(R.id.etLastName);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etEmailConfirm = (EditText) findViewById(R.id.etEmailConfirm);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etPasswordConfirm = (EditText) findViewById(R.id.etPassword);
        final EditText etAge = (EditText) findViewById(R.id.etAge);
        final EditText etPhone = (EditText) findViewById(R.id.etPhone);
        final EditText etCountry = (EditText) findViewById(R.id.etCountry);
        final CheckBox cbInCamp = (CheckBox) findViewById(R.id.cbInCamp);
        final Button bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstName = etFirstName.getText().toString();
                final String lastName = etLastName.getText().toString();
                final String email = etEmail.getText().toString();
                final String emailConfirm = etEmailConfirm.getText().toString();
                final String password = etPassword.getText().toString();
                final String passwordConfirm = etPasswordConfirm.getText().toString();
                final String age = etAge.getText().toString();
                final String phone = etPhone.getText().toString();
                final String country = etCountry.getText().toString();
                final String status = ((cbInCamp.isChecked()) ? "true" : "false");

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                if (!(email.equals(emailConfirm)) || !(password.equals(passwordConfirm)))
                                {
                                    registrationFailed("لا تتطابق إدخالات");

                                }
                                else if (!checkEmail(emailConfirm))
                                {
                                    registrationFailed("البريد الإلكتروني غير صالح");
                                }
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    Intent questionIntent = new Intent(RegisterActivity.this, QuestionActivity.class);
                                    RegisterActivity.this.startActivity(questionIntent);
                                } else {
                                    registrationFailed("فشل في التسجيل");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    RegisterRequest registerRequest = new RegisterRequest(firstName, lastName, email, password, age, phone, country, status, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
            }
        });

    }
}

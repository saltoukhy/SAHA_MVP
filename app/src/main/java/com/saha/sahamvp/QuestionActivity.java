package com.saha.sahamvp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class QuestionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        final EditText etDescription = (EditText) findViewById(R.id.etDescription);
        final Button bSubmit = (Button) findViewById(R.id.bSubmit);
        final TextView textView=(TextView)findViewById(R.id.tvSuccess);
        final Button bLogout = (Button) findViewById(R.id.bLogout);


        Intent questionIntent = getIntent();
        final String userId = questionIntent.getStringExtra("userId");
        final String firstName = questionIntent.getStringExtra("firstName");
        final String lastName = questionIntent.getStringExtra("lastName");
        Boolean success = questionIntent.getBooleanExtra("success", false);
        if (success) textView.setText("تلقينا سؤالك");

        bLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent loginActivity = new Intent(QuestionActivity.this, LoginActivity.class);
                QuestionActivity.this.startActivity(loginActivity);
            }
        });

        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final String description = etDescription.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Intent questionIntent = new Intent(QuestionActivity.this, QuestionActivity.class);
                                questionIntent.putExtra("userId", userId);
                                questionIntent.putExtra("firstName", firstName);
                                questionIntent.putExtra("lastName", lastName);
                                questionIntent.putExtra("success", true);
                                QuestionActivity.this.startActivity(questionIntent);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(QuestionActivity.this);
                                builder.setMessage("Submission Failed")
                                        .setNegativeButton("إعادة المحاولة", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                QuestionRequest questionRequest = new QuestionRequest(userId, description, responseListener);
                RequestQueue queue = Volley.newRequestQueue(QuestionActivity.this);
                queue.add(questionRequest);
            }
        });
    }
}

package com.saha.sahamvp;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by saltou200 on 1/29/17.
 */

public class QuestionRequest extends StringRequest{

    private static final String QUESTION_REQUEST_URL = "https://untimbered-restrain.000webhostapp.com/question.php";
    private Map<String, String> params;

    public QuestionRequest(String userId, String description, Response.Listener<String> listener) {
        super(Request.Method.POST, QUESTION_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("user_id", userId);
        params.put("description", description);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

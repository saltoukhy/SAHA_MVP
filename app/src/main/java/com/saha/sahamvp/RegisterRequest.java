package com.saha.sahamvp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by saltou200 on 1/23/17.
 */

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://untimbered-restrain.000webhostapp.com/register.php";
    private Map<String, String> params;

    public RegisterRequest(String firstname, String lastname, String email, String password, String age, String phone, String country, String status, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("first_name", firstname);
        params.put("email", email);
        params.put("last_name", lastname);
        params.put("password", password);
        params.put("age", age);
        params.put("phone", phone);
        params.put("country", country);
        params.put("status", status);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

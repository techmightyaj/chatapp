package in.techmighty.chatapp.custom;

/**
 * Created by ankit varia on 30/05/16.
 */


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import in.techmighty.chatapp.application.ApplicationClass;


public class CustomJSONVolleyRequest extends JsonObjectRequest {
    private static final String TAG = CustomJSONVolleyRequest.class.getSimpleName();


    public CustomJSONVolleyRequest(int method, String url, JSONObject jsonRequest,
                                   Response.Listener<JSONObject> listener,
                                   Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        // here you can write a custom retry policy and return it
        return new DefaultRetryPolicy(ApplicationClass.VOLLEY_TIMEOUT,
                ApplicationClass.VOLLEY_NUMBER_OF_ATTEMPTS,
                ApplicationClass.VOLLEY_BACK_OF_MULTIPLIER);
    }

}

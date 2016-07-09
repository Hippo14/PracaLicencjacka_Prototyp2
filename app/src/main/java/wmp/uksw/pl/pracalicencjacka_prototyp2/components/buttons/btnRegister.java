package wmp.uksw.pl.pracalicencjacka_prototyp2.components.buttons;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import wmp.uksw.pl.pracalicencjacka_prototyp2.AppController;
import wmp.uksw.pl.pracalicencjacka_prototyp2.MenuActivity;
import wmp.uksw.pl.pracalicencjacka_prototyp2.R;
import wmp.uksw.pl.pracalicencjacka_prototyp2.conf.URL;
import wmp.uksw.pl.pracalicencjacka_prototyp2.constants.accountTypes;
import wmp.uksw.pl.pracalicencjacka_prototyp2.helpers.SessionManager;
import wmp.uksw.pl.pracalicencjacka_prototyp2.helpers.VolleyErrorHelper;
import wmp.uksw.pl.pracalicencjacka_prototyp2.user.ProfileUser;

/**
 * Created by MSI on 2016-07-09.
 */
public class BtnRegister extends Button implements iButton {

    SessionManager sessionManager;
    Context context;

    public BtnRegister(Context context) {
        super(context);
        init(context);
    }
    public BtnRegister(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BtnRegister(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        sessionManager = new SessionManager(context);
        this.context = context;
    }


    @Override
    public void onClick(String name, String email, String password) {
        // Register user
        registerUser(name, email, accountTypes.ACCOUNT_EMAIL, password);
    }

    @Override
    public void onClick(String email, String password) {

    }

    private void registerUser(final String name, final String email, final String accountType, final String password) {
        String tag_string_req = "req_register";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        sessionManager.setLogin(true);

                        // Store user in sessionManager
                        JSONObject user = jObj.getJSONObject("user");

                        ProfileUser profileUser = new ProfileUser(user.getString("name"), user.getString("email"), user.getString("accountType"), user.getString("password"));
                        sessionManager.setProfileUser(profileUser);


                        // Launch main activity
                        Intent intent = new Intent(context,
                                MenuActivity.class);
                        getContext().startActivity(intent);
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Snackbar.make(findViewById(R.id.layoutRegister),
                                errorMsg, Snackbar.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Snackbar.make(getRootView(), "Json error: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(getRootView(),
                        VolleyErrorHelper.getMessage(error, context), Snackbar.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("accountType", accountType);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}

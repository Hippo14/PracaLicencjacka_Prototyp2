package wmp.uksw.pl.pracalicencjacka_prototyp2.components.buttons;

import android.app.ProgressDialog;
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
import wmp.uksw.pl.pracalicencjacka_prototyp2.RegisterActivity;
import wmp.uksw.pl.pracalicencjacka_prototyp2.conf.URL;
import wmp.uksw.pl.pracalicencjacka_prototyp2.constants.accountTypes;
import wmp.uksw.pl.pracalicencjacka_prototyp2.helpers.SessionManager;
import wmp.uksw.pl.pracalicencjacka_prototyp2.helpers.VolleyErrorHelper;
import wmp.uksw.pl.pracalicencjacka_prototyp2.user.ProfileUser;

/**
 * Created by MSI on 2016-07-09.
 */
public class BtnLogin extends Button implements iButton {

    ProgressDialog progressDialog;
    SessionManager sessionManager;
    Context context;


    public BtnLogin(Context context) {
        super(context);
        init(context);
    }

    public BtnLogin(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BtnLogin(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    @Override
    public void onClick(String name, String email, String password) {

    }

    @Override
    public void onClick(String email, String password) {
        loginUser(email, password, accountTypes.ACCOUNT_EMAIL);
    }

    private void init(Context context) {
        sessionManager = new SessionManager(context);
        this.context = context;

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void loginUser(final String email, final String password, final String accountType) {
        String tag_string_req = "req_login";

        progressDialog.setMessage("Logging in...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        sessionManager.setLogin(true);

                        // Set user data
                        JSONObject user = jObj.getJSONObject("user");
                        ProfileUser profileUser = new ProfileUser(user.getString("name"), user.getString("email"), user.getString("accountType"), user.getString("password"));
                        sessionManager.setProfileUser(profileUser);

                        // Launch main activity
                        Intent intent = new Intent(context,
                                MenuActivity.class);
                        context.startActivity(intent);
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Snackbar.make(getRootView(),
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
                hideDialog();
                Snackbar.make(getRootView(),
                        VolleyErrorHelper.getMessage(error, context), Snackbar.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}

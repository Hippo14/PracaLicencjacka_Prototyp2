package wmp.uksw.pl.pracalicencjacka_prototyp2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import wmp.uksw.pl.pracalicencjacka_prototyp2.components.buttons.BtnLogin;
import wmp.uksw.pl.pracalicencjacka_prototyp2.components.editText.InputText;
import wmp.uksw.pl.pracalicencjacka_prototyp2.conf.URL;
import wmp.uksw.pl.pracalicencjacka_prototyp2.helpers.VolleyErrorHelper;
import wmp.uksw.pl.pracalicencjacka_prototyp2.template.MyActivityTemplate;
import wmp.uksw.pl.pracalicencjacka_prototyp2.user.ProfileUser;

public class LoginActivity extends MyActivityTemplate implements View.OnClickListener {

    BtnLogin buttonLogin;
    TextView buttonRegister;

    InputText inputEmail;
    InputText inputPassword;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = (InputText) findViewById(R.id.email);
        inputPassword = (InputText) findViewById(R.id.password);

        buttonLogin = (BtnLogin) findViewById(R.id.buttonsignin);
        buttonRegister = (TextView) findViewById(R.id.signin);

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        buttonLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);

        // Check if user is already logged in or not
        if (sessionManager.getLogin()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void onClickLogin() {
        buttonLogin.onClick(inputEmail.getInputText(), inputPassword.getInputText());
    }

    private void onClickRegister() {
        Intent it = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(it);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_register;
    }

    @Override
    protected Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonLogin) {
            onClickLogin();
        }
        else if (v == buttonRegister) {
            onClickRegister();
        }
    }
}

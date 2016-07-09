package wmp.uksw.pl.pracalicencjacka_prototyp2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


import wmp.uksw.pl.pracalicencjacka_prototyp2.components.buttons.BtnRegister;
import wmp.uksw.pl.pracalicencjacka_prototyp2.components.editText.InputText;
import wmp.uksw.pl.pracalicencjacka_prototyp2.helpers.Validate;
import wmp.uksw.pl.pracalicencjacka_prototyp2.template.MyActivityTemplate;

public class RegisterActivity extends MyActivityTemplate implements View.OnClickListener {

    private InputText inputName;
    private InputText inputEmail;
    private InputText inputPassword;
    private ProgressDialog progressDialog;

    private TextView btnLogin;
    private BtnRegister buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inputName = (InputText) findViewById(R.id.user);
        inputEmail = (InputText) findViewById(R.id.email);
        inputPassword = (InputText) findViewById(R.id.password);

        btnLogin = (TextView) findViewById(R.id.signup);
        buttonRegister = (BtnRegister) findViewById(R.id.createaccount);

        buttonRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        // Check if user is already logged in or not
        if (sessionManager.getLogin()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegisterActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }

        // Validation formula
//        registerViews();
    }

    private void onClickLogin() {
        Intent it = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(it);
    }

    private void registerViews() {
        inputName.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                Validate.isEmailAddress(inputName, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        inputEmail.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                Validate.isEmailAddress(inputEmail, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        inputPassword.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                Validate.isPassword(inputPassword, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_register;
    }

    @Override
    protected Context getContext() {
        return getApplicationContext();
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            onClickLogin();
        }
        else if (v == buttonRegister) {
            progressDialog.setMessage("Creating account...");
            showDialog();
            buttonRegister.onClick(inputEmail.getText().toString(), inputName.getText().toString(), inputPassword.getText().toString());
            hideDialog();
        }
    }

}
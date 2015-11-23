package wmp.uksw.pl.pracalicencjacka_prototyp2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import wmp.uksw.pl.pracalicencjacka_prototyp2.constants.accountTypes;
import wmp.uksw.pl.pracalicencjacka_prototyp2.template.MyActivityTemplate;
import wmp.uksw.pl.pracalicencjacka_prototyp2.user.EmailUser;
import wmp.uksw.pl.pracalicencjacka_prototyp2.user.ProfileUser;

public class RegisterActivity extends MyActivityTemplate implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 0;

    private GoogleApiClient mGoogleApiClient;
    private CallbackManager callbackManager;

    private Button btnEmailRegister;
    private SignInButton btnGooglePlusRegister;
    private LoginButton btnFacebookRegister;

    // EMAIL  USER
    private EmailUser emailUser;

    // GOOGLE PLUS USER
    private GoogleSignInAccount googleplusUser;

    // FACEBOOK USER
    private JSONObject facebookJSON;
    private AccessToken facebookAccessToken;
    private Profile facebookProfile;

    // APPLICATION USER
    private ProfileUser profileUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callbackManager = CallbackManager.Factory.create();


        btnEmailRegister = (Button) findViewById(R.id.btnEmailRegister);



        // Button click listeners
        btnEmailRegister.setOnClickListener(this);

        facebookLogIn();
        googleplusLogIn();
    }

    public void facebookLogIn() {
        btnFacebookRegister = (LoginButton) findViewById(R.id.btnFacebookRegister);


        // Facebook button permissions
        btnFacebookRegister.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email", "user_birthday"));

        // Callback registration
        btnFacebookRegister.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code
                                facebookJSON = response.getJSONObject();
                                facebookAccessToken = loginResult.getAccessToken();
                                facebookProfile = Profile.getCurrentProfile();

                                // TODO New activity
                                profileUser = new ProfileUser(facebookJSON, facebookProfile, accountTypes.ACCOUNT_FACEBOOK);

                                sessionManager.clearSession();
                                sessionManager.setProfileUser(profileUser);

                                Intent intent = new Intent(RegisterActivity.this, MenuActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, link, gender, birthday, email");
                request.setParameters(parameters);
                request.executeAsync();

                Log.e("Parameters", request.getParameters().getString("fields"));
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });
        btnFacebookRegister.setOnClickListener(this);
    }

    public void googleplusLogIn() {
        btnGooglePlusRegister = (SignInButton) findViewById(R.id.btnGooglePlusRegister);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        btnGooglePlusRegister.setOnClickListener(this);
    }

    public static String printHashKey(Context ctx) {
        // Add code to print out the key hash
        try {
            PackageInfo info = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.DEFAULT);
            }
        } catch (PackageManager.NameNotFoundException e) {
            return "SHA-1 generation: the key count not be generated: NameNotFoundException thrown";
        } catch (NoSuchAlgorithmException e) {
            return "SHA-1 generation: the key count not be generated: NoSuchAlgorithmException thrown";
        }

        return "SHA-1 generation: epic failed";
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGooglePlusRegister:
                googlePlusSignIn();
                break;
            case R.id.btnEmailRegister:
                emailRegister();
                break;
            case R.id.btnEmailSignIn:
                emailSignIn();
                break;
            case R.id.btnFacebookRegister:
                facebookSignIn();
                break;
        }
    }

    private void emailSignIn() {
        TextView name = (TextView) findViewById(R.id.tvName);
        TextView email = (TextView) findViewById(R.id.tvEmail);
        TextView password = (TextView) findViewById(R.id.tvPassword);

        // TODO New activity
        ProfileUser profileUser = new ProfileUser(name, email, password, accountTypes.ACCOUNT_EMAIL);

        sessionManager.clearSession();
        sessionManager.setProfileUser(profileUser);
    }

    private void emailRegister() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutRegister);
        if (linearLayout.getVisibility() == View.VISIBLE)
            linearLayout.setVisibility(View.INVISIBLE);
        else
            linearLayout.setVisibility(View.VISIBLE);


    }

    private void facebookSignIn() {

    }

    private void googlePlusSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            googleplusUser = result.getSignInAccount();

            // TODO New activity
            profileUser = new ProfileUser(googleplusUser, accountTypes.ACCOUNT_GOOGLEPLUS);

            sessionManager.clearSession();
            sessionManager.setProfileUser(profileUser);

            Intent intent = new Intent(RegisterActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle arg0) {
        // Update the UI after signin
        updateUI(true);
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
        updateUI(false);
    }

    /**
     * Updating the UI, showing/hiding buttons and profile layout
     */
    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {

        } else {

        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_register;
    }

    @Override
    protected Context getContext() {
        return getApplicationContext();
    }
}

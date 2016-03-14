package wmp.uksw.pl.pracalicencjacka_prototyp2.user;

import android.widget.TextView;

import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MSI on 2015-11-21.
 */
public class ProfileUser {

    private String name;
    private String email;
    private String password;
    private String accountType;

    public ProfileUser(JSONObject facebookJSON, Profile facebookProfile, String accountType) {
        try {
            this.name = facebookJSON.getString("name");
            this.email = facebookJSON.getString("email");
            this.accountType = accountType;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ProfileUser(GoogleSignInAccount googleplusUser, String accountType) {
        this.name = googleplusUser.getDisplayName();
        this.email = googleplusUser.getEmail();
        this.accountType = accountType;
    }

    public ProfileUser(TextView name, TextView email, TextView password, String accountType) {
        this.name = name.getText().toString();
        this.email = email.getText().toString();
        this.password = password.getText().toString();
        this.accountType = accountType;
    }

    public Map<String, String> buildMapToValidate(ProfileUser profileUser) {
        Map<String, String> params = new HashMap<>();

        params.put("name", profileUser.getName());
        params.put("email", profileUser.getEmail());
        params.put("accountType", profileUser.getAccountType());
        if (profileUser.getPassword() == null)
            params.put("password", "");
        else
            params.put("password", profileUser.getPassword());

        return params;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getPassword() {
        return password;
    }
}

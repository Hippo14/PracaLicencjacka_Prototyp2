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

    public ProfileUser(String name, String email, String password, String accountType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.accountType = accountType;
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

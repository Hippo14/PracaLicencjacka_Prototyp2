package wmp.uksw.pl.pracalicencjacka_prototyp2.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import wmp.uksw.pl.pracalicencjacka_prototyp2.user.ProfileUser;

/**
 * Created by MSI on 2015-11-23.
 */
public class SessionManager {
    // Shared Preferences
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private final String PREF_NAME = "PracaLicencjacka";

    //Shared preferences keys
    private final String KEY_IS_NAME = "name";
    private final String KEY_IS_EMAIL = "email";
    private final String KEY_IS_ACCOUNT_TYPE = "accountType";
    private final String KEY_IS_PROFILEUSER = "ProfileUser";
    private  final String KEY_IS_LOCATION = "Location";

    public SessionManager(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        this.editor = sharedPreferences.edit();
    }

    public void setProfileUser(ProfileUser profileUser) {
        Gson gson = new Gson();
        String json = gson.toJson(profileUser);
        this.editor.putString(KEY_IS_PROFILEUSER, json);
        this.editor.commit();
    }

    public void setName(String name) {
        this.editor.putString(KEY_IS_NAME, name);
        this.editor.commit();
    }

    public void setEmail(String email) {
        this.editor.putString(KEY_IS_EMAIL, email);
        this.editor.commit();
    }

    public void setAccountType(String accountType) {
        this.editor.putString(KEY_IS_ACCOUNT_TYPE, accountType);
        this.editor.commit();
    }

    public void setLatLng(LatLng location) {
        Gson gson = new Gson();
        String json = gson.toJson(location);
        this.editor.putString(KEY_IS_LOCATION, json);
        this.editor.commit();
    }

    public ProfileUser getProfileUser() {
        Gson gson = new Gson();
        String json = this.sharedPreferences.getString(KEY_IS_PROFILEUSER, "empty");
        ProfileUser object = gson.fromJson(json, ProfileUser.class);

        return object;
    }

    public String getName() {
        return this.sharedPreferences.getString(KEY_IS_NAME, "empty");
    }

    public String getEmail() {
        return this.sharedPreferences.getString(KEY_IS_EMAIL, "empty");
    }

    public LatLng getLatLnt() {
        Gson gson = new Gson();
        String json = this.sharedPreferences.getString(KEY_IS_LOCATION, "empty");
        LatLng location = gson.fromJson(json, LatLng.class);

        return location;
    }

    public void clearSession() {
        this.editor.clear();
        this.editor.commit();
    }

}

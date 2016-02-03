package wmp.uksw.pl.pracalicencjacka_prototyp2.helpers;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Created by MSI on 2016-02-03.
 */
public class RegisterRequest extends Request<JSONObject> {

    private Response.Listener<JSONObject> listener;
    private HashMap<String, String> params;

    public RegisterRequest(String url, HashMap<String, String> params, Response.Listener<JSONObject> listener, Response.ErrorListener error) {
        super(Method.POST, url, error);

        this.listener = listener;
        this.params = params;
    }

    public RegisterRequest(int method, String url, HashMap<String, String> params, Response.Listener<JSONObject> listener, Response.ErrorListener error) {
        super(method, url, error);

        this.listener = listener;
        this.params = params;
    }

    public RegisterRequest(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);
    }

    @Override
    protected HashMap<String, String> getParams() throws AuthFailureError {
        return params;
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (JSONException e) {
            return Response.error(new ParseError(e));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

}

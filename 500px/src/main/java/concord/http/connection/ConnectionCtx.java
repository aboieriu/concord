package concord.http.connection;

import java.util.Map;

/**
 * Created by aboieriu on 9/15/16.
 */
public class ConnectionCtx {
    Map<String, String> cookies;
    String authorizationToken;

    public ConnectionCtx(Map<String, String> cookies, String authorizationToken) {
        this.cookies = cookies;
        this.authorizationToken = authorizationToken;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public String getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }
}

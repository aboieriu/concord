package concord.entity.domain;

/**
 * Created by aboieriu on 9/13/16.
 */
public class AuthCtx {
    private String auth_cookies;

    public AuthCtx(){};

    public AuthCtx(String auth_cookies) {
        this.auth_cookies = auth_cookies;
    }

    public String getAuth_cookies() {
        return auth_cookies;
    }

    public void setAuth_cookies(String auth_cookies) {
        this.auth_cookies = auth_cookies;
    }
}

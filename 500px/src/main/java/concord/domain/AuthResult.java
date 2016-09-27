package concord.domain;

/**
 * Created by aboieriu on 9/15/16.
 */
public class AuthResult {
    private String userData;
    private String authCtx;

    public AuthResult(String userData, String authCtx) {
        this.userData = userData;
        this.authCtx = authCtx;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public String getAuthCtx() {
        return authCtx;
    }

    public void setAuthCtx(String authCtx) {
        this.authCtx = authCtx;
    }
}

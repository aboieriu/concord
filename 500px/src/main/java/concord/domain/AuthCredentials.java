package concord.domain;

/**
 * Created by aboieriu on 9/13/16.
 */
public class AuthCredentials {
    private String email;
    private String password;
    private String authenticity_token;

    public AuthCredentials() {}

    public AuthCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthenticity_token() {
        return authenticity_token;
    }

    public void setAuthenticity_token(String authenticity_token) {
        this.authenticity_token = authenticity_token;
    }
}

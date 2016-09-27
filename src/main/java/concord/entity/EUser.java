package concord.entity;

import concord.entity.domain.AuthCtx;
import org.springframework.data.annotation.Id;

/**
 * Created by aboieriu on 9/13/16.
 */
public class EUser {
    @Id
    private String id;

    private String internalId;

    private String email;

    private String firstname;

    private String lastname;

    private String city;

    private String country;

    private String usertype;

    private String fullname;

    private String userpic_url;

    private AuthCtx authCtx;

    public EUser() {

    }

    public EUser(String id, String internalId, String firstname, String lastname, String city, String country, String usertype, String fullname, String userpic_url, AuthCtx authCtx) {
        this.id = id;
        this.internalId = internalId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.city = city;
        this.country = country;
        this.usertype = usertype;
        this.fullname = fullname;
        this.userpic_url = userpic_url;
        this.authCtx = authCtx;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUserpic_url() {
        return userpic_url;
    }

    public void setUserpic_url(String userpic_url) {
        this.userpic_url = userpic_url;
    }

    public AuthCtx getAuthCtx() {
        return authCtx;
    }

    public void setAuthCtx(AuthCtx authCtx) {
        this.authCtx = authCtx;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

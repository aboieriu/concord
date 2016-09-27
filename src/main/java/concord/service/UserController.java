package concord.service;

import concord.api.IAuthenticationApi;
import concord.api.user.IUserApi;
import concord.domain.AuthCredentials;
import concord.entity.EUser;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * Created by aboieriu on 9/13/16.
 */
@RestController
public class UserController {
    @Autowired
    private IUserApi userApi;

    @Autowired
    private IAuthenticationApi authenticationApi;

    @RequestMapping(path = "/authenticate", method = RequestMethod.POST)
    public EUser index(@RequestBody AuthCredentials authCredentials) {
        Optional<EUser> user = userApi.authenticate(authCredentials);
        return user.orElse(null);
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public List<EUser> index() {
        return userApi.findAll();
    }

    @RequestMapping(path = "/test", method = RequestMethod.POST)
    public void index(HttpServletRequest httpRequest) {
        if (httpRequest != null) {

        }
    }

}

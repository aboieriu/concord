package concord.api;

import concord.domain.AuthCredentials;
import concord.domain.AuthResult;
import concord.http.IFivePxHttpApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Created by aboieriu on 9/13/16.
 */
public class AuthenticationApi implements IAuthenticationApi{

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final IFivePxHttpApi fivePxHttpApi;

    public AuthenticationApi(IFivePxHttpApi fivePxHttpApi) {
        this.fivePxHttpApi = fivePxHttpApi;
    }

    public Optional<AuthResult> authenticate(AuthCredentials authCredentials) {
        return Optional.of(fivePxHttpApi.authenticateUser(authCredentials));
    }
}

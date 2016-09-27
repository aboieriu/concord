package concord.api;

import concord.domain.AuthCredentials;
import concord.domain.AuthResult;

import java.util.Optional;

/**
 * Created by aboieriu on 9/13/16.
 */
public interface IAuthenticationApi {
    Optional<AuthResult> authenticate(AuthCredentials authCredentials);
}

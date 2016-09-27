package concord.http;

import concord.domain.AuthCredentials;
import concord.domain.AuthResult;
import concord.http.connection.ConnectionCtx;
import org.apache.http.client.methods.HttpPost;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

/**
 * Created by aboieriu on 9/13/16.
 */
public interface IFivePxHttpApi {
    public AuthResult authenticateUser(AuthCredentials authCredentials);
}

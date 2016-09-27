package concord.api.user;

import concord.domain.AuthCredentials;
import concord.entity.EUser;

import java.util.List;
import java.util.Optional;

/**
 * Created by aboieriu on 9/13/16.
 */
public interface IUserApi {

    EUser save(EUser user);

    Optional<EUser> authenticate(AuthCredentials authCredentials);

    List<EUser> findAll();
}

package concord.api.user;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import concord.api.IAuthenticationApi;
import concord.domain.AuthCredentials;
import concord.domain.AuthResult;
import concord.entity.EUser;
import concord.entity.domain.AuthCtx;
import concord.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by aboieriu on 9/13/16.
 */
public class UserApi implements IUserApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final IUserRepository userRepository;

    private final IAuthenticationApi authenticationApi;

    private final ObjectMapper objectMapper;

    public UserApi(IUserRepository userRepository, IAuthenticationApi authenticationApi) {
        this.userRepository = userRepository;
        this.authenticationApi = authenticationApi;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public EUser save(EUser user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<EUser> authenticate(AuthCredentials authCredentials) {
        EUser savedUserSession = userRepository.findByEmail(authCredentials.getEmail());
        if (savedUserSession == null) {
            return authenticateUserDirectly(authCredentials);
        }
        return Optional.ofNullable(savedUserSession);
    }

    @Override
    public List<EUser> findAll() {
        return userRepository.findAll();
    }

    /**
     * Authenticate a user directly to 500px
     * @param authCredentials
     * @return
     */
    private Optional<EUser> authenticateUserDirectly(AuthCredentials authCredentials) {
        Optional<AuthResult> authResultOptional = authenticationApi.authenticate(authCredentials);
        if (authResultOptional.isPresent()) {
            try {
                AuthResult authResult = authResultOptional.get();

                EUser authenticatedUser = this.objectMapper.readValue(authResult.getUserData(), EUser.class);
                if (authenticatedUser != null) {
                    authenticatedUser.setInternalId(authenticatedUser.getId());
                    authenticatedUser.setId(null);
                    authenticatedUser.setAuthCtx(new AuthCtx(authResult.getAuthCtx()));
                    authCredentials.setEmail(authCredentials.getEmail());
                    return Optional.of(userRepository.save(authenticatedUser));
                }
                return Optional.ofNullable(authenticatedUser);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return Optional.empty();
    }
}

package concord.config;

import concord.api.IAuthenticationApi;
import concord.api.user.IUserApi;
import concord.api.user.UserApi;
import concord.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by aboieriu on 9/13/16.
 */
@Configuration
public class ServiceConfig {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IAuthenticationApi authenticationApi;

    @Bean
    public IUserApi getUserApi() {
        return new UserApi(userRepository, authenticationApi);
    }
}

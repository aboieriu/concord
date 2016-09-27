package concord.config;

import concord.api.AuthenticationApi;
import concord.api.IAuthenticationApi;
import concord.http.FivePxHttpApi;
import concord.http.IFivePxHttpApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by aboieriu on 9/13/16.
 */
@Configuration
public class FivePxConfig {
    @Bean
    public IFivePxHttpApi getFivePxHttpApi() {
        return new FivePxHttpApi();
    }

    @Bean
    public IAuthenticationApi getAuthenticationApi() {
        return new AuthenticationApi(getFivePxHttpApi());
    }
}

package concord.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by aboieriu on 4/18/17.
 */
@Configuration
@PropertySource("classpath:/META-INF/routes.properties")
public class ClassifierConfig {

}

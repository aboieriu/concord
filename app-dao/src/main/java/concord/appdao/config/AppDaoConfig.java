package concord.appdao.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by aboieriu on 4/18/17.
 */
@Configuration
@EnableMongoRepositories(basePackages = "concord.appdao.repository")
public class AppDaoConfig {
}

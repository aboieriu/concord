package concord; /**
 * Created by aboieriu on 4/23/17.
 */
import org.apache.camel.spring.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppSocialEngagement {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppSocialEngagement.class);

	Main main;

	public static void main(String[] args) throws Exception {
		LOGGER.info("Starting up camel");
		new AppSocialEngagement().startCamel(args);
		LOGGER.info("Camel stopped.");
	}

	public void startCamel(String[] args) throws Exception {
		main = new Main();
		main.setApplicationContextUri("classpath:/camel-context.xml");
		main.start();
		main.run();
	}
}

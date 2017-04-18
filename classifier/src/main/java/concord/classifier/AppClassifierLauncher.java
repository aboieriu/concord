package concord.classifier;

import org.apache.camel.spring.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by aboieriu on 4/18/17.
 */
public class AppClassifierLauncher {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppClassifierLauncher.class);

	Main main;

	public static void main(String[] args) throws Exception {
		LOGGER.info("Starting up camel");
		new AppClassifierLauncher().startCamel(args);
		LOGGER.info("Camel stopped.");
	}

	public void startCamel(String[] args) throws Exception {
		main = new Main();
		main.setApplicationContextUri("classpath:/camel-context.xml");
		main.start();
		main.run();
	}
}

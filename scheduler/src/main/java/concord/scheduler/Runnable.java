package concord.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by aboieriu on 4/18/17.
 */
public class Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(Runnable.class);

	public static void main(String[] args) throws Exception {

		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/scheduler-init.xml");

			Object lock = new Object();
			synchronized (lock) {
				lock.wait();
			}
		} catch (Exception e) {
			LOGGER.error("Something crashed", e);
		}
	}
}

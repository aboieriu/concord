package concord.indexer;

import concord.indexer.api.IAppIndexer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by aboieriu on 4/18/17.
 */
public class Runnable {

	public static void main (String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/app-init.xml");

		IAppIndexer indexer = context.getBean(IAppIndexer.class);

		indexer.indexPhotos();

	}
}

package concord.classifier.config;

import concord.classifier.JmsManager;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

/**
 * Created by aboieriu on 4/18/17.
 */
@Configuration
@EnableJms
public class JmsConfig {
	private static final String DEFAULT_BROKER_URL = "tcp://localhost:61616";

	private static final String ORDER_QUEUE = "concordPhotoInput";

	@Bean
	public ActiveMQConnectionFactory connectionFactory(){
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(DEFAULT_BROKER_URL);
		return connectionFactory;
	}

	@Bean
	public MappingJackson2MessageConverter getMappingJackson2MessageConverter(){
		return new MappingJackson2MessageConverter();
	}

	@Bean
	public JmsTemplate getJmsTemplate(){
		JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(connectionFactory());
		template.setDefaultDestinationName(ORDER_QUEUE);
		template.setMessageConverter(getMappingJackson2MessageConverter());
		return template;
	}

	@Bean
	public JmsManager jmsManager(){
		return new JmsManager(getJmsTemplate());
	}
}

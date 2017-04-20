package concord.commons;

import com.google.common.base.Preconditions;
import org.springframework.jms.core.JmsTemplate;

/**
 * Created by aboieriu on 4/18/17.
 */
public class JmsManager {

	private JmsTemplate jmsTemplate;

	public JmsManager(JmsTemplate jmsTemplate) {
		this.jmsTemplate = Preconditions.checkNotNull(jmsTemplate, "jmsTemplate must be initialized");
	}

	public void sendJmsMessage(String topic, Object message) {
		jmsTemplate.convertAndSend(topic, message);
	}
}

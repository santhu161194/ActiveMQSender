package amq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class AmqSender implements MessageListener{
	@Autowired
	JmsTemplate jmsTemplate;
	@Autowired
	Destination replyDestination;
	
	
	public void onMessage(Message arg0) {	
				try {
					System.out.println("I have got message"+arg0.getJMSReplyTo());
				} catch (JMSException e) {
					e.printStackTrace();
				}
				
	
	
	}
}
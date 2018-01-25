package demo;

import java.util.Date;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {

	@Autowired 
	JmsTemplate jmsTemplate;
	@Autowired 
	Destination replyDestination;
	@Autowired 
	Destination other;
	
	Message message;
	boolean gotTheReply;
	@RequestMapping(value = "/enter", method = RequestMethod.GET)
	@ResponseBody  
	public String showLogin(@RequestParam("value") final String value,HttpServletRequest request, HttpServletResponse response) throws JMSException, InterruptedException {
		
	/*	jmsTemplate.send("Mine", new MessageCreator() {
			
			public Message createMessage(Session session) throws JMSException {
				message=session.createTextMessage("Iam a Message sent at "+new Date());
				message.setJMSCorrelationID(new Date()+"MED"+value+"POC");
				message.setJMSReplyTo(replyDestination);
				return message;
			}
		});*/
		Message m=jmsTemplate.sendAndReceive("TestQueueTwo", new MessageCreator() {
			
			public Message createMessage(Session session) throws JMSException {
				message=session.createTextMessage("send and Recieve");
				message.setJMSReplyTo(other);
				return message;
			}
		});
		
		Thread.sleep(2000);
		
		if(gotTheReply==true) {
			gotTheReply=false;
			return "I have sent a message with CorrId.:"+message.getJMSCorrelationID()+"and I have also got the reply";
		}
		return "I have sent a message with CorrId.:"+message.getJMSCorrelationID();

		  }
	
	@JmsListener(destination="TestQueueTwo1")
	public void listenForReply() {
		gotTheReply=true;
		System.out.println("I have listened");
	}
	
	
	
	
	
	
	
	
	
	
	
}

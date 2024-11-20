package com.sri.Messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

	@Autowired
	KafkaTemplate<String, Object> kafkaTemplate;
	
	
	public void MessageForMaintenanceDeleteEmployee(String email){
		Message<String> message=MessageBuilder.withPayload(email)
				.setHeader(KafkaHeaders.TOPIC, "maintenance-employee-delete")
				.setHeader("actionType","delete")
				.build();
		kafkaTemplate.send(message);
	}
	
	public void MessageForInventoryDeleteEmployee(String email){
		System.out.println("Emp Deleted Inventory --------------------------------------");

		Message<String> message=MessageBuilder.withPayload(email)
				.setHeader(KafkaHeaders.TOPIC, "inventory-employee-delete")
				.setHeader("actionType","delete")
				.build();
		kafkaTemplate.send(message);
		System.out.println("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
	}
	
	public void MessageForProductionDeleteEmployee(String email){
		System.out.println("Emp Deleted Prodcution --------------------------------------");
		Message<String> message=MessageBuilder.withPayload(email)
				.setHeader(KafkaHeaders.TOPIC, "production-employee-delete")
				.setHeader("actionType","delete")
				.build();
		kafkaTemplate.send(message);
		System.out.println("Emp Deleted Prodcution --------------------------------------    2");

	}
	
	
}

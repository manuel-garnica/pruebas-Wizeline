package com.wizeline.maven.learningjavamaven.consumer;


import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaConsumer {

    @KafkaListener(id = "sampleGroup", topics = "useraccount-topic", containerFactory = "jsonKafkaListenerContainerFactory")
    public void consumeMessage(ConsumerRecord<String, List<BankAccountDTO>> cr, @Payload BankAccountDTO account) {
        System.out.println("Received: " + account.getUser());
    }

    @KafkaListener(id = "Prueba", topics = "useraccount-topic", containerFactory = "jsonKafkaListenerContainerFactory")
    public void consumeMessage2(ConsumerRecord<String, List<BankAccountDTO>> cr, @Payload BankAccountDTO account) {
        System.out.println("Received2: " + account.getUser());
    }

    @KafkaListener(id = "Prueba2", topics = "prueba-Topic", containerFactory = "jsonKafkaListenerContainerFactory")
    public void consumeMessagePrueba(ConsumerRecord<String, List<BankAccountDTO>> cr, @Payload BankAccountDTO account) {
        System.out.println("Desde Prueba 2: " + account.getUser());
    }

}


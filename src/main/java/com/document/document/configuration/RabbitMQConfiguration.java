package com.document.document.configuration;

import com.document.document.listener.RabbitMQListener;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${rabbitmq.queue.name}")
    String queueName;

    @Value("${rabbitmq.exchange}")
    String exchange;

    @Value("${rabbitmq.routing.key}")
    String routingKey;

    private final RabbitMQListener rabbitMQListener;

    public RabbitMQConfiguration(final RabbitMQListener rabbitMQListener) {
        this.rabbitMQListener = rabbitMQListener;
    }

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory ) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueues(queue());
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        simpleMessageListenerContainer.setMessageListener(rabbitMQListener);
        return simpleMessageListenerContainer;

    }
}

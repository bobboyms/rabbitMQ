package br.com.record.base;

import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//https://medium.com/@rameez.s.shaikh/spring-boot-rabbitmq-tutorial-retry-and-error-handling-example-8b9fa18bd685

@Configuration
public class RabbitmqConfiguration {

    @Value("${record.rabbitmq.exchange}")
    private String exchange;

    @Value("${record.rabbitmq.queue}")
    private String queue;

    @Value("${record.rabbitmq.routingKey}")
    private String routingKey;

    @Bean
    DirectExchange exchange() {
        return ExchangeBuilder.directExchange(exchange)
                .durable(true)
                .build();
        //new DirectExchange("deadLetterExchange");
    }

//    @Bean
//    DirectExchange exchange() {
//        return new DirectExchange(exchange);
//    }

    @Bean
    Queue queue() {
        return QueueBuilder.durable(queue).build();
    }

//    @Bean
//    Queue queue() {
//        return QueueBuilder.durable("javainuse.queue").withArgument("x-dead-letter-exchange", "deadLetterExchange")
//                .withArgument("x-dead-letter-routing-key", "deadLetter").build();
//    }

//    @Bean
//    Binding DLQbinding() {
//        return BindingBuilder.bind(dlq()).to(deadLetterExchange()).with("deadLetter");
//    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(routingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}

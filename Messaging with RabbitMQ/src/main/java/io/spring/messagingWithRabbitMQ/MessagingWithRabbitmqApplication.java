package io.spring.messagingWithRabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/*
    Spring AMQP’s RabbitTemplate provides everything you need to send and receive messages with RabbitMQ.
    However, you need to:

    1) Configure a message listener container.
    2) Declare the queue, the exchange, and the binding between them.
    3) Configure a component to send some messages to test the listener.

    You will use RabbitTemplate to send messages, and you will register a Receiver with
    the message listener container to receive messages.
    The connection factory drives both, letting them connect to the RabbitMQ server.

    Spring Boot automatically creates a connection factory and a RabbitTemplate,
    reducing the amount of code you have to write.
 */
@SpringBootApplication
public class MessagingWithRabbitmqApplication {
    static final String topicExchangeName = "spring-boot-exchange";

    static final String queueName = "spring-boot";

    /*
        JMS queues and AMQP queues have different semantics. For example,
        JMS sends queued messages to only one consumer. While AMQP queues do the same thing,
        AMQP producers do not send messages directly to queues. Instead,
        a message is sent to an exchange, which can go to a single queue or fan out to multiple queues,
        emulating the concept of JMS topics.

        There are two types of Queue - durable and non-durable.
        Durable queue survives a server restart.
     */
    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    /*
        Topic Exchange routes messages to multiple queues by a partial matching of a routing key.
        It uses patterns to match the routing and binding key whereas direct exchange
        routes messages to a queue by matching routing key equal to binding key.
     */
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    /*
        The queue() method creates an AMQP queue. The exchange() method creates a topic exchange.
        The binding() method binds these two together, defining the behavior that occurs when
        RabbitTemplate publishes to an exchange.

        In this case, we use a topic exchange, and the queue is bound with a routing key of foo.bar.#,
        which means that any messages sent with a routing key that begins with foo.bar.
        are routed to the queue.
     */
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
    }

    /*
        The bean defined in the listenerAdapter() method is registered as a message listener
        in the container (defined in container()). It listens for messages on the spring-boot queue.
        Because the Receiver class is a POJO, it needs to be wrapped in the MessageListenerAdapter,
        where you specify that it invokes receiveMessage.
     */
    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }
    public static void main(String[] args) {
        SpringApplication.run(MessagingWithRabbitmqApplication.class, args);
    }

}

package io.spring.messagingWithRedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/*
    Spring Data Redis provides all the components you need to send and receive messages with Redis.
    Specifically, you need to configure:
    1) A connection factory
    2) A message listener container
    3) A Redis template
    You will use the Redis template to send messages, and you will register the Receiver with
    the message listener container so that it will receive messages.
    The connection factory drives both the template and the message listener container,
    letting them connect to the Redis server.
 */

@SpringBootApplication
public class MessagingWithRedisApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingWithRedisApplication.class);

    @Bean
    Receiver receiver() {
        return new Receiver();
    }

    /*
        Because the Receiver class is a POJO, it needs to be wrapped in a message listener adapter
        that implements the MessageListener interface

        The message listener adapter is also configured to call the receiveMessage() method on
        Receiver when a message arrives.
     */
    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    /*
        RedisConnectionFactory is an instance of JedisConnectionFactory that is based on
        the Jedis Redis library. The connection factory is injected into both the message listener container
        and the Redis template.

        The bean defined in the listenerAdapter method is registered as a message listener
        in the message listener container defined in container and will listen
        for messages on the chat topic.
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));

        return container;
    }

    /*
         To send a message, you also need a Redis template. Here, it is a bean configured as
         a StringRedisTemplate, an implementation of RedisTemplate that is focused on
         the common use of Redis, where both keys and values are String instances.
     */
    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    /*
        The main() method kicks off everything by creating a Spring application context.
        The application context then starts the message listener container,
        and the message listener container bean starts listening for messages.
        The main() method then retrieves the StringRedisTemplate bean from the application context
        and uses it to send a Hello from Redis! message on the chat topic.
     */
    public static void main(String[] args) throws InterruptedException {

        ApplicationContext ctx = SpringApplication.run(MessagingWithRedisApplication.class, args);

        StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);

        Receiver receiver = ctx.getBean(Receiver.class);

        while (receiver.getCount() == 0) {

            LOGGER.info("Sending message...");
            template.convertAndSend("chat", "Hello from Redis!");
            Thread.sleep(500L);
        }

        System.exit(0);
    }

}

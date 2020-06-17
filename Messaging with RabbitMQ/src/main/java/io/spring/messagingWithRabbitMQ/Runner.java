package io.spring.messagingWithRabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class Runner implements CommandLineRunner {

//    To send a message, you also need a Rabbit template.
    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");

        /*
            Notice that the template routes the message to the exchange with a
            routing key of foo.bar.baz, which matches the binding.
         */
        rabbitTemplate.convertAndSend(MessagingWithRabbitmqApplication.topicExchangeName,
                "foo.bar.baz",
                "Hello from RabbitMQ!");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);

//        receiver.getLatch().await(1, TimeUnit.MILLISECONDS);
//        System.out.println("ok");
    }

}

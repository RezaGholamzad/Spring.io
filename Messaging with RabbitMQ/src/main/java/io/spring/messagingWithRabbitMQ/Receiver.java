package io.spring.messagingWithRabbitMQ;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    /*
        CountDownLatch is used to make sure that a task waits for other threads before it starts.
        To understand its application, let us consider a server where the main task can only
        start when all the required services have started.

        When we create an object of CountDownLatch, we specify the number of threads it should wait for,
        all such thread are required to do count down by calling CountDownLatch.countDown()
        once they are completed or ready to the job. As soon as count reaches zero, the waiting
        task starts running.
     */
    private final CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}

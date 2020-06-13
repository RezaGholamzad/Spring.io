package io.spring.messagingWithRedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/*
    create the message receiver, implement a receiver with a method to respond to messages
 */
public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private AtomicInteger counter = new AtomicInteger();

    public void receiveMessage(String message){
        LOGGER.info("Received <" + message + ">");
        counter.incrementAndGet();
    }

    public int getCount(){
        return counter.get();
    }
}

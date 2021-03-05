package com.se2.akka_demo.actors;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.se2.akka_demo.model.Message;
import com.se2.akka_demo.service.BusinessService;

import akka.actor.UntypedActor;

@Component("myActor")
@Scope("prototype")
public class MyActor extends UntypedActor {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	 
    @Autowired
    private BusinessService businessService;

    private final CompletableFuture<Message> completableFuture;

    public MyActor(CompletableFuture<Message> completableFuture) {
        this.completableFuture = completableFuture;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        businessService.perform(this + " " + message);

        if (message instanceof Message) {
            completableFuture.complete((Message) message);
        } else {
            unhandled(message);
        }

        getContext().stop(self());
    }
}

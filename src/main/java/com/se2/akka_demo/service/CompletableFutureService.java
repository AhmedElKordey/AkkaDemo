package com.se2.akka_demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.se2.akka_demo.model.Message;
import com.se2.akka_demo.spring.SpringExtension;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

@Service
public class CompletableFutureService {

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private SpringExtension springExtension;

    public List<Message> get(String payload, Long id) throws InterruptedException, ExecutionException {
        CompletableFuture<Message> completableFuture1 = new CompletableFuture<>();
        CompletableFuture<Message> completableFuture2 = new CompletableFuture<>();
        ActorRef workerActor = actorSystem.actorOf(springExtension.props("workerActor", completableFuture1), "worker-actor");
        ActorRef myActor = actorSystem.actorOf(springExtension.props("myActor", completableFuture2), "my-actor");
        workerActor.tell(new Message(payload, id), null);
        myActor.tell(new Message(payload, id+5), null);
        callActor(workerActor , new Message(payload, id));
        callActor(myActor , new Message(payload, id+5));
        
        Collection<CompletableFuture<Message>> futures = new ArrayList<>();
        futures.add(completableFuture1);
        futures.add(completableFuture2);
        
        return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());        
    }
    
    private void callActor (ActorRef actor , Message message) {
    	actor.tell(message, null);
    }
}

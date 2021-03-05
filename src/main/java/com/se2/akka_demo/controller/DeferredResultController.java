package com.se2.akka_demo.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se2.akka_demo.model.Message;
import com.se2.akka_demo.service.CompletableFutureService;

@RestController
public class DeferredResultController {
	
    private final AtomicLong id = new AtomicLong(0);

    @Autowired
    private CompletableFutureService completableFutureService;
    
    /* http://localhost:8070/async-non-blocking */ 
    @RequestMapping("/async-non-blocking")
    public List<Message> getAsyncNonBlocking() throws InterruptedException, ExecutionException {
    	return completableFutureService.get("async-non-blocking", id.getAndIncrement());
    }
}
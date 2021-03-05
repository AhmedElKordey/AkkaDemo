package com.se2.akka_demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmokeTest {
	@GetMapping("/smokeTest")
	public String serviceUp () {
		return "UP" ;
	}
}
